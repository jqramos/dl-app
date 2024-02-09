package com.download.api;

import com.download.services.DownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DownloadController {

    private final DownloadService downloadService;

    //add logging sl4j
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadFiles(@RequestBody String[] urls) {
        try {
            Resource resource = downloadService.downloadFiles(urls);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
//                    return as zip file
                    .header("Content-Disposition", "attachment; filename=download.zip")
                    .body(resource);
        } catch (Exception e) {
            LOGGER.error("Error downloading files", e);
            return ResponseEntity.badRequest().build();
        }
    }

}
