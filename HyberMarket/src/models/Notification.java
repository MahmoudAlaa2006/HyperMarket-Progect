package models;

import java.time.LocalDateTime;

public class Notification {
    private String id;
    private NotificationType type;
    private String message;
    private LocalDateTime createdAt;


    public Notification(){

    }

    public Notification(String id, NotificationType type, String message){
        this.id = id;
        this.type = type;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public String getId(){
        return id;
    }

    public NotificationType getType(){
        return type;
    }

    public void setType(NotificationType newType){
        type = newType;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String newMessage){
        message = newMessage;
    }
}

