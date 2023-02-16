package com.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patient.entity.Patient;


public interface PatientRepository extends JpaRepository<Patient, Long>{

	Optional<Patient> findByName(String name);

}
