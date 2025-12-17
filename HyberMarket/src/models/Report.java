package models;

import utils.IDGenerator;

import java.time.LocalDate;

public class Report {
    private String id;
    private String title;
    private String message;


    public Report(){

    }

    public Report(String title, String message){
        this.id = IDGenerator.generateReportId();
        this.title = title;
        this.message = message;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String newMessage){
        message = newMessage;
    }

}
