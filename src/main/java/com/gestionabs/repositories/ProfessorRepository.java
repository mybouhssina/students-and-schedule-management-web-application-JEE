package com.gestionabs.repositories;

import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer>{
}
