package com.gestionabs.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gestionabs.Consts;
import com.gestionabs.beans.Professor;
import com.gestionabs.beans.User;
import com.gestionabs.repositories.GroupRepository;
import com.gestionabs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class AttendanceController {
	@Autowired
	private GroupRepository groupeRepository;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/attendance")
	public String attendance(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return "redirect:/"+ Consts.loginController;
		}
		else {
			if(user instanceof Professor) {
				Professor responsible = (Professor) user;
				model.addAttribute("groupes", groupeRepository.findAll());
				return Consts.attendancePageName;
			}
			else {
				return "redirect:/"+ Consts.loginController;
			}
		}
		
	}
}
