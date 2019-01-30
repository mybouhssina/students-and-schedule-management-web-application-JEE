package com.gestionabs.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


// bug after changing the class name from group to group, have to keep the old entity name
@Entity(name="groupe")
public class Group {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private int grade ;
	
	@OneToMany(mappedBy = "currentGroup")
	@JsonBackReference
	private List<Student> students;
	
	
	@OneToMany(mappedBy="group")
	private List<Session> sessions;
	
	@OneToMany(mappedBy="group")
	private List<StudentGroupHistory> groupesHistory;
	
	@ManyToOne
	private Professor responsible;
	
	
	public Group() {
		
	}
	
	public Group(String name) {
		super();
		this.name = name;
	}
	
	
	public Group(String name, Professor responsible) {
		super();;
		this.name = name;
		this.responsible= responsible;
		sessions= new ArrayList<>();
		students = new ArrayList<>();
		grade = 1;
	}

	public Group(String name, Professor responsible, Integer grade) {
		super();;
		this.name = name;
		this.responsible= responsible;
		sessions= new ArrayList<>();
		students = new ArrayList<>();
		this.grade=grade;
	}
	
	public Group(String nom, List<Student> students) {
		super();
		this.name = nom;
		this.students=students;
		grade=1;
	}
	
	public final Integer getId() {
		return id;
	}
	public final void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<StudentGroupHistory> getGroupesHistory() {
		return groupesHistory;
	}

	public void setGroupesHistory(List<StudentGroupHistory> groupesHistory) {
		this.groupesHistory = groupesHistory;
	}

	public Professor getResponsible() {
		return responsible;
	}

	public void setResponsible(Professor responsible) {
		this.responsible = responsible;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getGradeName(){
		if(grade==1)
			return "1er année";
		else
			return grade + "ème année";

	}
}
