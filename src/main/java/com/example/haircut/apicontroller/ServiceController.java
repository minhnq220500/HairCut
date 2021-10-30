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
import java.util.Optional;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServiceController {
    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/services")
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

    @GetMapping("/availableServices")
    public ResponseEntity<List<Service>> getAllServiceAvailable() {
        try {
            List<Service> services = new ArrayList<>();
            serviceRepository.findByStatus(true).forEach(services::add);


            if (services != null) {
                return new ResponseEntity<List<Service>>(services, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/findService")
    public ResponseEntity<Service> findServiceByServiceId(@RequestBody Service service){
        try {
            Service serviceData = serviceRepository.findByServiceID(service.getServiceID());
            if(serviceData != null){
                return new ResponseEntity<>(serviceData, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/createService")
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        try {
            //Service currentMaxService = serviceRepository.findAll(Sort.by(Sort.Direction.DESC, "serviceID")).get(0);
            Service service1=serviceRepository.findServiceByServiceName(service.getServiceName());
            if(service1==null){
                Service currentMaxService = serviceRepository.findTopByOrderByIdDesc();
                String currentMaxId = currentMaxService.getServiceID();
                String newId = new MyUtil().autoIncrementId(currentMaxId);
                service.setServiceID(newId);
                service.setStatus(true);
                serviceRepository.save(service);

                return new ResponseEntity<>(service, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateService")
    public ResponseEntity<Service> updateService(@RequestBody Service service) {
        try {

            Service serviceData = serviceRepository.findByServiceID(service.getServiceID());
            if(serviceData != null){
                Service service1=serviceRepository.findServiceByServiceName(service.getServiceName());
                if(service1!=null){
                    return new ResponseEntity<>(serviceData, HttpStatus.ALREADY_REPORTED);
                }
                else{
                    serviceData.setServiceName(service.getServiceName());
                    serviceData.setPrice(service.getPrice());
                    serviceData.setStatus(service.isStatus());
                    serviceData.setDurationTime(service.getDurationTime());

                    serviceRepository.save(serviceData);

                    return new ResponseEntity<>(serviceData, HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteService")
    public ResponseEntity<Service> deleteService(@RequestBody Service service){
        try {
            Service serviceData = serviceRepository.findByServiceID(service.getServiceID());
            if(serviceData != null){
                serviceData.setStatus(false);
                serviceRepository.save(serviceData);
                return new ResponseEntity<>(serviceData, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/restoreService")
    public ResponseEntity<Service> restoreService(@RequestBody Service service){
        try {
            Service serviceData = serviceRepository.findByServiceID(service.getServiceID());
            if(serviceData != null){
                serviceData.setStatus(true);
                serviceRepository.save(serviceData);
                return new ResponseEntity<>(serviceData, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @GetMapping("/serviceByCate/{id}")
//    public ResponseEntity<List<Service>> getListServiceByCategoryId(@PathVariable String id){
//        try{
//            List<Service> services = new ArrayList<>();
//            serviceRepository.findByCateID(id).forEach(services::add);
//
//            if(!services.isEmpty()){
//                return new ResponseEntity<>(services, HttpStatus.OK);
//            }
//
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
