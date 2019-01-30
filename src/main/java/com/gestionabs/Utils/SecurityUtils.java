package com.gestionabs.Utils;

import com.gestionabs.beans.Administrator;
import com.gestionabs.beans.Professor;
import com.gestionabs.beans.Student;
import com.gestionabs.beans.User;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {
    public static boolean isAdmin(HttpServletRequest request){
        return request.getSession().getAttribute("user") instanceof Administrator;
    }
    public static boolean isAdmin(User user){
        return user instanceof Administrator;
    }


    public static boolean isProfessor(HttpServletRequest request){
        return request.getSession().getAttribute("user") instanceof Professor;
    }

    public static boolean isProfessor(User user){
        return user instanceof Professor;
    }

    public static boolean isStudent(HttpServletRequest request){
        return request.getSession().getAttribute("user") instanceof Student;
    }
    public static boolean isStudent(User user){
        return user instanceof Student;
    }
}
