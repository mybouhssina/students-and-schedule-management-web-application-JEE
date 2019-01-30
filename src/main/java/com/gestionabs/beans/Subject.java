package com.gestionabs.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Size(min = 5, max=100)
	private String name;
	@OneToMany (mappedBy="subject", cascade = CascadeType.REMOVE)
	private List<Session> sessions;
	@OneToMany(mappedBy = "key.subject", cascade = CascadeType.REMOVE)
	private List<SubjectResult> subjectResults;
	@ManyToMany
	private List<Professor> teachers ;
	
	public Subject() {}

	public Subject(String name) {
		this.name= name;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Session> getSessions() {
		return sessions;
	}
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	public List<SubjectResult> getSubjectResults() {
		return subjectResults;
	}
	public void setSubjectResults(List<SubjectResult> subjectResults) {
		this.subjectResults = subjectResults;
	}

	public List<Professor> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Professor> teachers) {
		this.teachers = teachers;
	}

	public void addTeacher(Professor teacher){
		if(teachers==null)
			teachers = new ArrayList<>(1);
		teachers.add(teacher);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Subject subject = (Subject) o;
		return Objects.equals(id, subject.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
