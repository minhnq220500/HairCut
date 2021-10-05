package com.example.haircut.repository;

import com.example.haircut.model.Appointment;
import com.example.haircut.model.Customer;
import com.example.haircut.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LocationRepository extends MongoRepository<Location,String> {
    Optional<Location> findLocationByLocationID(String locationID);
}
