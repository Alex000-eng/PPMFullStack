package edu.cornell.PPMFullStack.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(updatable= false, unique= true)
    private String projectSequence;

    @NotBlank(message= "Please include a project summary!")
    private String summary;

    private String acceptanceCriteria;

    private String status;

    private Integer priority;

    // Many to one with BackLog
    @Column(updatable= false)
    private String projectIdentifier;

    private Date dueDate;

    private Date create_At;

    private Date update_At;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name= "backlog_id", updatable= false, nullable= false)
    @JsonIgnore
    private Backlog backlog;

    public ProjectTask() {
        super();
    }

    @PrePersist
    protected void onCreate() {
        create_At= new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        update_At= new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id= id;
    }

    public String getProjectSequence() {
        return projectSequence;
    }

    public void setProjectSequence(String projectSequence) {
        this.projectSequence= projectSequence;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary= summary;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria= acceptanceCriteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status= status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority= priority;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier= projectIdentifier;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate= dueDate;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At= create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At= update_At;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog= backlog;
    }

    @Override
    public String toString() {
        return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" +
            summary + ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status +
            ", priority=" + priority + ", projectIdentifier=" + projectIdentifier + ", dueDate=" +
            dueDate + ", create_At=" + create_At + ", update_At=" + update_At + ", backlog=" +
            backlog + "]";
    }

}
