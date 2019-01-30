package com.gestionabs.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Student student;
    private String message;
    private  Boolean seen=false;
    private Date date = new Date();

    public Notification() {
    }

    public Notification(Student student, String message, Boolean seen) {
        this.student = student;
        this.message = message;
        this.seen = seen;
    }

    public Notification(Student student, String message) {
        this.student = student;
        this.message = message;
        this.seen = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
