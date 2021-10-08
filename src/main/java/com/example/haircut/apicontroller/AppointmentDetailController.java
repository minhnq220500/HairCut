package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.AppointmentDetail;
import com.example.haircut.model.Service;
import com.example.haircut.repository.AppointmentDetailRepository;
import com.example.haircut.repository.AppointmentRepository;
import com.example.haircut.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppointmentDetailController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentDetailRepository appointmentDetailRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @PostMapping("/appointmentDetails")
    public ResponseEntity<List<AppointmentDetail>> getDetailByAppointmentId(@RequestBody AppointmentDetail apptDetail){

        try{
            List<AppointmentDetail> appointmentDetails = new ArrayList<>();
            appointmentDetailRepository.findByApptID(apptDetail.getApptID()).forEach(appointmentDetails::add);

            if(!appointmentDetails.isEmpty()){
                return new ResponseEntity<>(appointmentDetails, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateAppointmentDetail")
    public ResponseEntity<AppointmentDetail> updateDetail(@RequestBody AppointmentDetail apptDetail){
        try{
            Optional<AppointmentDetail> detail = appointmentDetailRepository.findByDetailID(apptDetail.getDetailID());

            if(detail.isPresent()){
                Double price = serviceRepository.findByServiceID(apptDetail.getServiceID()).getPrice();
                AppointmentDetail _detail = detail.get();
                _detail.setEmpEmail(apptDetail.getEmpEmail());
                _detail.setServiceID(apptDetail.getServiceID());
                _detail.setPrice(price);
                appointmentDetailRepository.save(_detail);
                return new ResponseEntity<>(_detail, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
