package com.example.ecovelo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_history")
public class TransactionHistoryModel {
	public TransactionHistoryModel(int id_transaction, float money, Date datetime_transaction, String title_transaction,
			boolean status_transaction, boolean is_main_account, UserModel userModelTransaction) {
		super();
		this.id_transaction = id_transaction;
		this.money = money;
		this.datetime_transaction = datetime_transaction;
		this.title_transaction = title_transaction;
		this.status_transaction = status_transaction;
		this.is_main_account = is_main_account;
		this.userModelTransaction = userModelTransaction;
	}
	public TransactionHistoryModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_transaction;
	
	@Column(nullable = false)
	private float money;
	
	@Column 
	private Date datetime_transaction;

	@Column 
	private String title_transaction;
	
	@Column 
	private boolean status_transaction;
	
	@Column 
	private boolean is_main_account;
	
	@OneToOne
	@JoinColumn(name= "user_id")
	private UserModel userModelTransaction;
	
	public int getId_transaction() {
		return id_transaction;
	}
	public void setId_transaction(int id_transaction) {
		this.id_transaction = id_transaction;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public Date getDatetime_transaction() {
		return datetime_transaction;
	}
	public void setDatetime_transaction(Date datetime_transaction) {
		this.datetime_transaction = datetime_transaction;
	}
	public String getTitle_transaction() {
		return title_transaction;
	}
	public void setTitle_transaction(String title_transaction) {
		this.title_transaction = title_transaction;
	}
	public boolean isStatus_transaction() {
		return status_transaction;
	}
	public void setStatus_transaction(boolean status_transaction) {
		this.status_transaction = status_transaction;
	}
	public boolean isIs_main_account() {
		return is_main_account;
	}
	public void setIs_main_account(boolean is_main_account) {
		this.is_main_account = is_main_account;
	}
	public UserModel getUserModelTransaction() {
		return userModelTransaction;
	}
	public void setUserModelTransaction(UserModel userModelTransaction) {
		this.userModelTransaction = userModelTransaction;
	}
	
}
