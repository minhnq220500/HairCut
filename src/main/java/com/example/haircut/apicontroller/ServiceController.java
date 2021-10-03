package com.example.haircut.apicontroller;

import com.example.haircut.model.Service;
import com.example.haircut.repository.ServiceRepository;
import com.example.haircut.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/service")
@RestController
public class ServiceController {
    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("")
    public ResponseEntity<List<Service>> getAllService() {
        try {
            List<Service> services = new ArrayList<>();
            serviceRepository.findAll().forEach(services::add);


            if (services != null) {
                return new ResponseEntity<List<Service>>(services, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create")
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        try {
            Service currentMaxService = serviceRepository.findAll(Sort.by(Sort.Direction.DESC, "serviceID")).get(0);
            String currentMaxId = currentMaxService.getServiceID();
            String newId = new MyUtil().getAutoIncreasementId(currentMaxId);
            service.setServiceID(newId);
            service.setStatus(true);
            serviceRepository.save(service);

            return new ResponseEntity<>(service, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("update")
    public ResponseEntity<Service> updateService(@RequestBody Service service) {
        try {

//            Optional<Service>
            return new ResponseEntity<>(service, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("test")
    public ResponseEntity<String> getTest(@RequestBody String search) {
        System.out.println(search);
        return new ResponseEntity<>(search, HttpStatus.OK);
    }
}
