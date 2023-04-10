package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_legal")
public class PersonalLegalModel {
	public PersonalLegalModel(int id_personal_legal, String name_personal_legal,
			List<PersonalLegalUserModel> personalLegalUsers) {
		super();
		this.id_personal_legal = id_personal_legal;
		this.name_personal_legal = name_personal_legal;
		this.personalLegalUsers = personalLegalUsers;
	}
	public PersonalLegalModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_personal_legal;
	
	@Column(nullable = false)
	private String name_personal_legal;
	
	@OneToMany(mappedBy = "personalLegalModel")
	private List<PersonalLegalUserModel> personalLegalUsers;
	
	public int getId_personal_legal() {
		return id_personal_legal;
	}
	public void setId_personal_legal(int id_personal_legal) {
		this.id_personal_legal = id_personal_legal;
	}
	public String getName_personal_legal() {
		return name_personal_legal;
	}
	public void setName_personal_legal(String name_personal_legal) {
		this.name_personal_legal = name_personal_legal;
	}
	public List<PersonalLegalUserModel> getPersonalLegalUsers() {
		return personalLegalUsers;
	}
	public void setPersonalLegalUsers(List<PersonalLegalUserModel> personalLegalUsers) {
		this.personalLegalUsers = personalLegalUsers;
	}
}
