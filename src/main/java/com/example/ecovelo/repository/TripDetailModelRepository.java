package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.TripDetail;

public interface TripDetailModelRepository  extends JpaRepository<TripDetail, Integer>{

}
