package com.brs.bookrentalsystem.util;

import com.brs.bookrentalsystem.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;


@RequiredArgsConstructor
public class CustomMultipartFile implements MultipartFile {

    private byte[] inputFile;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return inputFile == null || inputFile.length == 0;
    }

    @Override
    public long getSize() {
        return inputFile.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return inputFile;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(inputFile);
    }

    @Override
    public Resource getResource() {
        return MultipartFile.super.getResource();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try(FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(inputFile);
        }
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        MultipartFile.super.transferTo(dest);
    }
}
