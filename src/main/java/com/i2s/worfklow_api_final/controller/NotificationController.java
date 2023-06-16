package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.NotificationDTO;
import com.i2s.worfklow_api_final.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/notifications")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/user/notifications/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable long id) {
        return notificationService.getNotificationById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/notifications/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(@PathVariable long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @GetMapping("/user/notifications/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotificationsForUser(@PathVariable long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsForUser(userId));
    }

    @PostMapping("/user/notifications/{id}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable long id) {
        try {
            return ResponseEntity.ok(notificationService.markNotificationAsRead(id));
        } catch (
                DataIntegrityViolationException e) {
            // handle database constraint violation error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }

    }
}
