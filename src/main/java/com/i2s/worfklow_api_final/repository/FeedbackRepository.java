package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Feedback;
import com.i2s.worfklow_api_final.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAll();

    Optional<Feedback> findById(long id);
}
