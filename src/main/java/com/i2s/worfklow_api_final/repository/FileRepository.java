package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAll();

    Optional<File> findById(long id);

    @Query("SELECT f FROM File f JOIN f.tasks t JOIN t.step s JOIN s.phase p JOIN p.project pr WHERE pr.id = :projectId")
    List<File> findByProject(@Param("projectId") long projectId);
}

