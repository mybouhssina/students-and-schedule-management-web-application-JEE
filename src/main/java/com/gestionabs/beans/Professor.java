package com.gestionabs.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@DiscriminatorValue("P")
public class Professor extends User {
	
	@OneToMany(mappedBy="responsible")
	private List<Group> groupesResponsible;
	@ManyToMany(mappedBy = "teachers")
	private List<Subject> taughtSubjects;

	@ManyToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
	private List<Session> sessions;

	public Professor() {
		
	}
	
	public Professor(String username, String password, String nom, String prenom) {
		super(username, password, nom, prenom);
	}

	public List<Group> getGroupesResponsible() {
		return groupesResponsible;
	}

	public void setGroupesResponsible(List<Group> groupesResponsible) {
		this.groupesResponsible = groupesResponsible;
	}

	public List<Subject> getTaughtSubjects() {
		return taughtSubjects;
	}

	public void setTaughtSubjects(List<Subject> taughtSubjects) {
		this.taughtSubjects = taughtSubjects;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void addTaughtSubject(Subject subject){
		if(taughtSubjects==null){
			taughtSubjects=new ArrayList<>();
		}
		taughtSubjects.add(subject);
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Professor professor = (Professor) o;
		return professor.getId().equals(id);
	}

}
