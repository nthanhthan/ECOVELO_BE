package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nameUser;
	private String email; 
	private float money;
	private boolean verify;
	
	@OneToOne(mappedBy = "userModel")
	private AccountModel accountModel;
	
	@OneToOne(mappedBy = "userModelLegal")
	private PersonalLegalUserModel personalLegalUserModel;
	
	@OneToMany(mappedBy = "userModelTransaction")
	private List<TransactionHistoryModel> transactionHistories;
	
	@OneToMany(mappedBy = "userModelVoucher")
	private List<UserVoucherModel> userVouchers;
	
	@OneToMany(mappedBy = "userModelRent")
	private List<RentBicycleModel> rentBicycleModels;
	
	@OneToMany(mappedBy = "userModelReport")
	private List<ReportProblem> listReportProblems;
	
	
}
