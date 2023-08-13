package ca.admin.delivermore.collector.data.entity;

import ca.admin.delivermore.collector.data.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "SchedulerEvent")
public class SchedulerReportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Transient
    private Logger log = LoggerFactory.getLogger(SchedulerReportEvent.class);

    @Enumerated(EnumType.STRING)
    @NotNull
    private Utility.EventType type = Utility.EventType.SHIFT;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    @NotNull
    private String resourceId;

    @NotNull
    private Boolean published = false;

    @NotNull
    private Boolean fullDay = false;

    public Utility.EventType getType() {
        return type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getResourceId() {
        return resourceId;
    }

    public Boolean getPublished() {
        return published;
    }

    public Boolean getFullDay() {
        return fullDay;
    }

    public String formatForReport(){
        String subject = "- ";
        subject += this.getType().typeName;
        if(this.getFullDay()){
            subject += " " + this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }else{
            String timeTitle = "";
            timeTitle = this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mm"));
            timeTitle += " - " + this.end.format(DateTimeFormatter.ofPattern("h:mm"));
            subject += " " + timeTitle;
        }
        return subject;
    }

}
