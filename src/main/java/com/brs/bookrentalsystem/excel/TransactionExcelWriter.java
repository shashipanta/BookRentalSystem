package com.brs.bookrentalsystem.excel;

import com.brs.bookrentalsystem.dto.transaction.TransactionExcelResponse;
import com.brs.bookrentalsystem.service.BookTransactionService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import java.io.File;
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

        generateTransactionExcel( workbook);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void generateTransactionExcel(XSSFWorkbook  workbook) throws IOException {
//        Book book1 = new Book(12, "Book Name");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        List<TransactionExcelResponse> allTransactions = transactionService.getAllTransactionsForExcel();

        System.out.println("All Transactions : \n" + allTransactions);

        String json = objectMapper.writeValueAsString(allTransactions);
        objectMapper.writeValue(new File("BookTransactions.json"), allTransactions);



        System.out.println("object mapped successfully " + json );
        XSSFSheet sheet = workbook.createSheet(" Sheet1"); //creates sheet in excel

        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode transaction = root.get("transaction");
            Iterator<JsonNode> transactionItr = root.iterator();

            int colNum = 0;
            int rowNum = 0;
            while(transactionItr.hasNext()) {
                Row row;
                row = sheet.createRow(++rowNum);
                // iterate list
                JsonNode next = transactionItr.next();

                // find each node and
                Iterator<String> stringIterator = next.fieldNames();
                while(stringIterator.hasNext()){
                    String field = stringIterator.next();
                    Cell cell = row.createCell(colNum++);
                    String fieldValue = next.get(field).asText();

                    if(fieldValue.startsWith("'")){
                         fieldValue = fieldValue.replace("'", "");
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
//            headerFont.setFontHeightInPoints((short) 11);
            headerFont.setColor(IndexedColors.DARK_TEAL.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();

            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

            while(headerIterator.hasNext()){
                Cell headerCell = headerRow.createCell(colNum++);
                headerCell.setCellStyle(headerCellStyle);
                headerCell.setCellValue(headerIterator.next().toUpperCase());
            }

            FileOutputStream outputStream = new FileOutputStream("BookTransactions.xlsx");
//            workbook.write(outputStream);
//            workbook.close();
            System.out.println(" Excel file is generated ");

        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


}
