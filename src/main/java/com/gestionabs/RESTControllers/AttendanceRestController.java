    package com.gestionabs.RESTControllers;

    import com.gestionabs.beans.Attendance;
    import com.gestionabs.beans.Session;
    import com.gestionabs.repositories.AttendanceRepository;
    import com.gestionabs.repositories.SessionRepository;
    import com.gestionabs.repositories.StudentRepository;
    import com.gestionabs.services.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.*;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.util.ArrayList;
    import java.util.List;


    @RestController
    @Transactional
    public class AttendanceRestController {

        @Autowired
        AttendanceRepository attendanceRepository;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        SessionRepository sessionRepository;
        @Autowired
        UserService userService ;

        @PutMapping("/REST/session/{sessionId}/attendance")
        public ResponseEntity doSaveAttendance(HttpServletRequest request, @PathVariable("sessionId") Integer sessionId, @RequestParam(value="isAbsent", required=false)  Integer absentStudentsId[]){
            if(!userService.isProfessor(request))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            if(sessionId!=null) {
                attendanceRepository.deleteAllByKeySessionId(sessionId);
                Session session = sessionRepository.findById(sessionId).get();
                session.setAttendanceChecked(true);
                sessionRepository.save(session);
                if (absentStudentsId != null && absentStudentsId.length > 0) {
                    for (Integer absentStudentId : absentStudentsId) {
                        Attendance a = new Attendance();
                        a.setIsAbsent(true);
                        a.setIsJustified(false);
                        a.setStudent(studentRepository.findById(absentStudentId).get());
                        a.setSession(sessionRepository.findById(sessionId).get());
                        attendanceRepository.save(a);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }

        @GetMapping("/REST/session/{sessionId}/attendance")
        public List<Integer> getAttendanceBySession(HttpServletRequest request, @PathVariable("sessionId") Integer sessionId, HttpServletResponse response) {
            if (!userService.isProfessor(request)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            else{
                List<Attendance> sessionAttedance = attendanceRepository.findAllByKeySessionId(sessionId);
                List<Integer> AbsentStudentIds = new ArrayList<>();
                for(Attendance a : sessionAttedance){
                    if(a.getIsAbsent()){
                        AbsentStudentIds.add(a.getStudent().getId());
                    }
                }
                return AbsentStudentIds;
            }
        }
    }
