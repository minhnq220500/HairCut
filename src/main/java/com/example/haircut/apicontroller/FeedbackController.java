package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Feedback;
import com.example.haircut.repository.AppointmentRepository;
import com.example.haircut.repository.FeedbackRepository;
import com.example.haircut.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // create feedback
    // post
    // request body dùng khi tạo mới thông tin
    @PostMapping("/createFeedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        try {
            //Feedback feedbackLast=feedbackRepository.findAll(Sort.by(Sort.Direction.DESC, "feedbackID")).get(0);
            Optional<Appointment> appointment=appointmentRepository.findAppointmentByApptID(feedback.getApptID());
            if(appointment.isPresent()){
                Appointment appointment1=appointment.get();
                if(appointment1.isFeedbacked()==true){
                    return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
                }
                else{
                    Feedback feedbackLast=feedbackRepository.findTopByOrderByIdDesc();
                    String currentMaxId = feedbackLast.getFeedbackID();
                    String newID=new MyUtil().autoIncrementId(currentMaxId);
                    feedback.setFeedbackID(newID);

                    appointment1.setFeedbacked(true);
                    appointmentRepository.save(appointment1);

                    Feedback feedbackLuuDatabase = feedbackRepository.save(feedback);
                    return new ResponseEntity<>(feedbackLuuDatabase, HttpStatus.CREATED);
                }
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    //update
    //khi sử dụng request body thì phải cung cấp cho nó đủ thông tin của cái object java đó
    @PutMapping("/updateFeedback")
    public ResponseEntity<Feedback> updateFeedback(@RequestBody Feedback feedback){
        Optional<Feedback> feedbackCanUpdateData=feedbackRepository.findFeedbackByCusEmail(feedback.getCusEmail());
        // xem thử nó có trong database không
        if (feedbackCanUpdateData.isPresent()) {
            Feedback feedbackSeLuuVaoDatabase=feedbackCanUpdateData.get();// lấy data của cái trên
            feedbackSeLuuVaoDatabase.setComment(feedback.getComment());
            feedbackSeLuuVaoDatabase.setRating(feedback.getRating());

            return new ResponseEntity<>(feedbackRepository.save(feedbackSeLuuVaoDatabase), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // lấy ra 1 feedback bằng apptID
    @GetMapping("/feedbackApptID")
    public ResponseEntity<Feedback> getFeedbacktByAppointmentId(@RequestParam String apptID) {
        Feedback feedback = feedbackRepository.findFeedbackByApptID(apptID);
        if (feedback!=null) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getListFeedbackByEmpEmail")
    public ResponseEntity<List<Feedback>> getListFeedbackByApptID(@RequestParam String empEmail) {
        try{
            List<Appointment> listAppointment=appointmentRepository.findAppointmentByEmpEmail(empEmail);

            if(listAppointment.size()==0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            else{
                List<Feedback> listFeedback=new ArrayList<>();
                for(Appointment appointment:listAppointment){
                    Feedback feedback = feedbackRepository.findFeedbackByApptID(appointment.getApptID());
                    if(feedback!=null){
                        listFeedback.add(feedback);
                    }
                }
                return new ResponseEntity<>(listFeedback, HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
