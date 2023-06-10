package com.example.ecovelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.PersonalLegalModel;

public interface LegalRepository extends JpaRepository<PersonalLegalModel, Integer> {

}
