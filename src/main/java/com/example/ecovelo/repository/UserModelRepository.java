package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.UserModel;

public interface UserModelRepository extends JpaRepository<UserModel, Integer>{

}
