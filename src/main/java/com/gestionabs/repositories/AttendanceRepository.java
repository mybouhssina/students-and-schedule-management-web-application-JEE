package com.gestionabs.repositories;

import com.gestionabs.beans.Attendance;
import com.gestionabs.beans.AttendanceKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, AttendanceKey>{
	void deleteAllByKeySessionId (Integer sessionId);
	List<Attendance> findAllByKeySessionId(Integer sessionId);

}
