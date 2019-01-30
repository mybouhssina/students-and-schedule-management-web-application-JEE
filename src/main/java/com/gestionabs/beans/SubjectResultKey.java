package com.gestionabs.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class SubjectResultKey implements Serializable {


    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Subject subject;


    public User getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
