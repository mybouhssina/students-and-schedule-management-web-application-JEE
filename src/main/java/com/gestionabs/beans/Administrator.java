package com.gestionabs.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Administrator extends User {

	public Administrator() {}
	
	public Administrator(String username, String password, String nom, String prenom) {
		super(username, password, nom, prenom);
	}

}
