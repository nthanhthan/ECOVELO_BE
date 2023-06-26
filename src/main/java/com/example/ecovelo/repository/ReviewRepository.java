package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecovelo.entity.ReviewModel;

public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {

}
