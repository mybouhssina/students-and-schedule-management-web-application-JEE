package com.gestionabs.controllers;

import com.gestionabs.Consts;
import com.gestionabs.Utils.DateUtils;
import com.gestionabs.Utils.SecurityUtils;
import com.gestionabs.beans.*;
import com.gestionabs.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Controller
public class AdminController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    GroupRepository groupeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SubjectResultRepository subjectResultRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    @RequestMapping("/"+Consts.AdminHomeController)
    public String cPanel(){
        return "cPanel";
    }
    @GetMapping("/"+Consts.AdminHomeController +"/students")
    public String manageStudents(Student student, HttpServletRequest request ,Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Iterable<Student> allStudents = studentRepository.findAll();
        Iterable<Group> allGroups = groupeRepository.findAll();
        model.addAttribute("students",allStudents);
        model.addAttribute("groups",allGroups);
        model.addAttribute("student",student);
        return Consts.manageStudentsPage;
    }
    @PostMapping("/cpanel/students")
    public String addOrUpdateStudent(HttpServletRequest request, @Valid Student student, BindingResult bindingResult, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        // if the username is already used, we check if the id is already set (=> modification), if so, we verify if the new username is the same as the old one
        // if so, no error. if the id is null then it's a creation and not a modification operation
        if((userRepository.findByUsername(student.getUsername()).isPresent()) &&
                (student.getId()==null || !studentRepository.findById(student.getId()).get().getUsername().equals(student.getUsername()))){
            bindingResult.addError(new FieldError("student","username","Nom d'utilisateur déjà utilisé"));
        }
        if(student.getPassword()!=null && !(student.getPassword().equals(student.getPasswordConfirmation()))){
            bindingResult.addError(new FieldError("student","passwordConfirmation","Mauvaise confirmation du mot de passe"));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",true);
        }
        else{
            studentRepository.save(student);
            model.addAttribute("success",true);
        }
        manageStudents(student,request,model);
        return Consts.manageStudentsPage;
    }

    @GetMapping("/cpanel/deleteStudent")
    public String deleteStudent(Model model,HttpServletRequest request, @RequestParam(value = "studentId") Integer studentId){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student==null)
            return Consts.ErrorPage;
        studentRepository.delete(student);
        manageStudents(new Student(),request,model);
        model.addAttribute("success",true);
        return Consts.manageStudentsPage;
    }


    @GetMapping("/"+Consts.AdminHomeController +"/sessions")
    public String manageSessions(HttpServletRequest request, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Date weekMonday = DateUtils.getThisWeekMondayDate();
        Date weekFriday = DateUtils.getThisWeekFridayDate();
        Date nextWeekMonday = DateUtils.getNextWeekMondayDate();
        Date nextWeekFriday = DateUtils.getNextWeekFridayDate();
        model.addAttribute("weekMonday",weekMonday);
        model.addAttribute("weekFriday",weekFriday);
        model.addAttribute("nextWeekMonday",nextWeekMonday);
        model.addAttribute("nextWeekFriday",nextWeekFriday);
        return Consts.manageSessions;
    }

    @GetMapping("/"+Consts.AdminHomeController +"/professors")
    public String manageProfessors(Professor professor, HttpServletRequest request,Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Iterable<Professor> allprofessors = professorRepository.findAll();
        model.addAttribute("professors",allprofessors);
        model.addAttribute("professor",professor);
        return Consts.manageProfessorsPage;
    }

    @PostMapping("/cpanel/professors")
    public String addOrUpdateprofessor(HttpServletRequest request, @ModelAttribute("professor") @Valid Professor professor, BindingResult bindingResult, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        if((userRepository.findByUsernameAndIdNot(professor.getUsername(),professor.getId())).isPresent()){
            bindingResult.addError(new FieldError("student","username","Nom d'utilisateur déjà utilisé"));
        }
        if(professor.getPassword()!=null && !(professor.getPassword().equals(professor.getPasswordConfirmation()))){
            bindingResult.addError(new FieldError("student","passwordConfirmation","Mauvaise confirmation du mot de passe"));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",true);
        }
        else{
            professorRepository.save(professor);
            model.addAttribute("success",true);
        }
        manageProfessors(professor,request,model);
        return Consts.manageProfessorsPage;
    }

    @GetMapping("/cpanel/deleteProfessor")
    public String deleteProfessor(Model model,HttpServletRequest request, @RequestParam(value = "professorId") Integer professorId){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Professor professor = professorRepository.findById(professorId).orElse(null);
        if(professor==null)
            return Consts.ErrorPage;
        for(Subject s : professor.getTaughtSubjects())
            s.getTeachers().remove(professor);
        professorRepository.delete(professor);
        manageProfessors(new Professor(),request,model);
        model.addAttribute("success",true);
        return Consts.manageProfessorsPage;
    }

    @RequestMapping("/"+Consts.AdminHomeController +"/subjects")
    public String manageSubjects(Subject subject, HttpServletRequest request, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Iterable<Subject> allSubjects = subjectRepository.findAll();
        Iterable<Professor> allProfessors = professorRepository.findAll();
        model.addAttribute("subjects",allSubjects);
        model.addAttribute("professors",allProfessors);
        model.addAttribute("subject",subject);
        return Consts.manageSubjects;
    }

    @PostMapping("/cpanel/subjects")
    public String addOrUpdateSubject(HttpServletRequest request, @Valid Subject subject, BindingResult bindingResult, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        if((subject.getId()!=null && subject.getName()!=null && subjectRepository.findByNameAndIdNot(subject.getName(),subject.getId()).size()>0))
            bindingResult.addError(new FieldError("subject","name","Cette matière existe déjà"));
        else if(subject.getId() == null && subject.getName()!=null && subjectRepository.findByName(subject.getName()).size()>0)
            bindingResult.addError(new FieldError("subject","name","Cette matière existe déjà"));
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",true);
        }
        else{
            subjectRepository.save(subject);
            model.addAttribute("success",true);
        }
        return manageSubjects(subject,request,model);
    }

    @GetMapping("/cpanel/deleteSubject")
    public String deleteSubject(Model model,HttpServletRequest request, @RequestParam(value = "subjectId") Integer subjectId){
        Subject s = subjectRepository.findById(subjectId).orElse(null);
        if(!SecurityUtils.isAdmin(request) || s==null)
            return Consts.ErrorPage;
        subjectRepository.delete(s);
        model.addAttribute("success",true);
        return manageSubjects(new Subject(),request,model);

    }

    @GetMapping("/"+Consts.AdminHomeController +"/classrooms")
    public String manageClassrooms(Classroom classroom, HttpServletRequest request, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        Iterable<Classroom> allClassrooms = classroomRepository.findAll();
        model.addAttribute("classrooms",allClassrooms);
        model.addAttribute("classroom",classroom);
        return Consts.maangeClassrooms;
    }

    @PostMapping("/cpanel/classrooms")
    public String addOrUpdateClassroom(HttpServletRequest request, @Valid Classroom classroom, BindingResult bindingResult, Model model){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        if((classroom.getId()!=null && classroomRepository.findByNameAndIdNot(classroom.getName(),classroom.getId()).size()>0)
                ||
                classroom.getId()==null && classroomRepository.findByName(classroom.getName()).size()>0)
            bindingResult.addError(new FieldError("classroom","name","Il y a déjà une salle avec ce nom"));
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",true);
        }
        else{
            classroomRepository.save(classroom);
            model.addAttribute("success",true);
        }
        return manageClassrooms(classroom,request,model);
    }


    @GetMapping("/cpanel/deleteClassroom")
    public String deleteClassroom(Model model,HttpServletRequest request, @RequestParam(value = "classroomId") Integer classroomId){
        Classroom c = classroomRepository.findById(classroomId).orElse(null);
        if(!SecurityUtils.isAdmin(request) || c==null)
            return Consts.ErrorPage;
        classroomRepository.delete(c);
        model.addAttribute("success",true);
        return manageClassrooms(new Classroom(),request,model);

    }

    @RequestMapping("/"+Consts.AdminHomeController +"/attendance")
    public String pickAttendanceSession(Model model, HttpServletRequest request){
        if (!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        List<Session> sessions = sessionRepository.findByAttendanceChecked(true);
        model.addAttribute("sessions",sessions);
        return "cpanel/pickAttendanceSession";
    }

    @RequestMapping("/"+Consts.AdminHomeController +"/manageAttendance")
    public String manageAttendance(Model model, HttpServletRequest request, @RequestParam("sessionId") Integer sessionId){
        if (!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        List<Attendance> studentsAttendance  = attendanceRepository.findAllByKeySessionId(sessionId);
        model.addAttribute("studentsAttendance",studentsAttendance);
        model.addAttribute("sessionId",sessionId);
        return "cpanel/manageAttendance";
    }

    @RequestMapping("/"+Consts.AdminHomeController +"/saveAttendanceJustification")
    public String doSaveAttendanceJustification(Model model, HttpServletRequest request, @RequestParam(value = "sessionId") Integer sessionId, @RequestParam(value="student",required = false) List<Integer> studentsId){
        if(!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        List<Attendance> studentsAttendance  = attendanceRepository.findAllByKeySessionId(sessionId);
        if(studentsId==null) {
            for (Attendance studentAttendace : studentsAttendance) {
                studentAttendace.setIsJustified(false);
            }
        }
        else{
            for(Attendance studentAttendance : studentsAttendance){
                if(studentsId.contains(studentAttendance.getStudent().getId()))
                    studentAttendance.setIsJustified(true);
                else
                    studentAttendance.setIsJustified(false);
                attendanceRepository.save(studentAttendance);
            }
        }

        model.addAttribute("success",true);
        return manageAttendance(model,request,sessionId);
    }

    @GetMapping("/"+Consts.AdminHomeController +"/results")
    public String manageSubjectResults(Model model, HttpServletRequest request) {
        if (!SecurityUtils.isAdmin(request))
            return Consts.ErrorPage;
        else {
            List<Session> examSessions = sessionRepository.findByTypeAndStartingDateBefore("e", new Date());
            model.addAttribute("examSessions", examSessions);
            return "cpanel/" + Consts.PickExamSessionPage;
        }
    }

}
