package com.brs.bookrentalsystem.excel;

import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookImportHelper {

    private final DateUtil dateUtil;

    public static boolean isUploadedFileValid(MultipartFile uploadedFile) {
        String contentType = uploadedFile.getContentType();

        assert contentType != null;
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<BookRequest> readBookRequestsFromExcel(MultipartFile uploadedExcelFile) throws IOException {

        InputStream inputStream = uploadedExcelFile.getInputStream();

        List<BookRequest> list = new ArrayList<>();

        try {

            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workBook.getSheet("BookSheet1");

            int rowNum = 0;

            for (Row row : sheet) {
                //skip first row header row
                if (rowNum == 0) {
                    rowNum++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;

                Cell singleCell;
                BookRequest bookRequest = new BookRequest();

                while (cells.hasNext()) {
                    singleCell = cells.next();

                    switch (cid) {
                        case 1:
                            bookRequest.setBookName(singleCell.getStringCellValue());
                            break;
                        case 2:
                            bookRequest.setIsbn(String.valueOf(singleCell.getStringCellValue()));
                            break;
                        case 3:
                            bookRequest.setRating(singleCell.getNumericCellValue());
                            break;
                        case 4:
                            bookRequest.setStockCount((int) singleCell.getNumericCellValue());
                            break;
                        case 5:
                            bookRequest.setPublishedDate(dateUtil.stringToDate(singleCell.getStringCellValue()));
                            break;
                        case 6:
                            bookRequest.setPhotoPath(singleCell.getStringCellValue());
                            break;
                        case 7:
                            bookRequest.setCategoryId((short) singleCell.getNumericCellValue());
                            break;
                        case 8:
                            var stringCellValue = singleCell.getStringCellValue();
                            var split = stringCellValue.split(",");
                            List<Integer> authorId = new ArrayList<>();
                            for (String value : split) {
                                authorId.add(Integer.valueOf(value));
                            }
                            bookRequest.setAuthorId(authorId);
                            break;

                        case 9:
                            bookRequest.setTotalPages((short) singleCell.getNumericCellValue());
                            break;
                        default:
                            break;

                    }
                    cid++;
                }
                list.add(bookRequest);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
