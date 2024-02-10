package com.download.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadServiceImpl implements DownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServiceImpl.class);
    @Override
    public Resource downloadFiles(String[] urls){
        try {
//            unique temp file name with date
            String tempFileName = "downloadedFiles" + System.currentTimeMillis();
            File tempFile = File.createTempFile(tempFileName, ".zip");
            FileOutputStream fos = new FileOutputStream(tempFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (String urlStr : urls) {
                URL url = new URI(urlStr).toURL();

                long start = System.currentTimeMillis();
                LOGGER.info("Downloading " + urlStr);
                try (InputStream in = url.openStream()) {
                    String fileName = url.openConnection().getHeaderField("Content-Disposition");
                    if (fileName != null && !fileName.isEmpty()) {
                        fileName = fileName.substring(fileName.indexOf("filename=") + 10, fileName.length() - 1);
                    }

                    LOGGER.info("File name: " + fileName);
                    ZipEntry entry = new ZipEntry(fileName);
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();

                    long end = System.currentTimeMillis();
                    LOGGER.info("Downloaded " + urlStr + " in " + (end - start) + " ms");
                }
            }

            zos.close();
            return new FileSystemResource(tempFile);
        }
        catch (Exception e) {
            LOGGER.error("Error downloading files", e);
            return null;
        }


    }
}
