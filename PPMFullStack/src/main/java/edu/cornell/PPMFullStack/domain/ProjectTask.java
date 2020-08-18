package edu.cornell.PPMFullStack.domain;

import java.util.Date;

import javax.persistence.CascadeType;
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
    private Long idLong;

    @Column(updatable= false)
    private String projectSequenceString;

    @NotBlank(message= "Please include a project summary!")
    private String summary;

    private String acceptanceCriteria;

    private String statu;

    private Integer priority;

    // Many to one with BackLog
    @Column(updatable= false)
    private String projectIdentifier;

    private Date dueDate;

    private Date create_At;

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog= backlog;
    }

    private Date update_At;

    @ManyToOne(fetch= FetchType.EAGER, cascade= CascadeType.REFRESH)
    @JoinColumn(name= "backlog_id", updatable= false, nullable= false)
    @JsonIgnore
    private Backlog backlog;

    public ProjectTask() {
        super();
    }

    public Long getIdLong() {
        return idLong;
    }

    public void setIdLong(Long idLong) {
        this.idLong= idLong;
    }

    public String getProjectSequenceString() {
        return projectSequenceString;
    }

    public void setProjectSequenceString(String projectSequenceString) {
        this.projectSequenceString= projectSequenceString;
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

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu= statu;
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

    @PrePersist
    protected void onCreate() {
        create_At= new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        update_At= new Date();
    }

    @Override
    public String toString() {
        return "ProjectTask [idLong=" + idLong + ", projectSequenceString=" +
            projectSequenceString + ", summary=" + summary + ", acceptanceCriteria=" +
            acceptanceCriteria + ", statu=" + statu + ", priority=" + priority +
            ", projectIdentifier=" + projectIdentifier + ", dueDate=" + dueDate + ", create_At=" +
            create_At + ", update_At=" + update_At + "]";
    }

}
