package com.gestionabs.controllers;

import com.gestionabs.Utils.DateUtils;
import com.gestionabs.beans.Session;
import com.gestionabs.repositories.*;
import com.gestionabs.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ScheduleController {

    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    GroupRepository groupeRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    SubjectRepository subjectRepository;


    @RequestMapping(method= RequestMethod.GET, value="/schedule")
    public String showStudentDetails(Session session, Model model, HttpServletRequest request, @RequestParam("groupId") Integer groupId, @RequestParam("week") Integer week) {
        ArrayList<ArrayList<Session>> weekProgram;
        Integer randomColor[][] = new Integer[5][4];
        String[] weekDays = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};
        //getting the student's week program, if we're in week end, then we're going to show to him next week's program.
        List<Session> thisWeekSessions ;
        if(week==1) {
            System.out.println(DateUtils.getTodayDayNumber());
            thisWeekSessions = sessionRepository.findByGroupAndStartingDateBetween(groupeRepository.findById(groupId).get(), DateUtils.getThisWeekMondayDate(), DateUtils.getThisWeekFridayDate());
            model.addAttribute("todayDayNumber",DateUtils.getTodayDayNumber());
            model.addAttribute("currentHour",DateUtils.getCurrentHour());
        }
        else {
            thisWeekSessions = sessionRepository.findByGroupAndStartingDateBetween(groupeRepository.findById(groupId).get(), DateUtils.getNextWeekMondayDate(), DateUtils.getNextWeekFridayDate());
            model.addAttribute("todayDayNumber",0);
            model.addAttribute("currentHour",0);
        }
        weekProgram = DateUtils.getWeekProgram(thisWeekSessions);
        for(int i = 0; i<5;i++){
            for(int j=0;j<4;j++){
                randomColor[i][j] = ThreadLocalRandom.current().nextInt(1, 5);
            }
        }
        model.addAttribute("teachers",professorRepository.findAll());
        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName",groupeRepository.findById(groupId).get().getName());
        model.addAttribute("week",week);
        model.addAttribute("subjects",subjectRepository.findAll());
        model.addAttribute("classrooms",classroomRepository.findAll());
        model.addAttribute("weekProgram",weekProgram);
        model.addAttribute("weekDays",weekDays);
        model.addAttribute("randomColor",randomColor);
        return "parts/manageSchedule";
    }

    // session num : 1 => session in this week, 2 => session in next week
    @RequestMapping(method= RequestMethod.POST, value="/schedule/addSession")
    public String addSession(@Valid  Session session, BindingResult bindingResult,  Model model, HttpServletRequest request, @RequestParam("groupId") Integer groupId, @RequestParam("week") Integer week, @RequestParam("day") Integer day, @RequestParam("hour") Integer hour) {
        Date sessionStartingDate;
        if(week==1){
            sessionStartingDate= DateUtils.getThisWeekDayDate(day,hour);
        }
        else{
            sessionStartingDate= DateUtils.getNextWeekDayDate(day,hour);
        }
        session.setGroup(groupeRepository.findById(groupId).get());
        session.setStartingDate(sessionStartingDate);

        if(session.getId()!=null){
            if(sessionRepository.findByTeacherAndStartingDateAndIdNot(session.getTeacher(),sessionStartingDate,session.getId()).size()>0){
                bindingResult.addError(new FieldError("session","professor","Ce professeur a déjà une session à cette heure"));
            }
            if(sessionRepository.findByClassRoomAndStartingDateAndIdNot(session.getClassRoom(),sessionStartingDate,session.getId()).size()>0){
                bindingResult.addError(new FieldError("session","classRoom","Cette salle est occupée à cette heure"));
            }
            if(
                    sessionRepository.findByGroupAndStartingDateAndIdNot(session.getGroup(),sessionStartingDate,session.getId()).size()>0){
                bindingResult.addError(new FieldError("session","startingDate","Ce groupe a déjà une séance à cette heure"));
            }
        }
        else{
            if(sessionRepository.findByTeacherAndStartingDate(session.getTeacher(),sessionStartingDate).size()>0){
                bindingResult.addError(new FieldError("session","professor","Ce professeur a déjà une session à cette heure"));
            }
            if(sessionRepository.findByClassRoomAndStartingDate(session.getClassRoom(),sessionStartingDate).size()>0){
                bindingResult.addError(new FieldError("session","classRoom","Cette salle est occupée à cette heure"));
            }
            if(
                    sessionRepository.findByGroupAndStartingDate(session.getGroup(),sessionStartingDate).size()>0){
                bindingResult.addError(new FieldError("session","startingDate","Ce groupe a déjà une séance à cette heure"));
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error",true);
        }
        else{
            sessionRepository.save(session);
            notificationService.notifyGroup(session.getGroup(),"Votre emploi a été modifié");
            model.addAttribute("session",new Session());
            model.addAttribute("success",true);
        }
        model.addAttribute("hour",hour);
        model.addAttribute("day",day);
        showStudentDetails(session,model,request,groupId,week);
        return "parts/manageSchedule";
    }

    @RequestMapping(method= RequestMethod.GET, value="/schedule/deleteSession")
    public String addSession(Session session,Model model, HttpServletRequest request, @RequestParam("groupId") Integer groupId, @RequestParam("week") Integer week, @RequestParam("sessionId") Integer sessionId) {
        sessionRepository.deleteById(sessionId);
        model.addAttribute("success");
        showStudentDetails(new Session(),model,request,groupId,week);
        return "parts/manageSchedule";
    }
}
