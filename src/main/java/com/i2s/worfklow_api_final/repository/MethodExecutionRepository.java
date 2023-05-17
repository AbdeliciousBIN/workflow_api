package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.MethodExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodExecutionRepository extends JpaRepository<MethodExecution, Long> {
}
