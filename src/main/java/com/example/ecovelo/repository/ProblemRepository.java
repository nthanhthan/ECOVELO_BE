package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.Problem;

public interface ProblemRepository  extends JpaRepository<Problem, Integer>{

}
