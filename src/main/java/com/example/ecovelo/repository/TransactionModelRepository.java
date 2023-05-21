package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecovelo.entity.TransactionHistoryModel;

public interface TransactionModelRepository extends JpaRepository<TransactionHistoryModel, Integer> {

}
