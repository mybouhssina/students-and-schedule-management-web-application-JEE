package com.gestionabs.RESTControllers;

import com.gestionabs.beans.Group;
import com.gestionabs.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GroupRestController {

	@Autowired
	private GroupRepository groupeRepository;
	
	@RequestMapping("/REST/grade/{grade}/groups")
	public List<Group> getGroupsByGrade(@PathVariable("grade") int grade){
		List<Group> groups = groupeRepository.findByGrade(grade);
		for(Group g : groups){
			g.setGroupesHistory(null);
			g.setStudents(null);
			g.setSessions(null);
			g.setResponsible(null);
		}
		return groups;
	}
}
