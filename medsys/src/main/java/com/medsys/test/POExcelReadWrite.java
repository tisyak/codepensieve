package com.medsys.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.medsys.orders.model.PurchaseOrderProductSet;
import com.medsys.product.model.ProductMaster;

public class POExcelReadWrite {

	public static void main(String[] args) {
		readExcel();
		createPOMain();
	}

	@SuppressWarnings("deprecation")
	private static void readExcel() {

		String FILE_NAME = "F:/Cini/UnniPpt/DivineOrthocare/Templates/PO_Format.xlsx";

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
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

	private static void createPOMain() {
		/*
		 * XSSFWorkbook templateWorkbook = new XSSFWorkbook(TEMPLATE_FILE_NAME);
		 * FileOutputStream newExcelFile = new FileOutputStream(new
		 * File(NEW_FILE_NAME)); templateWorkbook.write(newExcelFile);
		 * templateWorkbook.close(); newExcelFile.close();
		 */

		ArrayList<String> pdtCodes = new ArrayList<String>(Arrays.asList("SS026.12", "SS026.14", "SS026.16", "SS026.18",
				"SS027.12", "SS027.14", "SS027.16", "SS027.18", "SS027.20", "SS027.22", "SS027.24"));
		java.util.SortedSet<PurchaseOrderProductSet> products = new ConcurrentSkipListSet<PurchaseOrderProductSet>();
		for (String pdtCode : pdtCodes) {
			int i = 1;
			PurchaseOrderProductSet poProductSet = new PurchaseOrderProductSet();
			ProductMaster pm = new ProductMaster();
			pm.setProductCode(pdtCode);
			poProductSet.setProduct(pm);
			poProductSet.setQty(i);
			i++;
			products.add(poProductSet);

		}

		final String TEMPLATE_FILE_NAME = "F:/Cini/UnniPpt/DivineOrthocare/Templates/PO_Format.xlsx";
		final String NEW_FILE_NAME = "F:/Cini/UnniPpt/DivineOrthocare/Templates/PO_5.xlsx";
		int pdtListStartRowNum = 13, pdtListEndRowNum = 47, pdtDescColNum = 1, pdtCodeColNum = 2, pdtQtyColNum = 3;
		createNewPOExcel(TEMPLATE_FILE_NAME, products, pdtListStartRowNum, pdtListEndRowNum, pdtDescColNum,
				pdtCodeColNum, pdtQtyColNum, NEW_FILE_NAME);

	}

	private static void createNewPOExcel(String templateFilePathandName, SortedSet<PurchaseOrderProductSet> products,
			int pdtListStartRowNum, int pdtListEndRowNum, int pdtDescColNum, int pdtCodeColNum, int pdtQtyColNum,
			String POFileName) {

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

			Sheet templateSheet = templateWorkbook.getSheetAt(0);
			Iterator<Row> rowIterator = templateSheet.iterator();
			Iterator<PurchaseOrderProductSet> pdtIterator = products.iterator();

			Row currentRow = rowIterator.next();

			while (rowIterator.hasNext()) {

				if (currentRow.getRowNum() >= pdtListStartRowNum && currentRow.getRowNum() <= pdtListEndRowNum) {

					System.out.println("Writing into row: " + currentRow.getRowNum());
					if (pdtIterator.hasNext()) {
						PurchaseOrderProductSet poProductSet = pdtIterator.next();
						Cell pdtCodeCell = currentRow.getCell(pdtCodeColNum);
						// Cell pdtDescCell = currentRow.getCell(pdtDescColNum);
						// CellValue pdtDescCellValue =
						// evaluator.evaluate(pdtDescCell);
						Cell qtyCodeCell = currentRow.getCell(pdtQtyColNum);
						pdtCodeCell.setCellValue(poProductSet.getProduct().getProductCode());
						qtyCodeCell.setCellValue(poProductSet.getQty());
					} else {
						System.out.println("No.of products in PO exhausted. Exiting Row iterator loop. ");
						break;
					}

					System.out.println("Completed writing row: " + currentRow.getRowNum() + ".Iterating to next row.");
					currentRow = rowIterator.next();
				} else {
					System.out.println("Skipping row: " + currentRow.getRowNum() + ".Not part of product list range.");
					currentRow = rowIterator.next();
				}
			}

			if (pdtIterator.hasNext()) {
				System.out.println("No.of products in PO exceeds PO Format. Please create separate PO.");
			}

			try {
				// evaluator.evaluateAll();
				templateWorkbook.setForceFormulaRecalculation(true);

				File newFile = new File(POFileName);
				boolean isFileCreated = newFile.createNewFile();
				if (isFileCreated) {
					FileOutputStream outputStream = new FileOutputStream(newFile);
					templateWorkbook.write(outputStream);
					outputStream.close();
				} else {
					System.out.println("Unable to create new file in path: " + POFileName);
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