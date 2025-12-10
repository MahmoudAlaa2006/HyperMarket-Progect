package models;

import java.time.LocalDateTime;

public class Notification {
    private String id;
    private NotificationType type;
    private String message;
    // optional
    private LocalDateTime createAt;
    private boolean isRead;
}
