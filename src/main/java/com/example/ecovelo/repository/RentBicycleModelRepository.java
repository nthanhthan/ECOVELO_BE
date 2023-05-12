package com.example.ecovelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ecovelo.entity.RentBicycleModel;

public interface RentBicycleModelRepository extends JpaRepository<RentBicycleModel, Integer>{

}
