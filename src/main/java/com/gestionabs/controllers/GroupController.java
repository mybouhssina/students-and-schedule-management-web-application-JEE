package com.gestionabs.controllers;

import com.gestionabs.Consts;
import com.gestionabs.Utils.DateUtils;
import com.gestionabs.beans.*;
import com.gestionabs.repositories.GroupRepository;
import com.gestionabs.repositories.SessionRepository;
import com.gestionabs.repositories.StudentRepository;
import com.gestionabs.repositories.SubjectResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class GroupController {
    @Autowired
    SubjectResultRepository subjectResultRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(method= RequestMethod.GET, value="/groupDetails/{groupId}")
    public String showGroupDetails(Model model, HttpServletRequest request, @PathVariable("groupId") Integer groupId){
        User user = (User)request.getSession().getAttribute("user");
        ArrayList<ArrayList<Session>> weekProgram;
        Integer randomColor[][] = new Integer[5][4];
        String[] weekDays = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};
        if(user!=null){
            Group group ;
            List<Student> groupStudents;
            Optional<Group> opt = groupRepository.findById(groupId);
            if(opt.isPresent()){
                group= opt.get();
                group.setStudents(studentRepository.findByCurrentGroupId(groupId));
            }
            else{
                return Consts.ErrorPage;
            }
            List<Session> thisWeekSessions = sessionRepository.findByGroupAndStartingDateBetween(group,DateUtils.getThisWeekMondayDateOrNextWeekIfItsWeekEnd(),DateUtils.getThisWeekFridayDateOrNextWeekIfItsWeekEnd());
            weekProgram = DateUtils.getWeekProgram(thisWeekSessions);
            for(int i = 0; i<5;i++){
                for(int j=0;j<4;j++){
                    randomColor[i][j] = ThreadLocalRandom.current().nextInt(1, 5);
                }
            }
            model.addAttribute("group",group);
            model.addAttribute("weekProgram",weekProgram);
            model.addAttribute("weekDays",weekDays);
            model.addAttribute("randomColor",randomColor);
            return Consts.GroupDetailsPage;
        }
        else{
            return Consts.ErrorPage;
        }
    }
}
