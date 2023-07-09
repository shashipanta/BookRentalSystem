package com.brs.bookrentalsystem.excel;

import com.brs.bookrentalsystem.dto.transaction.TransactionExcelResponse;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Data
@Configuration
@RequiredArgsConstructor
public class TransactionExcelWriter {

    private final BookTransactionService transactionService;

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        generateTransactionExcel(workbook);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }

    public void generateTransactionExcel(XSSFWorkbook workbook) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        List<TransactionExcelResponse> allTransactions = transactionService.getAllTransactionsForExcel();

        String json = objectMapper.writeValueAsString(allTransactions);
        objectMapper.writeValue(new File("BookTransactions.json"), allTransactions);

        XSSFSheet sheet = workbook.createSheet(" Sheet1"); //creates sheet in excel

        Drawing drawing = sheet.createDrawingPatriarch();
        try (FileOutputStream outputStream = new FileOutputStream("BookTransactions.xlsx")) {

            JsonNode root = objectMapper.readTree(json);
            Iterator<JsonNode> transactionItr = root.iterator();

            int colNum = 0;
            int rowNum = 0;
            while (transactionItr.hasNext()) {
                Row row;
                row = sheet.createRow(++rowNum);
                // iterate list
                JsonNode next = transactionItr.next();

                // find each node and
                Iterator<String> stringIterator = next.fieldNames();
                while (stringIterator.hasNext()) {
                    String field = stringIterator.next();
                    Cell cell = row.createCell(colNum++);
                    String fieldValue = next.get(field).asText();

                    if (fieldValue.startsWith("'")) {
                        fieldValue = fieldValue.replace("'", "");
                    }

                    // save image to the cell
                    if (fieldValue.startsWith("/")) {
                        // write image into cell
                        row.setHeight((short) 1000);
                        writeImageIntoExcelCell(workbook, "xlsx", rowNum, colNum, drawing, fieldValue);
                        continue;
                    }


                    cell.setCellValue(fieldValue);
                }


                colNum = 0;
            }

            // add headers
            Iterator<String> headerIterator = root.iterator().next().fieldNames();
            Row headerRow = sheet.createRow(0);

            // format header
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            headerFont.setColor(IndexedColors.DARK_TEAL.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();

            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

            while (headerIterator.hasNext()) {
                Cell headerCell = headerRow.createCell(colNum++);
                headerCell.setCellStyle(headerCellStyle);
                String headerVal = headerIterator.next();

                String[] r = headerVal.split("(?=\\p{Upper})");
                headerVal = String.join("_", r);

                headerCell.setCellValue(headerVal.toUpperCase());

                // Autosize column
                sheet.autoSizeColumn(colNum);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    // write image into excel cell
    private void writeImageIntoExcelCell(Workbook workbook,
                                         String fileType,
                                         int rowNo, int colNo,
                                         Drawing drawing,
                                         String imagePath) {


        File imageFile = new File(imagePath);
        Integer imageId = null;

        try (FileInputStream fis = new FileInputStream(imageFile)) {

            byte[] inputImageBytes = fis.readAllBytes();

            String imageFileName = imagePath.substring(imagePath.lastIndexOf("/"));
            String imageExtension = imageFileName.substring(imageFileName.indexOf("."));

            imageId = workbook.addPicture(inputImageBytes, Workbook.PICTURE_TYPE_PNG);

        } catch (IOException e) {
            // do something
        }

        // Create client anchor based on file format
        ClientAnchor clientAnchor = null;

        if (fileType.equals("xls")) {
            // horrible format
            clientAnchor = new HSSFClientAnchor();
        } else {
            clientAnchor = new XSSFClientAnchor();
        }

        clientAnchor.setCol1(colNo -1);
        clientAnchor.setCol2(colNo);
        clientAnchor.setRow1(rowNo);
        clientAnchor.setRow2(rowNo + 1);

        // draw the image into excel file
        drawing.createPicture(clientAnchor, imageId);
    }


}
