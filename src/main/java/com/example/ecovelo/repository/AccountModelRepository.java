package com.example.ecovelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.AccountModel;

public interface AccountModelRepository extends JpaRepository<AccountModel, String>{
	Optional<AccountModel> findByPhoneNumber(String phoneNumber);
}
