package com.gestionabs.repositories;

import com.gestionabs.beans.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectResultRepository extends CrudRepository<SubjectResult, SubjectResultKey>{
    List<SubjectResult> findByKeyStudent(Student student);
    Optional<SubjectResult> findByKeyStudentAndKeySubjectAndExamSession(Student student,Subject subject, Session session);
}
