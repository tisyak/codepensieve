package com.medsys.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.medsys.customer.model.Customer;
import com.medsys.orders.model.Invoice;

public class GSTRExcelUtil {

	public static void main(String[] args) {
		final String TEMPLATE_FILE_NAME = "F:/Cini/UnniPpt/DivineOrthocare/Templates/GSTR1_Template-V1.0.xlsx";
		 readExcel(TEMPLATE_FILE_NAME);
		try {
			createGSTRMain();
		} catch (Exception e) {
			System.out.println("Exception in createGSTRMain: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void createGSTRMain() throws Exception {
		/*
		 * XSSFWorkbook templateWorkbook = new XSSFWorkbook(TEMPLATE_FILE_NAME);
		 * FileOutputStream newExcelFile = new FileOutputStream(new
		 * File(NEW_FILE_NAME)); templateWorkbook.write(newExcelFile);
		 * templateWorkbook.close(); newExcelFile.close();
		 */
		List<Invoice> invoices = new ArrayList<Invoice>();
		int num_of_ids = 25; // Number of "ids" to generate (26*26*26*999
		// + 1 <-- To get ).
		int i = 0; // Loop counter.

		String invNumber = "INV-2017-AAA-0001";

		while (i <= num_of_ids) {

			invNumber = TestGenerateAlphanumericNos.getNextInvoiceNumber(invNumber);
			// writer.print(invNumber + "\t");
			Invoice invoice = new Invoice();
			invoice.setInvoiceNo(invNumber);
			invoice.setInvoiceDate(new Date());
			//invoice.setTotalAmountBeforeTax(new BigDecimal("1500" + i));
			invoice.setTotalAmount(new BigDecimal("1550" + i));
			Customer cust = new Customer();
			cust.setGstin("XXABHJI3450L2" + i + "C");
			invoice.setCustomer(cust);
			System.out.println(i + " : " + invNumber); // Print out
			// the id.
			i++;
			invoices.add(invoice);

		}

		String PLACE_OF_SUPPLY = "27-Maharashtra", REVERSE_CHARGE = "N", INVOICE_TYPE = "Regular", RATE = "5.00";

		final String TEMPLATE_FILE_NAME = "F:/Cini/UnniPpt/DivineOrthocare/Templates/GSTR1_Template-V1.0.xlsx";
		String filePrefix = "GSTR1_";
		Calendar cal = Calendar.getInstance();
		String fileName = filePrefix + new SimpleDateFormat("MMM").format(cal.getTime())
				+ new SimpleDateFormat("YYYY").format(cal.getTime());

		final String NEW_FILE_NAME = "F:/Cini/UnniPpt/DivineOrthocare/Templates/" + fileName + ".xlsx";
		int recipientGSTINColNum = 0, invoiceNumberColNum = 1, invoiceDateColNum = 2, invoiceValueColNum = 3,
				placeOfSupplyColNum = 4, reverseChargeColNum = 5, invoiceTypeColNum = 6, eCommerceGSTINColNum = 7,
				rateColNum = 8, taxableValueColNum = 9, cessAmountColNum = 10;

		int MAX_ITEMS = 19000;
		int templateSheetNumber=1,pdtListStartRowNum = 4, pdtListEndRowNum = MAX_ITEMS - pdtListStartRowNum;
		createNewGSTRExcel(TEMPLATE_FILE_NAME,templateSheetNumber, invoices, pdtListStartRowNum, pdtListEndRowNum, recipientGSTINColNum,
				invoiceNumberColNum, invoiceDateColNum, invoiceValueColNum, placeOfSupplyColNum, reverseChargeColNum,
				invoiceTypeColNum, eCommerceGSTINColNum, rateColNum, taxableValueColNum, cessAmountColNum,
				PLACE_OF_SUPPLY, REVERSE_CHARGE, INVOICE_TYPE, RATE, NEW_FILE_NAME);

	}
	
	@SuppressWarnings("deprecation")
	private static void readExcel(String TEMPLATE_FILE_NAME) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(TEMPLATE_FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowNumber = 0;
			while (iterator.hasNext()  && rowNumber <=8) {

				Row currentRow = iterator.next();
				rowNumber = currentRow.getRowNum() ;
				System.out.println("Row Number: " + currentRow.getRowNum());
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					System.out.println("Column index: " + currentCell.getColumnIndex());
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting
					// from version 4.0
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "--");
					}

				}
				System.out.println();

			}
			
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createNewGSTRExcel(String templateFilePathandName,int templateSheetNumber, List<Invoice> invoices,
			int pdtListStartRowNum, int pdtListEndRowNum, int recipientGSTINColNum, int invoiceNumberColNum,
			int invoiceDateColNum, int invoiceValueColNum, int placeOfSupplyColNum, int reverseChargeColNum,
			int invoiceTypeColNum, int eCommerceGSTINColNum, int rateColNum, int taxableValueColNum,
			int cessAmountColNum, String constPlaceOfSupply, String constReverseCharge, String constInvoiceType,
			String constRate, String gstrFileName) {

		/*
		 * In current PO format 14 C - Pdt Code 14 D - Qty
		 */
		FileInputStream templateInputStream = null;
		try {
			templateInputStream = new FileInputStream(new File(templateFilePathandName));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e.getMessage());
			e.printStackTrace();
		}
		Workbook templateWorkbook;
		try {
			templateWorkbook = WorkbookFactory.create(templateInputStream);

			Sheet templateSheet = templateWorkbook.getSheetAt(templateSheetNumber);
			Iterator<Row> rowIterator = templateSheet.iterator();
			Iterator<Invoice> invIterator = invoices.iterator();

			Row currentRow = rowIterator.next();

			while (rowIterator.hasNext()) {

				if (currentRow.getRowNum() >= pdtListStartRowNum && currentRow.getRowNum() <= pdtListEndRowNum) {

					System.out.println("Writing into row: " + currentRow.getRowNum());
					if (invIterator.hasNext()) {
						Invoice invoice = invIterator.next();
						/*
						 * recipientGSTINColNum, invoiceNumberColNum,
						 * invoiceDateColNum, invoiceValueColNum,
						 * placeOfSupplyColNum, reverseChargeColNum,
						 * invoiceTypeColNum, eCommerceGSTINColNum, rateColNum,
						 * taxableValueColNum, cessAmountColNum
						 */
						Cell recipientGSTINCell = currentRow.getCell(recipientGSTINColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell invoiceNumberCell = currentRow.getCell(invoiceNumberColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell invoiceDateCell = currentRow.getCell(invoiceDateColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell invoiceValueCell = currentRow.getCell(invoiceValueColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell placeOfSupplyCell = currentRow.getCell(placeOfSupplyColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell reverseChargeCell = currentRow.getCell(reverseChargeColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell invoiceTypeCell = currentRow.getCell(invoiceTypeColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell eCommerceGSTINCell = currentRow.getCell(eCommerceGSTINColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell rateCell = currentRow.getCell(rateColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell taxableValueCell = currentRow.getCell(taxableValueColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						Cell cessAmountCell = currentRow.getCell(cessAmountColNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

						recipientGSTINCell.setCellValue(invoice.getCustomer().getGstin());
						invoiceNumberCell.setCellValue(invoice.getInvoiceNo());
						invoiceDateCell.setCellValue(invoice.getInvoiceDate());
						invoiceValueCell.setCellValue(invoice.getTotalAmount().toPlainString());
						placeOfSupplyCell.setCellValue(constPlaceOfSupply);
						reverseChargeCell.setCellValue(constReverseCharge);
						invoiceTypeCell.setCellValue(constInvoiceType);
						eCommerceGSTINCell.setCellValue("");
						rateCell.setCellValue(constRate);
						taxableValueCell.setCellValue(invoice.getTotalBeforeTax().toPlainString());
						cessAmountCell.setCellValue("");

					} else {
						System.out.println("No.of invoices exhausted. Exiting Row iterator loop. ");
						break;
					}

					System.out.println("Completed writing row: " + currentRow.getRowNum() + ".Iterating to next row.");
					currentRow = rowIterator.next();
				} else {
					System.out.println("Skipping row: " + currentRow.getRowNum() + ".Not part of product list range.");
					currentRow = rowIterator.next();
				}
			}

			if (invIterator.hasNext()) {
				System.out.println("No.of products in Invoice exceeds GSTR Format. Please create separate GSTR-1.");
			}

			try {
				// evaluator.evaluateAll();
				templateWorkbook.setForceFormulaRecalculation(true);
				templateWorkbook.setActiveSheet(templateSheetNumber);
				File newFile = new File(gstrFileName);
				boolean isFileCreated = newFile.createNewFile();
				if (isFileCreated) {
					FileOutputStream outputStream = new FileOutputStream(newFile);
					templateWorkbook.write(outputStream);
					outputStream.close();
				} else {
					System.out.println("Unable to create new file in path: " + gstrFileName);
				}
				templateWorkbook.close();
				templateInputStream.close();

				/* Open and close book for it to refresh the values */

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Done");

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			System.out.println("Exception in reading workbook: " + e.getMessage());
			e.printStackTrace();
		}

	}

}