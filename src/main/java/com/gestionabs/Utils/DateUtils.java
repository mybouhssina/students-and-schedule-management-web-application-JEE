package com.gestionabs.Utils;

import com.gestionabs.beans.Session;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class DateUtils {
    public static Date getDate(int hour,int min){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,min);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    public static Date getDate(int day, int month, int year,int hour,int min){
        month= month-1;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,min);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    public static Date getThisWeekMondayDate(){
        LocalDate today = LocalDate.now();
        LocalDate monday ;
        while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
            today = today.minusDays(1);
        }
        monday=today;
        Date mondayDate = Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        mondayDate.setHours(8);
        mondayDate.setMinutes(0);
        return mondayDate;
    }

    public static Date getThisWeekFridayDate(){
        LocalDate today = LocalDate.now();
        LocalDate friday ;
        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY ) {
            while(today.getDayOfWeek() != DayOfWeek.FRIDAY)
                today = today.minusDays(1);
        }
        else{
            while(today.getDayOfWeek() != DayOfWeek.FRIDAY)
                today = today.plusDays(1);
        }
        friday=today;
        Date fridayDate = Date.from(friday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fridayDate.setHours(23);
        fridayDate.setMinutes(59);
        return fridayDate;
    }

    public static Date getNextWeekMondayDate(){
        LocalDate today = LocalDate.now();
        LocalDate monday ;
        while (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
            today = today.plusDays(1);
        }
        while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
            today = today.plusDays(1);
        }
        monday=today;
        return Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getNextWeekFridayDate(){
        LocalDate today = LocalDate.now();
        LocalDate friday ;
        while (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
            today = today.plusDays(1);
        }
        while (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
            today = today.plusDays(1);
        }
        friday=today;
        Date fridayDate = Date.from(friday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fridayDate.setHours(23);
        fridayDate.setMinutes(59);
        return fridayDate;
    }

    public static Date getThisWeekMondayDateOrNextWeekIfItsWeekEnd(){
        LocalDate today = LocalDate.now();
        LocalDate monday ;
        if(today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY){
            while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
                today = today.plusDays(1);
            }
        }
        else{
            while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
                today = today.minusDays(1);
            }
        }
        monday = today;
        Date mondayDate = Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        mondayDate.setHours(8);
        mondayDate.setMinutes(0);
        return mondayDate;
    }



    public static Date getThisWeekFridayDateOrNextWeekIfItsWeekEnd(){
        LocalDate today = LocalDate.now();
        LocalDate friday ;
        while (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
            today = today.plusDays(1);
        }
        friday = today;
        Date fridayDate = Date.from(friday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fridayDate.setHours(23);
        fridayDate.setMinutes(59);
        return fridayDate;
    }


    // 0 = monday
    public static Date getThisWeekDayDate(int day,int hour){
        LocalDate today = LocalDate.now();
        LocalDate desiredDayLocDate ;
        DayOfWeek desiredDay;
        if(day==0){
            desiredDay=DayOfWeek.MONDAY;
        }
        else if(day==1){
            desiredDay=DayOfWeek.TUESDAY;
        }
        else if(day==2){
            desiredDay=DayOfWeek.WEDNESDAY;
        }
        else if(day==3){
            desiredDay=DayOfWeek.THURSDAY;
        }
        else{
            desiredDay=DayOfWeek.FRIDAY;
        }

        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY ) {
            while(today.getDayOfWeek() != desiredDay)
                today = today.minusDays(1);
        }
        else{
            while(today.getDayOfWeek() != desiredDay)
                today = today.plusDays(1);
        }
        desiredDayLocDate=today;
        Date desiredDayDate = Date.from(desiredDayLocDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        desiredDayDate.setHours(hour);
        desiredDayDate.setMinutes(00);
        return desiredDayDate;
    }

    // 0 = monday
    public static Date getNextWeekDayDate(int day,int hour){
        LocalDate today = LocalDate.now();
        LocalDate desiredDayLocDate ;
        DayOfWeek desiredDay;
        if(day==0){
            desiredDay=DayOfWeek.MONDAY;
        }
        else if(day==1){
            desiredDay=DayOfWeek.TUESDAY;
        }
        else if(day==2){
            desiredDay=DayOfWeek.WEDNESDAY;
        }
        else if(day==3){
            desiredDay=DayOfWeek.THURSDAY;
        }
        else{
            desiredDay=DayOfWeek.FRIDAY;
        }
        while(today.getDayOfWeek() != DayOfWeek.MONDAY)
            today = today.plusDays(1);
        while(today.getDayOfWeek() != desiredDay)
            today = today.plusDays(1);
        desiredDayLocDate=today;
        Date desiredDayDate = Date.from(desiredDayLocDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        desiredDayDate.setHours(hour);
        desiredDayDate.setMinutes(00);
        return desiredDayDate;
    }

    public static int getTodayDayNumber(){
        LocalDate today = LocalDate.now();
        return today.getDayOfWeek().getValue();
    }

    public static int getCurrentHour(){
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static  ArrayList<ArrayList<Session>> getWeekProgram(List<Session> weekSessions){
        ArrayList<ArrayList<Session>> programOfTheWeek = new ArrayList<ArrayList<Session>>();
        programOfTheWeek.add(new ArrayList<>(4));
        programOfTheWeek.add(new ArrayList<>(4));
        programOfTheWeek.add(new ArrayList<>(4));
        programOfTheWeek.add(new ArrayList<>(4));
        programOfTheWeek.add(new ArrayList<>(4));


        Calendar c = Calendar.getInstance();
        Collections.sort(weekSessions);
        for(Session s : weekSessions){
            c.setTime(s.getStartingDate());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek==Calendar.MONDAY){
                programOfTheWeek.get(0).add(s);
            }
            else if(dayOfWeek==Calendar.TUESDAY){
                programOfTheWeek.get(1).add(s);
            }
            else if(dayOfWeek==Calendar.WEDNESDAY){
                programOfTheWeek.get(2).add(s);
            }
            else if(dayOfWeek==Calendar.THURSDAY){
                programOfTheWeek.get(3).add(s);
            }
            else if(dayOfWeek==Calendar.FRIDAY){
                programOfTheWeek.get(4).add(s);
            }
        }
        return programOfTheWeek;
    }

    public static  Session[][] getWeekProgramAllDaysFilled(List<Session> weekSessions){
        Session[][] programOfTheWeek = new Session[5][4];

        Calendar c = Calendar.getInstance();
        Collections.sort(weekSessions);
        for(Session s : weekSessions){
            c.setTime(s.getStartingDate());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            int hour = s.getStartingDate().getHours();
            if(dayOfWeek==Calendar.MONDAY){
                if(hour==8)
                    programOfTheWeek[0][0]= s;
                else if(hour==10)
                    programOfTheWeek[0][1]= s;
                else if(hour==14)
                    programOfTheWeek[0][2]= s;
                else if(hour==16)
                    programOfTheWeek[0][3]= s;
            }
            else if(dayOfWeek==Calendar.TUESDAY){
                if(hour==8)
                    programOfTheWeek[1][0]= s;
                else if(hour==10)
                    programOfTheWeek[1][1]= s;
                else if(hour==14)
                    programOfTheWeek[1][2]= s;
                else if(hour==16)
                    programOfTheWeek[1][3]= s;
            }
            else if(dayOfWeek==Calendar.WEDNESDAY){
                if(hour==8)
                    programOfTheWeek[2][0]= s;
                else if(hour==10)
                    programOfTheWeek[2][1]= s;
                else if(hour==14)
                    programOfTheWeek[2][2]= s;
                else if(hour==16)
                    programOfTheWeek[2][3]= s;
            }
            else if(dayOfWeek==Calendar.THURSDAY){
                if(hour==8)
                    programOfTheWeek[3][0]= s;
                else if(hour==10)
                    programOfTheWeek[3][1]= s;
                else if(hour==14)
                    programOfTheWeek[3][2]= s;
                else if(hour==16)
                    programOfTheWeek[3][3]= s;
            }
            else if(dayOfWeek==Calendar.FRIDAY){
                if(hour==8)
                    programOfTheWeek[4][0]= s;
                else if(hour==10)
                    programOfTheWeek[4][1]= s;
                else if(hour==14)
                    programOfTheWeek[4][2]= s;
                else if(hour==16)
                    programOfTheWeek[4][3]= s;
            }
        }
        return programOfTheWeek;
    }


    // 8 -> 0 , 10 -> 1 , 14 -> 2 , 16 -> 3
    private static int hourToArrayIndex(int h){
        if(h==8)
            return 0;
        else if(h==10)
            return 1;
        else if(h==14)
            return 2;
        else
            return 3;
    }


}
