package br.com.libdolf.sgtesig.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String assignee;
    private Date deadline;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void update(Task taskUpdated) {
        this.title = taskUpdated.getTitle();
        this.description = taskUpdated.getDescription();
        this.assignee = taskUpdated.getAssignee();
        this.deadline = taskUpdated.getDeadline();
        this.status = taskUpdated.getStatus();
        this.priority = taskUpdated.getPriority();
    }
}
