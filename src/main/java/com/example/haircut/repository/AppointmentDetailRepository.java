package com.example.haircut.repository;

import com.example.haircut.model.AppointmentDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentDetailRepository extends MongoRepository<AppointmentDetail,String> {
}
