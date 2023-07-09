package com.brs.bookrentalsystem.controller.thymeleaf.file;


import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;

// http://localhost:8080/uploads/{fileName}
@Controller
@RequestMapping("/uploads")
public class FileController {

    //root path for image files
    private static final String ROOT_LOCATION = System.getProperty("user.home")
            + File.separator + "Desktop" + File.separator + "BRS";


    // http://localhost:8080/brs/uploads/file?filename=myFileName.extension
    @GetMapping("/file")
    public ResponseEntity<byte[]> getImage(@RequestParam("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(ROOT_LOCATION + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}