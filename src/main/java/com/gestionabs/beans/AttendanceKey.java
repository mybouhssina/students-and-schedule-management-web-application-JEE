package com.gestionabs.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class AttendanceKey implements Serializable {


    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JsonIgnore
    private Student student;
    @ManyToOne
    @JsonIgnore
    private Session session;



    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
