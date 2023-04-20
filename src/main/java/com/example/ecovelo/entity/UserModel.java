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

@Entity
@Table(name = "user")
public class UserModel {
	public UserModel(int id_user, String name_user, String email, boolean sex, float money, boolean is_verify,
			AccountModel accountModel, PersonalLegalUserModel personalLegalUserModel,
			List<TransactionHistoryModel> transactionHistories, List<UserVoucherModel> userVouchers,
			List<RentBicycleModel> rentBicycleModels, List<ReportProblem> listReportProblems) {
		super();
		this.id_user = id_user;
		this.name_user = name_user;
		this.email = email;
		this.sex = sex;
		this.money = money;
		this.is_verify = is_verify;
		this.accountModel = accountModel;
		this.personalLegalUserModel = personalLegalUserModel;
		this.transactionHistories = transactionHistories;
		this.userVouchers = userVouchers;
		this.rentBicycleModels = rentBicycleModels;
		this.listReportProblems = listReportProblems;
	}
	public UserModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_user;
	
	@Column 
	private String name_user;
	
	@Column 
	private String email;
	
	@Column 
	private boolean sex;
	
	@Column 
	private float money;
	
	@Column 
	private boolean is_verify;
	
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
	
	public int getId_user() {
		return id_user;
	}
	
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
	public String getName_user() {
		return name_user;
	}
	
	public void setName_user(String name_user) {
		this.name_user = name_user;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public boolean isIs_verify() {
		return is_verify;
	}
	public void setIs_verify(boolean is_verify) {
		this.is_verify = is_verify;
	}

	public AccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}

	public PersonalLegalUserModel getPersonalLegalUserModel() {
		return personalLegalUserModel;
	}

	public void setPersonalLegalUserModel(PersonalLegalUserModel personalLegalUserModel) {
		this.personalLegalUserModel = personalLegalUserModel;
	}

	public List<TransactionHistoryModel> getTransactionHistories() {
		return transactionHistories;
	}

	public void setTransactionHistories(List<TransactionHistoryModel> transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public List<UserVoucherModel> getUserVouchers() {
		return userVouchers;
	}

	public void setUserVouchers(List<UserVoucherModel> userVouchers) {
		this.userVouchers = userVouchers;
	}

	public List<RentBicycleModel> getRentBicycleModels() {
		return rentBicycleModels;
	}

	public void setRentBicycleModels(List<RentBicycleModel> rentBicycleModels) {
		this.rentBicycleModels = rentBicycleModels;
	}
	public List<ReportProblem> getListReportProblems() {
		return listReportProblems;
	}
	public void setListReportProblems(List<ReportProblem> listReportProblems) {
		this.listReportProblems = listReportProblems;
	}
	
}
