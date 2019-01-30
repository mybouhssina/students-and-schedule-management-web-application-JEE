package com.gestionabs.repositories;

import java.util.List;
import java.util.Optional;

import com.gestionabs.beans.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>{
	List<Student> findByCurrentGroupId(Integer id);
	Optional<Student> findByUsername(String username);
}
