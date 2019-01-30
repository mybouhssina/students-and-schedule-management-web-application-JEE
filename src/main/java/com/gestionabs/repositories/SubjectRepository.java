package com.gestionabs.repositories;

import com.gestionabs.beans.Attendance;
import com.gestionabs.beans.AttendanceKey;
import com.gestionabs.beans.Subject;
import com.gestionabs.beans.SubjectResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer>{
    List<Subject> findByName(String name);
    List<Subject> findByNameAndIdNot(String name, Integer id);
}
