package com.gestionabs.RESTControllers;

import java.util.List;

import com.gestionabs.beans.Notification;
import com.gestionabs.beans.Session;
import com.gestionabs.beans.Student;
import com.gestionabs.beans.User;
import com.gestionabs.repositories.NotificationRepository;
import com.gestionabs.repositories.SessionRepository;
import com.gestionabs.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class StudentRestController {
	
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	
	@RequestMapping("/REST/session/{sessionId}/students")
	public List<Student> getStudentBySession(@PathVariable("sessionId") int sessionId){
		Session session = sessionRepository.findById(sessionId).orElse(null);
		List<Student> students = studentRepository.findByCurrentGroupId(session.getGroup().getId());
		for(Student s : students) {
			s.setAttendances(null);
			s.setCurrentGroup(null);
			s.setSubjectsResults(null);
			s.setGroupesHistory(null);
			s.setNotifications(null);
		}
		return students;
	}
	@RequestMapping(method= RequestMethod.POST, value="/student/markAllNotificationsAsRead")
	public void markAllNotificationsAsRead(HttpServletRequest request, @RequestParam(value = "studentId") Integer studentId){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user!=null && user.getId()==studentId){
			for(Notification n : notificationRepository.findByStudentAndSeen((Student)user,false)){
				n.setSeen(true);
				notificationRepository.save(n);
			}
		}
	}
}
