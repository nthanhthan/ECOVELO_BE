package com.example.ecovelo.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ecovelo.entity.BicycleModel;



public interface BicycleModelRepository  extends JpaRepository<BicycleModel, String> {
 

}
