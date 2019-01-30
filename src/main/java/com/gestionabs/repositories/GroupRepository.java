package com.gestionabs.repositories;

import java.util.List;

import com.gestionabs.beans.Group;
import com.gestionabs.beans.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer>{
	List<Group> findByResponsible(Professor responsible);
	List<Group> findByGrade(Integer grade);
}
