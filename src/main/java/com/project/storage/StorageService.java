// package main.java.com.project.storage;

// public class StorageService {
    
// }

package com.project.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    private final String JOBS_DIR = "jobs";

    public File saveJobFiles(Long jobId, MultipartFile file) throws IOException {
        Path jobDir = Path.of(JOBS_DIR, String.valueOf(jobId), "submissions");
        Files.createDirectories(jobDir);

        // Save uploaded ZIP
        Path uploadedZip = jobDir.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), uploadedZip, StandardCopyOption.REPLACE_EXISTING);

        // Extract ZIP
        unzip(uploadedZip.toFile(), jobDir.toFile());

        return jobDir.toFile();
    }

    private void unzip(File zipFile, File destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile.toPath()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    newFile.getParentFile().mkdirs();
                    Files.copy(zis, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}