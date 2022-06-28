package com.example.haircut.repository;

import com.example.haircut.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Optional<Schedule> findByScheduleID(String id);

    Schedule findTopByOrderByIdDesc();

    List<Schedule> findByStatus(boolean status);
}
