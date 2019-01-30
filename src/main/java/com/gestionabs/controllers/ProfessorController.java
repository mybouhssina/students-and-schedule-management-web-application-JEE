package com.gestionabs.controllers;

import com.gestionabs.Consts;
import com.gestionabs.Utils.DateUtils;
import com.gestionabs.beans.*;
import com.gestionabs.repositories.GroupRepository;
import com.gestionabs.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ProfessorController {
    @Autowired
    GroupRepository groupeRepository;
    @Autowired
    SessionRepository sessionRepository;

    @RequestMapping(method= RequestMethod.GET, value="/teacher/{teacherId}")
    public String showProfessorDetails(Model model, HttpServletRequest request, @PathVariable("teacherId") Integer teacherId, RedirectAttributes redirectAttributes){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return Consts.ErrorPage;
        }
        if(user instanceof Professor && user.getId()==teacherId){
            Professor teacher = (Professor) user;
            ArrayList<ArrayList<Session>> weekProgram;
            Integer randomColor[][] = new Integer[5][4];
            String[] weekDays = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};
            //getting the student's week program, if we're in week end, then we're going to show to him next week's program.
            List<Session> thisWeekSessions ;
            // manually fetching the teacher groups
            teacher.setGroupesResponsible(groupeRepository.findByResponsible(teacher));
            thisWeekSessions = sessionRepository.findByTeacherAndStartingDateBetween(teacher, DateUtils.getThisWeekMondayDateOrNextWeekIfItsWeekEnd(), DateUtils.getThisWeekFridayDateOrNextWeekIfItsWeekEnd());

            weekProgram = DateUtils.getWeekProgram(thisWeekSessions);
            for(int i = 0; i<5;i++){
                for(int j=0;j<4;j++){
                    randomColor[i][j] = ThreadLocalRandom.current().nextInt(1, 5);
                }
            }
            model.addAttribute("teacher",teacher);
            model.addAttribute("weekProgram",weekProgram);
            model.addAttribute("weekDays",weekDays);
            model.addAttribute("randomColor",randomColor);
            return Consts.TeacherDetailsPage;
        }
        else{
            return Consts.ErrorPage;
        }
    }
    @RequestMapping(method= RequestMethod.GET, value="/myGroups")
    public String showProfessorGroups(Model model, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null || !(user instanceof Professor) ){
            return Consts.ErrorPage;
        }
        Professor teacher = (Professor) user;
        List<Group> teacherGroups = groupeRepository.findByResponsible(teacher);
        model.addAttribute("teacherGroups",teacherGroups);
        return Consts.teacherGroupsPage;
    }
}
