package com.example.ecovelo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class AccountModel {
	public AccountModel(String phone_number, String password, boolean enable_account, boolean is_admin,
			UserModel userModel) {
		super();
		this.phone_number = phone_number;
		this.password = password;
		this.enable_account = enable_account;
		this.is_admin = is_admin;
		this.userModel = userModel;
	}
	public AccountModel() {
		
	}
	@Id
	private String phone_number;
	
	@Column(nullable = false)
	private String password;
	
	@Column 
	private boolean enable_account;
	
	@Column 
	private boolean is_admin;
	
	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModel;
	
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnable_account() {
		return enable_account;
	}
	public void setEnable_account(boolean enable_account) {
		this.enable_account = enable_account;
	}
	public boolean isIs_admin() {
		return is_admin;
	}
	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
}
