package com.medsys.ui.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.orders.bd.PurchaseOrderBD;
import com.medsys.orders.model.PurchaseOrder;
import com.medsys.orders.model.PurchaseOrderProductSet;

@Service
public class POExcelDownloadService {

	static Logger logger = LoggerFactory.getLogger(POExcelDownloadService.class);

	@Autowired
	private PurchaseOrderBD purchaseOrderBD;

	final String TEMPLATE_FILE_NAME = "/excel/PO_Format.xlsx";

	@Transactional
	public void download(String type, Integer purchaseOrderId, HttpServletResponse response) {

		logger.debug("Getting PO Excel for: " + purchaseOrderId);
		PurchaseOrder purchaseOrder = purchaseOrderBD.getPurchaseOrder(purchaseOrderId);
		List<PurchaseOrderProductSet> lstPurchaseOrderProductSet = purchaseOrderBD
				.getAllProductsInPurchaseOrder(purchaseOrderId);
		int pdtListStartRowNum = 13, pdtListEndRowNum = 47, pdtDescColNum = 1, pdtCodeColNum = 2, pdtQtyColNum = 3;
		final String NEW_FILE_NAME_PATH_AND_PREFIX = "C:/DO/PO/PO_";
		String finalFileLocation = NEW_FILE_NAME_PATH_AND_PREFIX + purchaseOrder.getPurchaseOrderNumber() + ".xlsx";
		createNewPOExcel(TEMPLATE_FILE_NAME, lstPurchaseOrderProductSet, pdtListStartRowNum, pdtListEndRowNum,
				pdtDescColNum, pdtCodeColNum, pdtQtyColNum, finalFileLocation);
		/*
		 * IF EXCEL is to be opened via browser
		 * logger.debug("Writing report to the stream"); try { // Retrieve the
		 * output stream ServletOutputStream outputStream =
		 * response.getOutputStream(); // Write to the output stream
		 * worksheet.getWorkbook().write(outputStream); // Flush the stream
		 * outputStream.flush();
		 * 
		 * } catch (Exception e) {
		 * logger.error("Unable to write report to the output stream"); }
		 * 
		 */
		// Retrieve output stream
		try {
			ServletOutputStream outputStream = response.getOutputStream();

			// Write to output stream
			outputStream.println("Excel successfully generated at location: " + finalFileLocation);
			// Flush the stream
			outputStream.flush();
		} catch (IOException e) {
			logger.error("Unable to write message to the output stream: " + e.getMessage());
		}

	}

	private void createNewPOExcel(String templateFilePathandName, List<PurchaseOrderProductSet> products,
			int pdtListStartRowNum, int pdtListEndRowNum, int pdtDescColNum, int pdtCodeColNum, int pdtQtyColNum,
			String POFileName) {

		/*
		 * In current PO format 14 C - Pdt Code 14 D - Qty
		 */
		InputStream templateInputStream = this.getClass().getResourceAsStream(templateFilePathandName);

		Workbook templateWorkbook;
		try {
			templateWorkbook = WorkbookFactory.create(templateInputStream);

			Sheet templateSheet = templateWorkbook.getSheetAt(0);
			Iterator<Row> rowIterator = templateSheet.iterator();
			Iterator<PurchaseOrderProductSet> pdtIterator = products.iterator();

			Row currentRow = rowIterator.next();

			while (rowIterator.hasNext()) {

				if (currentRow.getRowNum() >= pdtListStartRowNum && currentRow.getRowNum() <= pdtListEndRowNum) {

					logger.debug("Writing into row: " + currentRow.getRowNum());
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
						logger.debug("No.of products in PO exhausted. Exiting Row iterator loop. ");
						break;
					}

					logger.debug("Completed writing row: " + currentRow.getRowNum() + ".Iterating to next row.");
					currentRow = rowIterator.next();
				} else {
					logger.debug("Skipping row: " + currentRow.getRowNum() + ".Not part of product list range.");
					currentRow = rowIterator.next();
				}
			}

			if (pdtIterator.hasNext()) {
				logger.debug("No.of products in PO exceeds PO Format. Please create separate PO.");
			}

			try {
				// evaluator.evaluateAll();
				templateWorkbook.setForceFormulaRecalculation(true);

				File newFile = new File(POFileName);
				boolean isFileCreated = newFile.createNewFile();
				//if (isFileCreated) {
					FileOutputStream outputStream = new FileOutputStream(newFile);
					templateWorkbook.write(outputStream);
					outputStream.close();
				/*} else {
					logger.error("Unable to create new file in path: " + POFileName);
				}*/
				templateWorkbook.close();
				templateInputStream.close();

				/* Open and close book for it to refresh the values */

			} catch (FileNotFoundException e) {
				logger.error("Error encountered: " + e.getMessage());
			} catch (IOException e) {
				logger.error("Error encountered: " + e.getMessage());
			}

			logger.debug("Done");

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			logger.debug("Exception in reading workbook: " + e.getMessage());
			logger.error("Error encountered: " + e.getMessage());
		}

	}
}