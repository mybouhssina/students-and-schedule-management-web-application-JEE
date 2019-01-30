package com.gestionabs.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Session implements Comparable<Session>  {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Date startingDate = new Date();
	private Boolean attendanceChecked;

	@OneToMany(mappedBy = "key.session")
    @JsonBackReference
	private List<Attendance> attendances;

	@OneToMany(mappedBy = "examSession", cascade = CascadeType.REMOVE)
	private List<SubjectResult> subjectResults;

	@ManyToOne
	@NotNull
	private Classroom classRoom;

	@ManyToOne
	private Group group;
	
	@ManyToOne
	@NotNull
	private Subject subject;

	@ManyToOne
	@NotNull
	private Professor teacher;

	@JsonIgnore
	@NotNull
	private String type ;

	
	public Session() {
		super();

	}
	
	public Session( Boolean attendanceChecked, Group group) {
		super();
		this.attendanceChecked = attendanceChecked;
		this.group = group;
	}

	public Session(Date startingDate, Boolean attendanceChecked, Group group,Subject subject, Classroom classRoom,Professor teacher, String type) {
		super();
		this.startingDate=startingDate;
		this.attendanceChecked = attendanceChecked;
		this.group = group;
		this.subject= subject;
		this.classRoom=classRoom;
		this.teacher= teacher;
		this.type=type;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public final Integer getId() {
		return id;
	}
	public final void setId(Integer id) {
		this.id = id;
	}


	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Boolean getAttendanceChecked() {
		return attendanceChecked;
	}

	public void setAttendanceChecked(Boolean attendanceChecked) {
		this.attendanceChecked = attendanceChecked;
	}


	public List<Attendance> getAttendances() {
		return attendances;
	}


	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	@Override
	public String toString() {
		return "Session{" +
				"id=" + id +
				", startingDate=" + startingDate +
				", attendanceChecked=" + attendanceChecked +
				", attendances=" + attendances +
				", classRoom=" + classRoom +
				", group=" + group +
				", subject=" + subject +
				", teacher=" + teacher +
				", type='" + type + '\'' +
				'}';
	}

	@Override
	public int compareTo(Session o) {
		return getStartingDate().compareTo(o.getStartingDate());
	}


	public Date getEndDate(){
		Date endDate = new Date();
		int twoHours = 1000 * 60 * 60 * 2;
		endDate.setTime(startingDate.getTime()+twoHours);
		return endDate;
	}
		public Classroom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(Classroom classRoom) {
		this.classRoom = classRoom;
	}

	public Professor getTeacher() {
		return teacher;
	}

	public void setTeacher(Professor teacher) {
		this.teacher = teacher;
	}

	@JsonIgnore
	public String getTypeAbr(){
		if(type==null)
			return "pas de type précis";
		else if(type.equals("c"))
			return "Cours";
		else if(type.equals("tp"))
			return "TP";
		else if(type.equals("td"))
			return "TD";
		else
			return "EXAM";
	}

	public String getType(){
		if(type==null)
			return "";
		if(type.equals("c"))
			return "Cours";
		else if(type.equals("tp"))
			return "Travaux pratiques";
		else if(type.equals("td"))
			return "Travaux dirigés";
		else
			return "Examen";
	}

	public String getTypeRealValue(){
		return type;
	}

	public void setType(String type){
		this.type=type;
	}

	public List<SubjectResult> getSubjectResults() {
		return subjectResults;
	}

	public void setSubjectResults(List<SubjectResult> subjectResults) {
		this.subjectResults = subjectResults;
	}

	public void addSubjectResult(SubjectResult subjectResult){
		subjectResults.add(subjectResult);
	}

	public void addSubjectResult(List<SubjectResult> subjectResults){
		this.subjectResults.addAll(subjectResults);
	}
}
