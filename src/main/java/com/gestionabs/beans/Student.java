package com.gestionabs.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("S")
public class Student extends User {


	@OneToMany(mappedBy = "key.student", cascade = CascadeType.ALL)
	private List<SubjectResult> subjectsResults;
	
	@ManyToOne
	@NotNull(message = "Veuillez choisir un groupe !")
	private Group currentGroup;
	
	@OneToMany(mappedBy = "key.student", fetch = FetchType.EAGER)
	private List<Attendance> attendances;
	
	@OneToMany(mappedBy="student")
	private List<StudentGroupHistory> groupesHistory;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private List<Notification> notifications;

	
	public Student(String username, String password, String nom, String prenom) {
		super(username, password, nom, prenom);
	}
	
	public Student(String username, String password, String nom, String prenom, Group currentGroup) {
		super(username, password, nom, prenom);
		this.currentGroup = currentGroup;
	}
	
	public Student(Integer id, String login, String password, String nom, String prenom) {
		super(id, login, password, nom, prenom);
	}

	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public List<SubjectResult> getSubjectsResults() {
		return subjectsResults;
	}

	public void setSubjectsResults(List<SubjectResult> subjectsResults) {
		this.subjectsResults = subjectsResults;
	}

	public Group getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(Group currentGroup) {
		this.currentGroup = currentGroup;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public List<StudentGroupHistory> getGroupesHistory() {
		return groupesHistory;
	}

	public void setGroupesHistory(List<StudentGroupHistory> groupesHistory) {
		this.groupesHistory = groupesHistory;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public void addNotification(Notification notification){
		if(notifications==null)
			notifications = new ArrayList<>(30);
		notifications.add(notification);
	}
}
