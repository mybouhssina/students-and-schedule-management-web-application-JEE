package com.gestionabs.beans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance
@DiscriminatorColumn(name="PROJ_TYPE")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	@JsonIgnore

	@NotNull
	@Column(unique=true)
	@Size(min=5,max=30, message = "la taille du nom d'utilisateur doit être compris entre 5 et 30 caractères")
	protected String username;
	@JsonIgnore
	@NotNull
	@Size(min=1,max=30, message = "la taille du mot de passe doit être compris entre 1 et 30 caractères")
	protected String password;

	@Transient
	protected String passwordConfirmation;
	@NotNull
	@Size(min=4,max=30, message = "la taille du nom doit être compris entre 4 et 30 caractères")
	protected String lName;
	@NotNull
	@Size(min=4,max=30, message = "la taille du prénom doit être compris entre 4 et 30 caractères")
	protected String fName;

	public User() {};
	
	public User(String username, String password, String lName, String fName) {
		super();
		this.id = null;
		this.username = username;
		this.password = password;
		this.lName = lName;
		this.fName = fName;
	}
	
	public User(Integer id, String username, String password, String lName, String fName) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.lName = lName;
		this.fName = fName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLName() {
		return lName;
	}
	public void setLName(String lName) {
		this.lName = lName;
	}
	public String getFName() {
		return fName;
	}
	public void setFName(String fName) {
		this.fName = fName;
	}
	public String getFullName(){
		return lName + " " + fName;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", lName=" + lName + ", fName=" + fName
				+  "]";
	}
	
	
}
