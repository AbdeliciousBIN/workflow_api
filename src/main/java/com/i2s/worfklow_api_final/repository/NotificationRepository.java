package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Notification;
import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAll();

    Optional<Notification> findById(long id);
    List<Notification> findByUser(User user);
    List<Notification> findByReadAndAndUser(boolean read, User user);
}
