package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.NotificationDTO;
import com.i2s.worfklow_api_final.model.Notification;
import com.i2s.worfklow_api_final.model.User;
import com.i2s.worfklow_api_final.repository.NotificationRepository;
import com.i2s.worfklow_api_final.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(notification -> {
            NotificationDTO notificationDTO = modelMapper.map(notification, NotificationDTO.class);
            return notificationDTO;
        }).collect(Collectors.toList());
    }

    public Optional<NotificationDTO> getNotificationById(long id) {
        return notificationRepository.findById(id).map(notification -> {
            NotificationDTO notificationDTO = modelMapper.map(notification, NotificationDTO.class);
            return notificationDTO;
        });
    }

    public List<NotificationDTO> getNotificationsForUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
        List<Notification> notifications = notificationRepository.findByUser(user);

        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getUnreadNotificationsForUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
        List<Notification> notifications = notificationRepository.findByReadAndAndUser(false, user);

        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    public NotificationDTO markNotificationAsRead(long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new EntityNotFoundException("Notification with ID " + notificationId + " not found."));
        notification.setRead(true);
        Notification savedNotification = notificationRepository.save(notification);
        return modelMapper.map(savedNotification, NotificationDTO.class);
    }

}
