package com.download.services;

import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadServiceImpl implements DownloadService {

    @Override
    public Resource downloadFiles(String[] urls) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (String urlStr : urls) {
            URL url = new URL(urlStr);
            try (InputStream in = url.openStream()) {
                String fileName = url.getFile();
                ZipEntry entry = new ZipEntry(fileName);
                zos.putNextEntry(entry);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            }
        }

        zos.close();
        return new ByteArrayResource(baos.toByteArray());
    }
}
