// package com.project.service;

// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// import java.io.*;
// import java.nio.file.*;
// import java.util.zip.*;

// @Service
// public class FileService {
//     public File extractZip(MultipartFile file, String jobId) throws IOException {
//         Path uploadPath = Paths.get("uploads", jobId);
//         Files.createDirectories(uploadPath);
        
//         try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
//             ZipEntry entry;
//             while ((entry = zis.getNextEntry()) != null) {
//                 // Create a subfolder based on the filename to satisfy JPlag structure
//                 String folderName = entry.getName().replaceAll("[^a-zA-Z0-9]", "_");
//                 Path filePath = uploadPath.resolve(folderName).resolve(entry.getName());
//                 Files.createDirectories(filePath.getParent());
//                 Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
//                 zis.closeEntry();
//             }
//         }
//         return uploadPath.toFile();
//     }
// }


package com.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    // public File extractZip(MultipartFile file, String jobId) throws IOException {
    //     Path uploadPath = Paths.get("uploads", jobId);
    //     Files.createDirectories(uploadPath);

    //     try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
    //         ZipEntry entry;

    //         while ((entry = zis.getNextEntry()) != null) {
    //             if (entry.isDirectory()) continue;

    //             Path filePath = uploadPath.resolve(entry.getName());

    //             // Create parent directories properly
    //             Files.createDirectories(filePath.getParent());

    //             Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
    //             zis.closeEntry();
    //         }
    //     }

    //     return uploadPath.toFile();
    // }
    public File extractZip(MultipartFile file, String jobId) throws IOException {
    Path uploadPath = Paths.get("uploads", jobId);
    Files.createDirectories(uploadPath);

    try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {
            if (entry.isDirectory()) continue;

            String folderName = entry.getName()
                    .split("/")[0]
                    .replaceAll("[^a-zA-Z0-9]", "_");

            Path filePath = uploadPath
                    .resolve(folderName)
                    .resolve(Paths.get(entry.getName()).getFileName());

            Path normalizedPath = filePath.normalize();

            // 🔒 prevent Zip Slip
            if (!normalizedPath.startsWith(uploadPath)) {
                throw new IOException("Bad zip entry: " + entry.getName());
            }

            Files.createDirectories(normalizedPath.getParent());
            Files.copy(zis, normalizedPath, StandardCopyOption.REPLACE_EXISTING);

            zis.closeEntry();
        }
    }

    return uploadPath.toFile();
}
}