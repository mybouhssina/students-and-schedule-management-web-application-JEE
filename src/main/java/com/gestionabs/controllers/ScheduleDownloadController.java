package com.gestionabs.controllers;

import com.gestionabs.Utils.DateUtils;
import com.gestionabs.Utils.SecurityUtils;
import com.gestionabs.beans.Group;
import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Student;
import com.gestionabs.repositories.GroupRepository;
import com.gestionabs.repositories.SessionRepository;
import com.gestionabs.viewResolvers.ScheduleService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ScheduleDownloadController {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    GroupRepository groupRepository;
    @RequestMapping(value = "/download/teacherSchedule", method = RequestMethod.GET)
    public void downloadProfessorSchedule(HttpServletRequest request, HttpServletResponse response) {
        if(!SecurityUtils.isProfessor(request)){
            return;
        }
        Professor professor = (Professor) request.getSession().getAttribute("user");
        HSSFWorkbook workbook = null;
        try{
            workbook = ScheduleService.buildExcelDocument(DateUtils.getWeekProgramAllDaysFilled(sessionRepository.findByTeacherAndStartingDateBetween(professor,DateUtils.getThisWeekMondayDateOrNextWeekIfItsWeekEnd(),DateUtils.getThisWeekFridayDateOrNextWeekIfItsWeekEnd())));
            response.setHeader("Content-disposition", "attachment; filename=schedule.xls");
            workbook.write(response.getOutputStream());
            response.flushBuffer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/download/studentSchedule", method = RequestMethod.GET)
    public void downloadStudentSchedule(HttpServletRequest request, HttpServletResponse response) {
        if(!SecurityUtils.isStudent(request)){
            return;
        }
        Student student = (Student) request.getSession().getAttribute("user");
        Group group = student.getCurrentGroup();
        HSSFWorkbook workbook ;
        try{
            workbook = ScheduleService.buildExcelDocument(DateUtils.getWeekProgramAllDaysFilled(sessionRepository.findByGroupAndStartingDateBetween(group,DateUtils.getThisWeekMondayDateOrNextWeekIfItsWeekEnd(),DateUtils.getThisWeekFridayDateOrNextWeekIfItsWeekEnd())));
            response.setHeader("Content-disposition", "attachment; filename=schedule.xls");
            workbook.write(response.getOutputStream());
            response.flushBuffer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
