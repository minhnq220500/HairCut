package com.example.haircut.apicontroller;

import com.example.haircut.model.*;
import com.example.haircut.repository.AppointmentRepository;
import com.example.haircut.repository.DiscountRepository;
import com.example.haircut.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DiscountController {

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> getAllDiscountt() {
        try {
            List<Discount> listDiscount = new ArrayList<>();
            discountRepository.findAll().forEach(listDiscount::add);
            if (listDiscount.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listDiscount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAvailableDiscounts")
    public ResponseEntity<List<Discount>> getAvailableDiscounts() {
        try {
            List<Discount> listDiscount = discountRepository.findDiscountsByStatus(true);
            if (listDiscount.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listDiscount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDisableDiscounts")
    public ResponseEntity<List<Discount>> getDisableDiscounts() {
        try {
            List<Discount> listDiscount = discountRepository.findDiscountsByStatus(false);
            if (listDiscount.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listDiscount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/checkDiscountCode")
        public ResponseEntity<Discount> checkDiscountCode(@RequestParam String discountCode, String createDate) {
        try {
            Discount discount=discountRepository.findDiscountByDiscountCode(discountCode);
            if (discount!=null) {
                if(discount.isStatus()){
                    Date startDate=discount.getStartDate();
                    Date endDate=discount.getEndDate();
                    Date createDateFormat=new SimpleDateFormat("dd-MM-yyyy").parse(createDate);
//                Date createDateFormat=new SimpleDateFormat("yyyy--mm--dd").parse(createDate);
                    if(!startDate.after(createDateFormat) && !endDate.before(createDateFormat)){
//                        if(!historyDate.after(todayDate) && !futureDate.before(todayDate)) {
//                            /* historyDate <= todayDate <= futureDate */
//                        }
                        return new ResponseEntity<>(discount,HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }
                }
                else{
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //tạo discount check trùng name, trùng code
    @PostMapping("/createDiscount")
    public ResponseEntity<Discount> createFeedback(@RequestBody Discount discount){
        try {
            Discount discount1=discountRepository.findDiscountByDiscountCode(discount.getDiscountCode());
            if(discount1!=null){
                return new ResponseEntity<>(discount1, HttpStatus.ALREADY_REPORTED);
            }else{
                Discount discount2=discountRepository.findDiscountByDiscountName(discount.getDiscountName());
                if(discount2!=null){
                    return new ResponseEntity<>(discount2, HttpStatus.ALREADY_REPORTED);
                }
                else{
                    return new ResponseEntity<>(discountRepository.save(discount), HttpStatus.CREATED);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    @PostMapping("/removeDiscountByCode")
    public ResponseEntity<Discount> removeDiscountByCode(@RequestParam String discountCode){
        try {
            Discount discount1=discountRepository.findDiscountByDiscountCode(discountCode);
            if(discount1==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                discount1.setStatus(false);
                discountRepository.save(discount1);
                return new ResponseEntity<>(discount1, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    @PostMapping("/restoreDiscountByCode")
    public ResponseEntity<Discount> restoreDiscountByCode(@RequestParam String discountCode){
        try {
            Discount discount1=discountRepository.findDiscountByDiscountCode(discountCode);
            if(discount1==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                discount1.setStatus(true);
                discountRepository.save(discount1);
                return new ResponseEntity<>(discount1, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    @PutMapping("/updateDiscount")
    public ResponseEntity<Discount> updateFeedback(@RequestBody Discount discount){
        Discount discount1=discountRepository.findDiscountByDiscountCode(discount.getDiscountCode());
        // xem thử nó có trong database không
        if (discount1!=null) {
            List<Discount> listDiscount=discountRepository.findAll();
            System.out.println(listDiscount.size());
            for(int i=0;i<listDiscount.size();i++){
                if(listDiscount.get(i).getDiscountName().toUpperCase().trim().equals(discount1.getDiscountName().toUpperCase().trim())){
                    listDiscount.remove(i);
                }
            }
            System.out.println(listDiscount.size());
            //list service đã bỏ thằng đang cập nhật
            for(Discount discount2:listDiscount){
                if(discount2.getDiscountName().toUpperCase().trim().equals(discount.getDiscountName().toUpperCase().trim())){
                    return new ResponseEntity<>(discount2, HttpStatus.ALREADY_REPORTED);
                }
            }

            discount1.setDiscountName(discount.getDiscountName());
            discount1.setValue(discount.getValue());
            discount1.setStartDate(discount.getStartDate());
            discount1.setEndDate(discount.getEndDate());
            discount1.setStatus(discount.isStatus());

            return new ResponseEntity<>(discountRepository.save(discount1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/discountCodeRemovable")
    public ResponseEntity<Discount> checkDiscountCode(@RequestBody Discount discount) {
        try {
            Date checkDate = discount.getStartDate();
            List<Appointment> appointments = new ArrayList<>();
            appointments = appointmentRepository.findByDiscountCode(discount.getDiscountCode());;
            if(appointments.size() > 0){
                for(Appointment a : appointments){
                    if(a.getStartTime().compareTo(discount.getStartDate()) > 0){
                        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
                    }
                }
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
