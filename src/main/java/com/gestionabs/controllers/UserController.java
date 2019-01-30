package com.gestionabs.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gestionabs.Consts;
import com.gestionabs.beans.Administrator;
import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Student;
import com.gestionabs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestionabs.beans.User;
	
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/{userId}")
	public User getUserById(@PathVariable Integer userId) {
		return null;
	}
	
	@RequestMapping(method= RequestMethod.POST, value="/user")
	public void addUser(@RequestBody User user) {
		System.out.println(user);
	}
	
	@RequestMapping(method= RequestMethod.GET, value={"/login","/"})
	public String login(HttpServletRequest request, Model model, User user) {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			model.addAttribute("error", "already logged in");
			return "redirect:/" + Consts.homeController;
		}
		else {
			return "login";
		}
	}

	
	@RequestMapping(method= RequestMethod.POST, value="/login")
	public String doLogin(HttpServletRequest request, RedirectAttributes redirectAttributes, User user) {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			redirectAttributes.addAttribute("error", "already logged in");
		}
		else {
			if(userService.signInUser(user)) {
				session.setAttribute("user", userService.findUserByUsername(user.getUsername()));
			}
			else {
				redirectAttributes.addAttribute("error", true);
				return "redirect:/" + Consts.loginController;
			}
		}

		return "redirect:/" + Consts.homeController;
	}

	
	@RequestMapping(method= RequestMethod.GET, value="/logout")
	public String doLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!=null) {
			session.removeAttribute("user");
		}
		return "redirect:/" + Consts.homeController;
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/acceuil")
	public String home(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null)
			return "redirect:/"+ Consts.loginController;
		else if (user instanceof Professor)
			return "redirect:/"+ Consts.ProfessorHomeController;
		else if (user instanceof Administrator)
			return "redirect:/"+ Consts.AdminHomeController;
		else if (user instanceof Student)
			return "redirect:/"+ "student/"+user.getId();
		else{
			return Consts.ErrorPage;
		}
	}
	
}
