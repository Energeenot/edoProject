package com.example.edo.services;

import com.example.edo.models.Files;
import com.example.edo.models.User;
import com.example.edo.repositories.FilesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilesService {

    private final FilesRepository filesRepository;

    @Value("${file.storage.path}")
    private String fileStoragePath;

    public String findUniqueNameByUniqueGroupCode(String uniqueGroupCode) {
        return filesRepository.findUniqueNameByUniqueGroupCode(uniqueGroupCode);
    }

    public Path getFilePathByName(String fileName) {
        return Paths.get(fileStoragePath, fileName);
    }

    public String saveZippedFiles(String uniqueID, List<MultipartFile> files) {
        String uniqueFileName = generateUniqueFileName(uniqueID);
        String UPLOAD_FOLDER = "src/main/webapp/uploads/";
        String zipFileName = UPLOAD_FOLDER + uniqueFileName;

        try {
            createZipArchive(files, zipFileName);
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании ZIP-архива", e);
        }
    }

    private String generateUniqueFileName(String uniqueID) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(uniqueID.getBytes(StandardCharsets.UTF_8));
            String uniqueFileName = Base64.getEncoder().encodeToString(hash) + "_" + uniqueID;
            return uniqueFileName.replaceAll("/", "_") + ".zip";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка генерации хэша для имени файла", e);
        }
    }

    private void createZipArchive(List<MultipartFile> files, String zipFileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);
                String zipEntryName = originalFilename + "." + extension;

                zos.putNextEntry(new ZipEntry(zipEntryName));
                zos.write(file.getBytes());
                zos.closeEntry();
            }
        }
    }


    public Files saveFiles(String uniqueFileName, String uniqueID, User sender){
        Files newFiles = new com.example.edo.models.Files();
        newFiles.setUniqueName(uniqueFileName);
        newFiles.setUniqueGroupCode(uniqueID);

        newFiles.setSender(sender);
        return filesRepository.save(newFiles);
    }
}
