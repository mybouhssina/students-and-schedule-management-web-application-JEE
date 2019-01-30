package com.gestionabs.repositories;

import com.gestionabs.beans.Attendance;
import com.gestionabs.beans.AttendanceKey;
import com.gestionabs.beans.Classroom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends CrudRepository<Classroom, Integer>{
    List<Classroom> findByNameAndIdNot(String name, Integer id);
    List<Classroom> findByName(String name);
}
