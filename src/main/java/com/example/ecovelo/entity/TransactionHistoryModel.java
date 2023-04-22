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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistoryModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private float money;
	
	@Column 
	private Date datetimeTransaction;

	@Column 
	private String titleTransaction;
	
	@Column 
	private boolean statusTransaction;
	
	@Column 
	private boolean mainAccount;
	
	@OneToOne
	@JoinColumn(name= "user_id")
	private UserModel userModelTransaction;
}
