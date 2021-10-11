package com.example.haircut.repository;

import com.example.haircut.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceRepository extends MongoRepository<Service, String> {
    Service findByServiceID(String id);

    List<Service> findByStatus(Boolean status);

    List<Service> findByCateID(String cateID);
}
