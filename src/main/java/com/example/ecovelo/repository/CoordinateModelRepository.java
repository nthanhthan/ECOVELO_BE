package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.Coordinate;

public interface CoordinateModelRepository extends JpaRepository<Coordinate, Integer> {

}
