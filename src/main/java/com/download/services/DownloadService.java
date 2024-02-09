package com.download.services;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface DownloadService{

    public Resource downloadFiles(String[] urls) throws IOException;
}
