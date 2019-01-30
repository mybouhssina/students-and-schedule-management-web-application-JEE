package com.gestionabs.services;

import com.gestionabs.beans.Administrator;
import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Student;
import com.gestionabs.beans.User;
import com.gestionabs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userDAO;
	
	public User findUserByUsername(String username) {
			return userDAO.findByUsername(username).orElse(null);
	}
	
	public void addUser(User user) {
		userDAO.save(user);
	}
	
	public boolean signInUser(User user) {
		User userDb = findUserByUsername(user.getUsername());
		return userDb !=null && user!=null && userDb.getPassword().equals(user.getPassword());
	}

	public boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user != null && user instanceof Administrator;
	}
	public boolean isProfessor(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user != null && user instanceof Professor;
	}
	public boolean isStudent(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user != null && user instanceof Student;
	}

	public boolean isLoggedIn(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user != null ;
	}
}
