package com.gestionabs.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;

import javax.persistence.*;

@Entity

public class Attendance implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*@Id
	@ManyToOne
	private Student student;
	@Id
	@ManyToOne
	private Session session;*/
	@EmbeddedId
	private AttendanceKey key = new AttendanceKey();
	private Boolean isAbsent;
	private Boolean isJustified;


	public Attendance(){

	}

	public Attendance(Student student, Session session, boolean isAbsent, boolean isJustified){
		this.key.setStudent(student);
		this.key.setSession(session);
		this.isAbsent=isAbsent;
		this.isJustified=isJustified;
	}

	@JsonManagedReference
	public Student getStudent() {
		return key.getStudent();
	}
	@JsonManagedReference
	public void setStudent(Student student) {
		key.setStudent(student);
	}


	public AttendanceKey getKey() {
		return key;
	}

	public void setKey(AttendanceKey key) {
		this.key = key;
	}
	@JsonManagedReference
	public Session getSession() {
		return key.getSession();
	}
	@JsonManagedReference
	public void setSession(Session session) {
		key.setSession(session);
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Boolean getIsAbsent() {
		return isAbsent;
	}


	public void setIsAbsent(Boolean isAbsent) {
		this.isAbsent = isAbsent;
	}


	public Boolean getIsJustified() {
		return isJustified;
	}


	public void setIsJustified(Boolean isJustified) {
		this.isJustified = isJustified;
	}

	
	
	
}
