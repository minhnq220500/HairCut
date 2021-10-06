package com.example.haircut.apicontroller;

import com.example.haircut.model.Schedule;
import com.example.haircut.repository.ScheduleRepository;
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
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getAllSchedule(){
        try{
            List<Schedule> schedules = new ArrayList<>();
            scheduleRepository.findAll().forEach(schedules::add);

            if(!schedules.isEmpty()){
                return new ResponseEntity<>(schedules, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createSchedule")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule){
        try{

            Schedule lastSchedule = scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "scheduleID")).get(0);
            String id = new MyUtil().autoIncrementId(lastSchedule.getScheduleID());

            schedule.setScheduleID(id);
            scheduleRepository.save(schedule);

            return new ResponseEntity<>(schedule, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/findSchedule")
    public ResponseEntity<Schedule> findSchedule(@RequestBody Schedule schedule){
        try{

            Optional<Schedule> scheduleData = scheduleRepository.findByScheduleID(schedule.getScheduleID());

            if(scheduleData.isPresent()){
                Schedule _schedule = scheduleData.get();
                return new ResponseEntity<>(_schedule, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateSchedule")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule){
        try{

            Optional<Schedule> scheduleData = scheduleRepository.findByScheduleID(schedule.getScheduleID());

            if(scheduleData.isPresent()){

                Schedule _schedule = scheduleData.get();
                _schedule.setStartTime(schedule.getStartTime());
                _schedule.setEndTime(schedule.getEndTime());
                scheduleRepository.save(_schedule);
                return new ResponseEntity<>(_schedule, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
