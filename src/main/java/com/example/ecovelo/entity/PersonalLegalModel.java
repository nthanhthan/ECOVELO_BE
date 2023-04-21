package com.example.ecovelo.entity;

import java.util.List;

import com.example.ecovelo.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class PersonalLegalModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String namePersonalLegal;
	
	@OneToMany(mappedBy = "personalLegalModel")
	private List<PersonalLegalUserModel> personalLegalUsers;
}
