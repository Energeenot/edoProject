package com.example.edo.repositories;

import com.example.edo.models.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {
    @Query("SELECT f.uniqueName FROM Files f WHERE f.uniqueGroupCode = :uniqueGroupCode")
    List<String> findAllUniqueNamesByUniqueGroupCode(@Param("uniqueGroupCode") String uniqueGroupCode);

    @Query("SELECT f.uniqueName FROM Files f WHERE f.uniqueGroupCode = :uniqueGroupCode")
    String findUniqueNameByUniqueGroupCode(@Param("uniqueGroupCode") String uniqueGroupCode);


}
