package com.gestionabs.repositories;

import com.gestionabs.beans.Attendance;
import com.gestionabs.beans.AttendanceKey;
import com.gestionabs.beans.Notification;
import com.gestionabs.beans.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer>{
    List<Notification> findByStudentAndSeenOrderByDate(Student s, Boolean seen);
    List<Notification> findByStudentAndSeen(Student s, Boolean seen);
}
