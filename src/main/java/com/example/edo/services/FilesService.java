package com.example.edo.services;

import com.example.edo.models.Files;
import com.example.edo.repositories.FilesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilesService {
    private final FilesRepository filesRepository;

    @Value("${file.storage.path}")
    private String fileStoragePath;

    public List<String> findAllUniqueNamesByUniqueGroupCode(String uniqueGroupCode){
        return filesRepository.findAllUniqueNamesByUniqueGroupCode(uniqueGroupCode);
    }
    public String findUniqueNameByUniqueGroupCode(String uniqueGroupCode){
        return filesRepository.findUniqueNameByUniqueGroupCode(uniqueGroupCode);
    }

    public Path getFilePathByName(String fileName) {
        return Paths.get(fileStoragePath, fileName);
    }
}
