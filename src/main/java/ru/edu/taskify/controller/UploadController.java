package ru.edu.taskify.controller;

import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.edu.taskify.dto.UploadLinkRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Log
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UploadController {

    private final String uploadDir = "uploads";


    @PostMapping("/upload-by-link")
    public ResponseEntity<?> uploadByLink(@RequestBody UploadLinkRequest request) {
        try {
            String link = request.getLink();
            if (link == null || link.isEmpty()) {
                return ResponseEntity.badRequest().body("No link provided");
            }


            String newName = "photo" + System.currentTimeMillis() + ".jpg";


            File tempFile = downloadFile(link, "/tmp/" + newName);


            Path targetPath = Paths.get(uploadDir).resolve(newName).normalize();
            Files.createDirectories(targetPath.getParent());
            Files.move(tempFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);


            return ResponseEntity.ok(newName);

        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("photos") List<MultipartFile> photos) {
        List<String> uploadedFilenames = new ArrayList<>();

        for (MultipartFile photo : photos) {
            try {
                String originalName = photo.getOriginalFilename();
                String extension = "";
                if (originalName != null && originalName.contains(".")) {
                    extension = originalName.substring(originalName.lastIndexOf("."));
                }
                String newName = "photo" + System.currentTimeMillis() + UUID.randomUUID() + extension;

                Path targetPath = Paths.get(uploadDir).resolve(newName).normalize();
                Files.createDirectories(targetPath.getParent());
                photo.transferTo(targetPath);


                uploadedFilenames.add(newName);

            } catch (IOException e) {
                log.info(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file");
            }
        }

        return ResponseEntity.ok(uploadedFilenames);
    }


    private File downloadFile(String link, String destPath) throws IOException {
        URL url = new URL(link); // <-- нужно http:// или https://
        File file = new File(destPath);
        FileUtils.copyURLToFile(url, file, 5000, 5000); // 5s таймаут
        return file;
    }

}
