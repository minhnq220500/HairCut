package com.example.haircut.apicontroller;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Service;
import com.example.haircut.model.ServiceCount;
import com.example.haircut.repository.AppointmentRepository;
import com.example.haircut.repository.ServiceRepository;
import com.example.haircut.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServiceController {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

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

    //đề xuất service
    @GetMapping("/getSuggestedServices")
    public ResponseEntity<List<ServiceCount>> getSuggestedServices() {
        try {
            List<Appointment> listAppointmentsAccepted=appointmentRepository.findAppointmentByStatus("DONE");

            List<Service> listServiceBookedInDB=new ArrayList<>();
            for(Appointment appointment:listAppointmentsAccepted){
                List<Service> listService=appointment.getListService();
                System.out.println(listService.size());
                listServiceBookedInDB.addAll(listService);
            }

            for(Service service:listServiceBookedInDB){
                System.out.println(service.toString());
            }

            Set<Service> setWithoutDuplicateElements = listServiceBookedInDB.stream()
                    .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Service::getServiceID))));

            System.out.println("---------------------------");
//            for(Service service:setWithoutDuplicateElements){
//                System.out.println(service.toString());
//            }
            List<Service> listWithoutDuplicateElements = new ArrayList<>();
            listWithoutDuplicateElements.addAll(setWithoutDuplicateElements);

            List<ServiceCount> listCount=new ArrayList<>();
            for(Service service:listWithoutDuplicateElements){
                ServiceCount serviceCount=new ServiceCount(service.getServiceID(),service.getServiceName(),service.getPrice(),0);
                listCount.add(serviceCount);
            }

//            System.out.println("---------------------------");
//            for(ServiceCount service:listCount){
//                System.out.println(service.toString());
//            }

            for (ServiceCount serviceCount:listCount) {
                for(Service service:listServiceBookedInDB){
                    if(serviceCount.getServiceID().equals(service.getServiceID())){
                        int count=serviceCount.getCount()+1;
                        serviceCount.setCount(count);
                    }
                }
            }
            System.out.println("---------------------------");
            for(ServiceCount service:listCount){
                System.out.println(service.toString());
            }

            Collections.sort(listCount,(ServiceCount s1, ServiceCount s2 ) -> s2.getCount() - s1.getCount() );
//            List<ServiceCount> listTop3=new ArrayList<>();
//            if(listCount.size()<3)
//                listTop3=listCount;
//            else
//                listTop3=listCount.subList(0,3);
//            //không lấy vị trí thứ 3
//
//            return new ResponseEntity<>(listTop3,HttpStatus.OK);
            return new ResponseEntity<>(listCount.size()>=3?listCount.subList(0,3):listCount,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping("/disableServices")
    public ResponseEntity<List<Service>> getAllServiceDislable() {
        try {
            List<Service> services = new ArrayList<>();
            serviceRepository.findByStatus(false).forEach(services::add);


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

//                Service service1=serviceRepository.findServiceByServiceName(service.getServiceName());
//                if(service1!=null){
//                    return new ResponseEntity<>(serviceData, HttpStatus.ALREADY_REPORTED);
//                }
//                else{
//                    serviceData.setServiceName(service.getServiceName());
//                    serviceData.setPrice(service.getPrice());
//                    serviceData.setStatus(service.isStatus());
//                    serviceData.setDurationTime(service.getDurationTime());
//
//                    serviceRepository.save(serviceData);
//
//                    return new ResponseEntity<>(serviceData, HttpStatus.OK);
//                }

                List<Service> listService=serviceRepository.findAll();
                System.out.println(listService.size());
                for(int i=0;i<listService.size();i++){
                    if(listService.get(i).getServiceName().toUpperCase().trim().equals(serviceData.getServiceName().toUpperCase().trim())){
                        listService.remove(i);
                    }
                }
                System.out.println(listService.size());
                //list service đã bỏ thằng đang cập nhật
                for(Service service1:listService){
                    if(service1.getServiceName().toUpperCase().trim().equals(service.getServiceName().toUpperCase().trim())){
                        return new ResponseEntity<>(service1, HttpStatus.ALREADY_REPORTED);
                    }
                }
                serviceData.setServiceName(service.getServiceName());
                serviceData.setPrice(service.getPrice());
                serviceData.setStatus(service.isStatus());
                serviceData.setDurationTime(service.getDurationTime());
                serviceRepository.save(serviceData);
                return new ResponseEntity<>(serviceData, HttpStatus.OK);

            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteService")
    public ResponseEntity<Service> deleteService(@RequestParam String serviceID){
        try {
            Service serviceData = serviceRepository.findByServiceID(serviceID);
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
    public ResponseEntity<Service> restoreService(@RequestParam String serviceID){
        try {
            Service serviceData = serviceRepository.findByServiceID(serviceID);
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
