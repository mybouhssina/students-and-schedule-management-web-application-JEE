package com.gestionabs.repositories;

import java.util.Date;
import java.util.List;

import com.gestionabs.beans.Classroom;
import com.gestionabs.beans.Group;
import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer>{
	List<Session> findByGroupId(Integer id);
	List<Session> findByGroupIdAndTeacher(Integer id,Professor teacher);
	List<Session> findByGroupAndStartingDateBetween(Group group, Date startingDate, Date endingDate);
	List<Session> findByGroupAndStartingDate(Group group, Date startingDate);
	List<Session> findByGroupAndStartingDateAndIdNot(Group group, Date startingDate,Integer id);
	List<Session> findByStartingDate(Date startingDate);
	List<Session> findByTeacherAndStartingDateBetween(Professor teacher, Date startingDate, Date endingDate);
	List<Session> findByTeacherAndStartingDate(Professor teacher, Date startingDate);
	List<Session> findByTeacherAndStartingDateAndIdNot(Professor teacher, Date startingDate,Integer id);
	List<Session> findByClassRoomAndStartingDate(Classroom classroom, Date startingDate);
	List<Session> findByClassRoomAndStartingDateAndIdNot(Classroom classroom, Date startingDate,Integer id);
	List<Session> findByTeacherAndTypeAndStartingDateBefore(Professor teacher, String type,Date date);
	List<Session> findByTypeAndStartingDateBefore(String type,Date date);
	List<Session> findByAttendanceChecked(boolean isChecked);

}
