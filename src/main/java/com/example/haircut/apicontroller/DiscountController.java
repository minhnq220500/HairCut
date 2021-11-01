package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Discount;
import com.example.haircut.model.Feedback;
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

    @PostMapping("/checkDiscountCode")
        public ResponseEntity<Discount> checkDiscountCode(@RequestParam String discountCode, Date createDate) {
        try {
            Discount discount=discountRepository.findDiscountByDiscountCode(discountCode);
            if (discount!=null) {
                if(discount.isStatus()){
                    Date startDate=discount.getStartDate();
                    Date endDate=discount.getEndDate();
//                Date createDateFormat=new SimpleDateFormat("yyyy--mm--dd").parse(createDate);
                    if(!startDate.after(createDate) && !endDate.before(createDate)){
                        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping("/updateDiscount")
    public ResponseEntity<Discount> updateFeedback(@RequestBody Discount discount){
        Discount discount1=discountRepository.findDiscountByDiscountCode(discount.getDiscountCode());
        // xem thử nó có trong database không
        if (discount1!=null) {
            discount1.setDiscountCode(discount.getDiscountCode());
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

}
