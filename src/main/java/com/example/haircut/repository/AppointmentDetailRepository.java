package com.example.haircut.repository;

import com.example.haircut.model.AppointmentDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentDetailRepository extends MongoRepository<AppointmentDetail, String> {

    List<AppointmentDetail> findByApptID(String id);

    Optional<AppointmentDetail> findByDetailID(String id);

}
