package Dao;

import Dto.NotificationDTO;

import java.util.List;

public interface NotifyDao {
    void addNotification(int sellerId, String message);

    List<NotificationDTO> getUnreadNotifications(int sellerId);

    void markAsRead(int notificationId);
}
