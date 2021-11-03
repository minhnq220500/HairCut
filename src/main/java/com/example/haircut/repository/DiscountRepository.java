package com.example.haircut.repository;

import com.example.haircut.model.Customer;
import com.example.haircut.model.Discount;
import com.example.haircut.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DiscountRepository extends MongoRepository<Discount,String> {
    Discount findDiscountByDiscountCode(String discountCode);
    Discount findDiscountByDiscountName(String discountName);
    List<Discount> findDiscountsByStatus(boolean status);
}
