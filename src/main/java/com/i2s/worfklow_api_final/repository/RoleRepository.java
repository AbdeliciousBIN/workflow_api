package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();

    Optional<Role> findById(long id);

    Optional<Role> findRoleByName(String roleName);

}
