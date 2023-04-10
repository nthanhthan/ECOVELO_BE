package com.example.ecovelo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_voucher",uniqueConstraints = @UniqueConstraint(columnNames = {"code_voucher", "user_id"}))
public class UserVoucherModel {
	public UserVoucherModel(int id_user_voucher, UserModel userModelVoucher, VoucherModel voucherModel) {
		super();
		this.id_user_voucher = id_user_voucher;
		this.userModelVoucher = userModelVoucher;
		this.voucherModel = voucherModel;
	}
	public UserVoucherModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_user_voucher;
	
	@OneToOne
	@JoinColumn(name= "user_id")
	private UserModel userModelVoucher;
	
	@OneToOne
	@JoinColumn(name= "code_voucher")
	private VoucherModel voucherModel;

	public int getId_user_voucher() {
		return id_user_voucher;
	}

	public void setId_user_voucher(int id_user_voucher) {
		this.id_user_voucher = id_user_voucher;
	}

	public UserModel getUserModelVoucher() {
		return userModelVoucher;
	}

	public void setUserModelVoucher(UserModel userModelVoucher) {
		this.userModelVoucher = userModelVoucher;
	}

	public VoucherModel getVoucherModel() {
		return voucherModel;
	}

	public void setVoucherModel(VoucherModel voucherModel) {
		this.voucherModel = voucherModel;
	}
}
