package com.example.edo.repositories;

import com.example.edo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    @Query("SELECT CASE WHEN t.files.uniqueGroupCode IS NULL THEN 'отсутствует' ELSE t.files.uniqueGroupCode END, t FROM Task t")
//    List<Task> findAllTasksWithReplacedNull();
    List<Task> findAll();

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findAllByUserId(@Param("userId") Long userId);

    Optional<Task> findTaskById(Long taskId);

    void deleteById(Long id);

    @Query("SELECT t FROM Task t WHERE t.files.uniqueGroupCode = :uniqueGroupCode")
    Optional<Task> findByUniqueGroupCode(@Param("uniqueGroupCode") String uniqueGroupCode);
}
