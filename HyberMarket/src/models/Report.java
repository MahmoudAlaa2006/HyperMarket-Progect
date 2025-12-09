package models;

import java.time.LocalDate;
import java.util.Date;

public class Report {
    private String id;
    private String title;
    private ReportType type;
    private String generatedBy;
    private Date date;
    private String content;
    private String queryUsed;
}
