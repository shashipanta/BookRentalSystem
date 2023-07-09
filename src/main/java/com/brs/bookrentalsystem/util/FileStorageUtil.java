package com.brs.bookrentalsystem.util;

import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FileStorageUtil {

    private final DateUtil dateUtil;
    private static final String ROOT_LOCATION = System.getProperty("user.home")
            + File.separator + "Desktop" + File.separator + "BRS";

    public String formattedIsbn;

    public String getFileStorageLocation(BookRequest request){
        String formattedBookName = request.getBookName().replace(" ", "-");
        formattedIsbn = changeToStandardIsbn(request.getIsbn());

        // ISBN_BOOK_NAME_IMAGE-SAVED-DATE.png 987-11-2-530011-1_RICH-DAD-POOR-DAD_2023-01-23.Png
        String storedFileName = String.format("%s_%s_%s.png", formattedIsbn, formattedBookName, dateUtil.dateToString(LocalDate.now()));

        String filePath = ROOT_LOCATION + File.separator + storedFileName;

        return filePath;

    }

    public String getFileStorageLocation(BookUpdateRequest request){
        String formattedBookName = request.getBookName().replace(" ", "-");
        formattedIsbn = changeToStandardIsbn(request.getIsbn());

        // ISBN_BOOK_NAME_IMAGE-SAVED-DATE.png 987-11-2-530011-1_RICH-DAD-POOR-DAD_2023-01-23.Png
        String storedFileName = String.format("%s_%s_%s.png", formattedIsbn, formattedBookName, dateUtil.dateToString(LocalDate.now()));

        return ROOT_LOCATION + File.separator + storedFileName;

    }

    public String saveMultipartFile(MultipartFile multipartFile, String fileStorageLocation) {

        // check if folder exists
        File directoryPath = new File(ROOT_LOCATION);

        if (!directoryPath.exists()) {
            directoryPath.mkdir();
        }



        File fileToStore = new File(fileStorageLocation);

        //  store file
        try {
            multipartFile.transferTo(fileToStore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO :: detect filetype and proceed


        return fileStorageLocation;
    }

    // change isbn into standard format
    public String changeToStandardIsbn(String isbn) {
        // Format : A 13-digit ISBN, 978-3-16-148410-0,
        return isbn.substring(0, 3) + "-"
                + isbn.charAt(3) + "-"
                + isbn.substring(4, 6) + "-"
                + isbn.substring(6, 12) + "-"
                + isbn.charAt(12);
    }

    public MultipartFile imagePathToMultipartFile(String filePath)  {
        File imageFile = new File(filePath);

        try {
            return new CustomMultipartFile(FileUtils.readFileToByteArray(imageFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveImageFromFilePath(BookRequest request, String filePath) throws IOException {

        String fileStorageLocation = getFileStorageLocation(request);
        File srcFile = new File(filePath);
        File dirFilePath = new File(ROOT_LOCATION);
        File destFile = new File(fileStorageLocation);

        //TODO: check file type

        if(!dirFilePath.exists()){
            dirFilePath.mkdir();
        }

        FileUtils.copyFile(srcFile, destFile);

        return fileStorageLocation;
    }

}
