package com.example.ecovelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecovelo.entity.PersonalLegalUserModel;
import com.example.ecovelo.entity.UserModel;

public interface VerifyAccountRepository extends JpaRepository<PersonalLegalUserModel, Integer> {
	boolean existsByUserModelLegal(UserModel userModel);
	Optional<PersonalLegalUserModel> findByUserModelLegal(UserModel userModel);

}
