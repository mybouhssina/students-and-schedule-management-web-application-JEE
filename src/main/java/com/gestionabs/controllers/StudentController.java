package com.gestionabs.controllers;

import com.gestionabs.Consts;
import com.gestionabs.Utils.DateUtils;
import com.gestionabs.beans.Notification;
import com.gestionabs.beans.Session;
import com.gestionabs.beans.Student;
import com.gestionabs.beans.User;
import com.gestionabs.repositories.NotificationRepository;
import com.gestionabs.repositories.SessionRepository;
import com.gestionabs.repositories.StudentRepository;
import com.gestionabs.repositories.SubjectResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class StudentController {
    @Autowired
    SubjectResultRepository subjectResultRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(method= RequestMethod.GET, value="/student/{studentId}")
    public String showStudentDetails(Model model, HttpServletRequest request, @PathVariable("studentId") Integer studentId, RedirectAttributes redirectAttributes){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return Consts.ErrorPage;
        }
        else{
            Student student = studentRepository.findById(studentId).get();
            ArrayList<ArrayList<Session>> weekProgram;
            Integer randomColor[][] = new Integer[5][4];
            String[] weekDays = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};
            // manually fetching the subject results, because it's lazily fetched
            student.setSubjectsResults(subjectResultRepository.findByKeyStudent(student));
            //getting the student's week program, if we're in week end, then we're going to show to him next week's program.
            List<Session> thisWeekSessions = sessionRepository.findByGroupAndStartingDateBetween(student.getCurrentGroup(),DateUtils.getThisWeekMondayDateOrNextWeekIfItsWeekEnd(),DateUtils.getThisWeekFridayDateOrNextWeekIfItsWeekEnd());
            weekProgram = DateUtils.getWeekProgram(thisWeekSessions);
            for(int i = 0; i<5;i++){
                for(int j=0;j<4;j++){
                    randomColor[i][j] = ThreadLocalRandom.current().nextInt(1, 5);
                }
            }
            model.addAttribute("student",student);
            model.addAttribute("weekProgram",weekProgram);
            model.addAttribute("weekDays",weekDays);
            model.addAttribute("randomColor",randomColor);
            model.addAttribute("newNotifications",notificationRepository.findByStudentAndSeenOrderByDate(student,false));
            return Consts.StudentDetailsPage;
        }
    }

}
