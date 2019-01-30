package com.gestionabs.services;

import com.gestionabs.beans.Group;
import com.gestionabs.beans.Notification;
import com.gestionabs.beans.Student;
import com.gestionabs.repositories.GroupRepository;
import com.gestionabs.repositories.NotificationRepository;
import com.gestionabs.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    StudentRepository studentRepository;
    public void notifyGroup(Group group, String notificationMsg){
        for(Student s : studentRepository.findByCurrentGroupId(group.getId())){
            Notification notification = new Notification(s,notificationMsg);
            notificationRepository.save(notification);
        }
    }

}
