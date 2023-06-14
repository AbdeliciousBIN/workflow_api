package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(long id);
    Optional<User> findByFullName(String fullName);
    @Query("SELECT count(u) FROM User u")
    long countUsers();


    Optional<User> findByEmail(String email);
}
