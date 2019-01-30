package com.gestionabs;

import com.gestionabs.Utils.DateUtils;
import com.gestionabs.beans.*;
import com.gestionabs.repositories.*;
import com.gestionabs.services.NotificationService;
import com.gestionabs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupeRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    SubjectResultRepository subjectResultRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    NotificationService notificationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Administrator admin = new Administrator("admin","1","Bouhssina","Mohamed Youssef");

        Professor professor = new Professor("professeur","1","Moufakkir","Younes");
        Professor professorMath = new Professor("professeur2","1","Matieu","adjzoaid");
        Professor professorPhys = new Professor("professeur3","1","Albert","physuqd");
        Professor professorChim = new Professor("professeur4","1","Alchaidj","physuqd");
        Professor professorInfo = new Professor("professeur5","1","Cpila","Javane");


        Group group1 = new Group("group1",professorMath);
        Group group2 = new Group("group2",professorMath);

        Student student1 = new Student("etudiant","1","Benkhali","Ahmad", group1);
        Student student2 = new Student("student2","1","Ranadzadom","Adzad", group1);
        Student student3 = new Student("student3","1","Okdzadazd","dazdad", group1);
        Student student4 = new Student("student4","1","zadadza","adzadaz", group1);
        Student student5 = new Student("student5","1","dzedzedsd","dazdazd", group1);
        Student student6 = new Student("student6","1","dzadaz","edzedez", group1);
        Student student7 = new Student("student7","1","dsxwc","dzadza", group1);
        Student student8 = new Student("student8","1","dzada","dzadaz", group1);
        Student student9 = new Student("student9","1","dzadazdaz","xqsxqs", group1);
        Student student10 = new Student("student10","1","dzadaz","xsqxqsxxwc", group1);
        Student student111 = new Student("student11","1","zadaz","dfsgdfgfdg", group1);
        Student student12 = new Student("student12","1","qdsqxqxq","jkhjkhk", group1);
        Student student13 = new Student("student13","1","xsqxqsx","uiyiuy", group1);
        Student student14 = new Student("student14","1","xsqxqsxsq","zeraed", group1);


        Student student11 = new Student("etudiant2","1","dazddazd","Ahmad", group2);
        Student student21 = new Student("student22","1","dzadazd","Adzad", group2);
        Student student31 = new Student("student32","1","Okazdzadad","dazdad", group2);
        Student student41 = new Student("student42","1","zaddzadadazdadza","adzadaz", group2);
        Student student51 = new Student("student52","1","Bendazdzakhdazdazali","dazdazd", group2);


        Date now = new Date();
        Date tomorrow = new Date();
        tomorrow.setTime(now.getTime()+1 * 24 * 60 * 60 * 1000);


        group1.getStudents().add(student1);
        group1.getStudents().add(student2);
        group1.getStudents().add(student3);
        group1.getStudents().add(student4);
        group1.getStudents().add(student5);
        group1.getStudents().add(student6);
        group1.getStudents().add(student7);
        group1.getStudents().add(student8);
        group1.getStudents().add(student9);
        group1.getStudents().add(student10);
        group1.getStudents().add(student111);
        group1.getStudents().add(student12);
        group1.getStudents().add(student13);
        group1.getStudents().add(student14);

        group2.getStudents().add(student11);
        group2.getStudents().add(student21);
        group2.getStudents().add(student31);
        group2.getStudents().add(student41);
        group2.getStudents().add(student51);

        userRepository.save(professor);
        userRepository.save(professorChim);
        userRepository.save(professorInfo);
        userRepository.save(professorMath);
        userRepository.save(professorPhys);

        groupeRepository.save(group1);
        groupeRepository.save(group2);


        userRepository.save(admin);
        userRepository.save(student1);
        userRepository.save(student2);
        userRepository.save(student3);
        userRepository.save(student4);
        userRepository.save(student5);
        userRepository.save(student6);
        userRepository.save(student7);
        userRepository.save(student8);
        userRepository.save(student9);
        userRepository.save(student10);
        userRepository.save(student111);
        userRepository.save(student12);
        userRepository.save(student13);
        userRepository.save(student14);

        userRepository.save(student11);
        userRepository.save(student21);
        userRepository.save(student31);
        userRepository.save(student41);
        userRepository.save(student51);

        Subject math = new Subject("Mathématiques");
        Subject physique = new Subject("Physique");
        Subject info = new Subject("Informatique");
        Subject chimie = new Subject("Chimie");

        math.addTeacher(professorMath);
        physique.addTeacher(professorPhys);
        info.addTeacher(professorInfo);
        chimie.addTeacher(professorChim);

        professorMath.addTaughtSubject(math);
        professorPhys.addTaughtSubject(physique);
        professorInfo.addTaughtSubject(info);
        professorChim.addTaughtSubject(chimie);


        subjectRepository.save(math);
        subjectRepository.save(physique);
        subjectRepository.save(info);
        subjectRepository.save(chimie);

        userRepository.save(professorMath);
        userRepository.save(professorPhys);
        userRepository.save(professorInfo);
        userRepository.save(professorChim);


        Classroom classroom1 = new Classroom("S1","A");
        Classroom classroom2 = new Classroom("S2","A");
        Classroom classroom3 = new Classroom("S3","A");
        Classroom classroom4 = new Classroom("S4","A");


        Session session11= new Session(DateUtils.getDate(11,6,2018,8,0),false, group1,physique,classroom1,professorPhys,"tp");
        Session session12= new Session(DateUtils.getDate(11,6,2018,10,0),false, group1,math,classroom2,professorMath,"c");
        Session session13= new Session(DateUtils.getDate(11,6,2018,14,0),false, group1,info,classroom3,professorInfo,"td");
        Session session14= new Session(DateUtils.getDate(11,6,2018,16,0),false, group1,chimie,classroom1,professorChim, "tp");

        Session session21= new Session(DateUtils.getDate(12,6,2018,8,0),false, group1,info,classroom3,professorInfo,"tp");
        Session session22= new Session(DateUtils.getDate(12,6,2018,10,0),false, group1,math,classroom1,professorMath,"e");
        Session session23= new Session(DateUtils.getDate(12,6,2018,14,0),false, group1,chimie,classroom1,professorMath,"tp");
        Session session24= new Session(DateUtils.getDate(12,6,2018,16,0),false, group1,math,classroom1,professorMath,"td");

        Session session31= new Session(DateUtils.getDate(13,6,2018,8,0),false, group1,physique,classroom1,professorPhys,"c");
        Session session32= new Session(DateUtils.getDate(13,6,2018,10,0),false, group1,math,classroom2,professorMath,"e");

        Session session41= new Session(DateUtils.getDate(14,6,2018,8,0),false, group1,math,classroom1,professorMath,"c");
        Session session42= new Session(DateUtils.getDate(14,6,2018,10,0),false, group1,physique,classroom2,professorPhys,"e");
        Session session43= new Session(DateUtils.getDate(14,6,2018,16,0),false, group1,info,classroom1,professorInfo,"td");

        Session session61= new Session(DateUtils.getDate(15,6,2018,8,0),false, group1,chimie,classroom1,professorChim,"c");
        Session session62= new Session(DateUtils.getDate(15,6,2018,10,0),false, group1,info,classroom2,professorInfo,"e");




        Session session71= new Session(DateUtils.getDate(1,6,2018,10,0),false, group1,info,classroom2,professorInfo,"e");
        Session session72= new Session(DateUtils.getDate(2,6,2018,10,0),false, group1,math,classroom3,professorMath,"e");
        Session session73= new Session(DateUtils.getDate(3,6,2018,10,0),false, group1,chimie,classroom4,professorChim,"e");
        Session session74= new Session(DateUtils.getDate(4,6,2018,10,0),false, group1,physique,classroom1,professorPhys,"e");



        classroomRepository.save(classroom1);
        classroomRepository.save(classroom2);
        classroomRepository.save(classroom3);
        classroomRepository.save(classroom4);



        sessionRepository.save(session11);
        sessionRepository.save(session12);
        sessionRepository.save(session13);
        sessionRepository.save(session14);
        sessionRepository.save(session21);
        sessionRepository.save(session22);
        sessionRepository.save(session23);
        sessionRepository.save(session24);
        sessionRepository.save(session31);
        sessionRepository.save(session32);
        sessionRepository.save(session41);
        sessionRepository.save(session42);
        sessionRepository.save(session43);
        sessionRepository.save(session61);
        sessionRepository.save(session62);
        sessionRepository.save(session71);
        sessionRepository.save(session72);
        sessionRepository.save(session73);
        sessionRepository.save(session74);

        subjectResultRepository.save(new SubjectResult(student1,math, new Float(20),session71));
        subjectResultRepository.save(new SubjectResult(student1,math, new Float(20),session72));
        subjectResultRepository.save(new SubjectResult(student1,math, new Float(20),session73));
        subjectResultRepository.save(new SubjectResult(student1,math, new Float(20),session74));
        subjectResultRepository.save(new SubjectResult(student2,physique, new Float(16),session71));
        subjectResultRepository.save(new SubjectResult(student3,info, new Float(14),session71));
        subjectResultRepository.save(new SubjectResult(student4,chimie, new Float(17),session71));




        attendanceRepository.save(new Attendance(student1,session11,true,false));
        attendanceRepository.save(new Attendance(student2,session11,true,false));
        attendanceRepository.save(new Attendance(student3,session11,true,false));
        attendanceRepository.save(new Attendance(student4,session11,true,false));
        attendanceRepository.save(new Attendance(student5,session11,true,false));
        attendanceRepository.save(new Attendance(student6,session11,true,false));
        attendanceRepository.save(new Attendance(student7,session11,true,false));
        attendanceRepository.save(new Attendance(student8,session11,true,false));
        session11.setAttendanceChecked(true);
        sessionRepository.save(session11);

        // teste d'absence
        sessionRepository.save(new Session(DateUtils.getDate(16,0),false, group1,math,classroom1,professorMath,"td"));
        sessionRepository.save(new Session(DateUtils.getDate(16,0),false, group2,physique,classroom1,professorPhys,"tp"));


        notificationService.notifyGroup(group1,"Votre emploi a été changé");
        notificationService.notifyGroup(group1,"Les résultats d'un module ont été affiché");


    }



}
