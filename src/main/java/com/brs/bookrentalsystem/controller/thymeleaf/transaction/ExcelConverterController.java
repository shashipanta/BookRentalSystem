package com.brs.bookrentalsystem.controller.thymeleaf.transaction;

import com.brs.bookrentalsystem.excel.TransactionExcelWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "brs/excel")
public class ExcelConverterController {


    private final TransactionExcelWriter transactionExcelWriter;


    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BookTransaction" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        transactionExcelWriter.generateExcelFile(response);
    }
}
