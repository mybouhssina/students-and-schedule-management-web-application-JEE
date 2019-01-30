package com.gestionabs.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubjectResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private SubjectResultKey key = new SubjectResultKey();
	private Float mark;
	@ManyToOne
	private Session examSession;
	
	public SubjectResult(Student student, Subject subject) {
		super();
		this.key.setStudent(student);
		this.key.setSubject(subject);
	}

	public SubjectResult(Student student, Subject subject, Float mark, Session examSession) {
		super();
		this.key.setStudent(student);
		this.key.setSubject(subject);
		this.mark = mark;
		this.examSession = examSession;
	}

	public SubjectResult(Student student, Subject subject,Session examSession) {
		super();
		this.key.setStudent(student);
		this.key.setSubject(subject);
		this.examSession = examSession;
	}



	
	public SubjectResult() {
		// TODO Auto-generated constructor stub
	}

	public User getStudent() {
		return key.getStudent();
	}

	public void setStudent(Student student) {
		this.key.setStudent(student);
	}

	public Subject getSubject() {
		return key.getSubject();
	}

	public void setSubject(Subject subject) {
		this.key.setSubject(subject);
	}


	public Float getMark() {
		return mark;
	}

	public void setMark(Float mark) {
		this.mark = mark;
	}


	public SubjectResultKey getKey() {
		return key;
	}

	public void setKey(SubjectResultKey key) {
		this.key = key;
	}

	public Date getDateExam() {
		return examSession.getStartingDate();
	}

	public void setDateExam(Date dateExam) {
		examSession.setStartingDate(dateExam);
	}
}
