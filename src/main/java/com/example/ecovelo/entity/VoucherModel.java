package com.example.ecovelo.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "voucher")
public class VoucherModel {
	public VoucherModel(int id_voucher, String code_voucher, String name_voucher, float money_voucher,
			Date expiried_use_voucher, List<UserVoucherModel> userVouchers) {
		super();
		this.id_voucher = id_voucher;
		this.code_voucher = code_voucher;
		this.name_voucher = name_voucher;
		this.money_voucher = money_voucher;
		this.expiried_use_voucher = expiried_use_voucher;
		this.userVouchers = userVouchers;
	}
	public VoucherModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_voucher;
	
	
	@Column(nullable = false, unique=true)
	private String code_voucher;
	
	@Column 
	private String name_voucher;
	
	@Column(nullable = false)
	private float money_voucher; 
	
	@Column 
	private Date expiried_use_voucher;
	
	@OneToMany(mappedBy = "voucherModel")
	private List<UserVoucherModel> userVouchers;
	
	public int getId_voucher() {
		return id_voucher;
	}
	public void setId_voucher(int id_voucher) {
		this.id_voucher = id_voucher;
	}
	public String getCode_voucher() {
		return code_voucher;
	}
	public void setCode_voucher(String code_voucher) {
		this.code_voucher = code_voucher;
	}
	public String getName_voucher() {
		return name_voucher;
	}
	public void setName_voucher(String name_voucher) {
		this.name_voucher = name_voucher;
	}
	public float getMoney_voucher() {
		return money_voucher;
	}
	public void setMoney_voucher(float money_voucher) {
		this.money_voucher = money_voucher;
	}
	public Date getExpiried_use_voucher() {
		return expiried_use_voucher;
	}
	public void setExpiried_use_voucher(Date expiried_use_voucher) {
		this.expiried_use_voucher = expiried_use_voucher;
	}
	public List<UserVoucherModel> getUserVouchers() {
		return userVouchers;
	}
	public void setUserVouchers(List<UserVoucherModel> userVouchers) {
		this.userVouchers = userVouchers;
	}
}
