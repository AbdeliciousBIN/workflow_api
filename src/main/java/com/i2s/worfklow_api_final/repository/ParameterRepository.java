package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    List<Parameter> findAll();

    Optional<Parameter> findById(long id);
}
