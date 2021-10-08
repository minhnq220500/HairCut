package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Feedback;
import com.example.haircut.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;

    // create feedback
    // post
    // request body dùng khi tạo mới thông tin
    @PostMapping("/createFeedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        try {
            Feedback feedbackLuuDatabase = feedbackRepository.save(feedback);
            return new ResponseEntity<>(feedbackLuuDatabase, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    //update
    //khi sử dụng request body thì phải cung cấp cho nó đủ thông tin của cái object java đó
    @PutMapping("/feedback")
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
}
