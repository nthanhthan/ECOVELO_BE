package com.example.ecovelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecovelo.entity.TransactionHistoryModel;
import com.example.ecovelo.entity.UserModel;

public interface TransactionModelRepository extends JpaRepository<TransactionHistoryModel, Integer> {
	List<TransactionHistoryModel> findByUserModelTransaction(UserModel userModel);

}
