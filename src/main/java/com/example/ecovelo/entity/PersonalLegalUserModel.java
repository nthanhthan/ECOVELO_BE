package com.example.ecovelo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class PersonalLegalUserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String identifyFront;
	
	@Column(nullable = false)
	private String identifyBackside;
	
	@ManyToOne
	@JoinColumn(name= "id_personal_legal")
	private PersonalLegalModel personalLegalModel;
	
	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelLegal;
}
