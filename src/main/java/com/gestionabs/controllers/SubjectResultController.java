package com.gestionabs.controllers;

import com.gestionabs.Consts;
import com.gestionabs.Utils.DateUtils;
import com.gestionabs.Utils.SecurityUtils;
import com.gestionabs.beans.*;
import com.gestionabs.repositories.*;
import com.gestionabs.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class SubjectResultController {
    @Autowired
    SubjectResultRepository subjectResultRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/pickExamSession")
    public String pickExamSession(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (!(user instanceof Professor))
            return Consts.ErrorPage;
        else {
            Professor teacher = (Professor) user;
            List<Session> examSessions = sessionRepository.findByTeacherAndTypeAndStartingDateBefore(teacher, "e", new Date());
            model.addAttribute("examSessions", examSessions);
            return Consts.PickExamSessionPage;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/enterNotes")
    public String enterNotes(Model model, HttpServletRequest request, @RequestParam(value = "examSessionId") Integer examSessionId) {
        User user = (User) request.getSession().getAttribute("user");
        if (!(user instanceof Professor) && !SecurityUtils.isAdmin(user))
            return Consts.ErrorPage;
        else {
            Session examSession = sessionRepository.findById(examSessionId).get();
            List<Student> students = studentRepository.findByCurrentGroupId(examSession.getGroup().getId());
            List<SubjectResult> subjectResults = new ArrayList<>(30);
            for (Student s : students) {
                SubjectResult result = subjectResultRepository.findByKeyStudentAndKeySubjectAndExamSession(s,examSession.getSubject(), examSession).orElse(null);
                if (result != null)
                    subjectResults.add(result);
                else
                    subjectResults.add(new SubjectResult(s,examSession.getSubject(),examSession));
            }
            model.addAttribute("subjectResults", subjectResults);
            model.addAttribute("examSession", examSession);
            return Consts.EnterNotesPage;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/doEnterNotes")
    public String saveNotes(Model model, HttpServletRequest request, @RequestParam(value = "examSessionId") Integer examSessionId,@RequestParam(value = "mark") Float[] marks, @RequestParam(value = "studentId") Integer[] studentsId) {
        User user = (User) request.getSession().getAttribute("user");
        if (!(user instanceof Professor || SecurityUtils.isAdmin(user)) || marks.length != studentsId.length ) {
            return Consts.ErrorPage;
        }
        else {
            Session examSession = sessionRepository.findById(examSessionId).get();
            Subject subject = examSession.getSubject();
            for(int i=0; i<marks.length;i++){
                Student student = studentRepository.findById(studentsId[i]).get();
                subjectResultRepository.save(new SubjectResult(student,subject,marks[i],examSession));
            }
            notificationService.notifyGroup(examSession.getGroup(),"Les notes de la matière " + subject.getName() + " que vous avez passé le " +
                    examSession.getStartingDate().getDate() + "/" + examSession.getStartingDate().getMonth()+
                     " sont disponibles "
            );
            enterNotes(model,request,examSessionId);
            model.addAttribute("success",true);
            return Consts.EnterNotesPage;
        }
    }

}
