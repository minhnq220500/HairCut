package com.example.haircut.apicontroller;

import com.example.haircut.model.Location;
import com.example.haircut.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    // create
    // post
    // request body dùng khi tạo mới thông tin
    @PostMapping("/createAppointment")
    public ResponseEntity<Location> createAppointment(@RequestBody Location locationCanAdd){
        try {
            Location location = locationRepository.save(locationCanAdd);
            return new ResponseEntity<>(location, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocation(){
        try {
            List<Location> listLocation=new ArrayList<>();
            locationRepository.findAll().forEach(listLocation::add);
            if(listLocation.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listLocation,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    // tìm
    //lấy ra 1 location bằng locationID
    @GetMapping("/location")
    public ResponseEntity<Location> getLocationByLocationID(@RequestParam String locationID) {
        Optional<Location> locationCanTim=locationRepository.findLocationByLocationID(locationID);
        //optional để kiểm tra xem có dữ liệu trong
        if (locationCanTim.isPresent()) {
            return new ResponseEntity<>(locationCanTim.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //update
    //khi sử dụng request body thì phải cung cấp cho nó đủ thông tin của cái object java đó
    @PutMapping("/location")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location){
        Optional<Location> locationCanUpdateData=locationRepository.findLocationByLocationID(location.getLocationID());
        // xem thử nó có trong database không
        if (locationCanUpdateData.isPresent()) {
            Location locationSeLuuVaoDatabase=locationCanUpdateData.get();// lấy data của cái trên
            locationSeLuuVaoDatabase.setStatus(location.getStatus());
            locationSeLuuVaoDatabase.setAddress(location.getAddress());
            locationSeLuuVaoDatabase.setState(location.getState());
            locationSeLuuVaoDatabase.setCity(location.getCity());

            return new ResponseEntity<>(locationRepository.save(locationSeLuuVaoDatabase), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
