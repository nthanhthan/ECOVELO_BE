package com.example.ecovelo.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VoucherModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(nullable = false, unique=true)
	private String codeVoucher;
	
	@Column 
	private String nameVoucher;
	
	@Column(nullable = false)
	private float moneyVoucher; 
	
	@Column 
	private Date expiriedUseVoucher;
	
	@OneToMany(mappedBy = "voucherModel")
	private List<UserVoucherModel> userVouchers;
}
