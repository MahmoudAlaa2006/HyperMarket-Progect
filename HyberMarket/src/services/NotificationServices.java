package services;

import models.Notification;
import models.Product;

import java.util.ArrayList;
import java.util.List;

public class NotificationServices {
    private List<Notification> notifications = new ArrayList<>();

    public void send(Notification notification) {
        notifications.add(notification);
        System.out.println("[" + notification.getType() + "]" + notification.getMessage());
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}
