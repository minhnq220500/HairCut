package com.example.haircut.repository;

import com.example.haircut.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<Feedback,String> {
    Optional<Feedback> findFeedbackByCusEmail(String cusEmail);

    Feedback findTopByOrderByIdDesc();
}
