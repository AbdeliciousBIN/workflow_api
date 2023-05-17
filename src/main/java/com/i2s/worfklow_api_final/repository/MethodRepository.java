package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {
    List<Method> findAll();

    Optional<Method> findById(long id);
    Optional<Method> findByMethodName(String methodName);
}

