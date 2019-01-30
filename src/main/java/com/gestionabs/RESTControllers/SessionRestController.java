package com.gestionabs.RESTControllers;

import java.util.Date;
import java.util.List;

import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Session;
import com.gestionabs.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SessionRestController {
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@RequestMapping("/REST/group/{groupId}/sessions")
	public Session getSessionsByGroupeAndTeacher(@PathVariable("groupId") int groupId,HttpServletRequest request){
		Professor teacher = (Professor) request.getSession().getAttribute("user");
		List<Session> allSessions = sessionRepository.findByGroupIdAndTeacher(groupId,teacher);
		Date now = new Date();
		Session currentSession = null;
		for(Session s : allSessions){
			Date startingTimePlus2h  = new Date();
			int twoHoursInMs = 1000 * 60 * 60 * 2;
			startingTimePlus2h.setTime(s.getStartingDate().getTime() + twoHoursInMs);
			if(now.compareTo(s.getStartingDate()) >= 0 && now.compareTo(startingTimePlus2h)  <= 0 ){
				startingTimePlus2h.setTime(s.getStartingDate().getTime() + 1000 * 60 * 60 * 2);
				currentSession = s;
			}
		}

		if(currentSession!=null) {
			Session session = new Session();
			currentSession.setGroup(null);
			// sending only required data... json infinite boucle problem...
			session.setId(currentSession.getId());
			session.setStartingDate(currentSession.getStartingDate());
			return session;
		}

		 return null;
	}
}
