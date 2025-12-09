package models;

import java.time.LocalDateTime;
import java.util.Date;

public class Notification {
    private String id;
    private NotificationType type;
    private String message;
    private Date createAt;
    private boolean isRead;
}
