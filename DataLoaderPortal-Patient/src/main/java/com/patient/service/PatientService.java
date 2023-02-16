package com.patient.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.patient.entity.Patient;
import com.patient.exception.ResourceNotFoundExceptionHandler;
import com.patient.helper.ExcelHelper;
import com.patient.repository.PatientRepository;


@Service
public class PatientService {

	@Autowired
	PatientRepository repository;

	public void save(MultipartFile file) {
		try {
			System.out.println("service inside try");
			List<Patient> patients = ExcelHelper.excelToPatients(file.getInputStream());
			System.out.println("after list "+patients);
			repository.saveAll(patients);
			System.out.println("Service after file save inside try");
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		List<Patient> patients = repository.findAll();

		ByteArrayInputStream in = ExcelHelper.patientsToExcel(patients);
		return in;
	}

	public List<Patient> getAllPatients() {
		return repository.findAll();
	}

	public Patient getPatientByName(String name) {

		Optional<Patient> patients = repository.findByName(name);

		if(patients.get().getStatus().equals("Processed")) {

			return null;
		}else {

			return patients.get();
		}
	}

	public Patient getPatientById(Long id) {

		Optional<Patient> patients = repository.findById(id);

		if(patients.get().getStatus().equals("Processed")) {

			return null;
		}else {

			return patients.get();
		}
	}

	public Patient updatePatient(Patient patient, Long id) {

		Patient existPatient = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundExceptionHandler("Patient", "id", id));

		if(patient.getStatus().equals("Processed")) {
			return null;
		}else {

			//existPatient.setId(patient.getId());
			existPatient.setName(patient.getName());
			existPatient.setAddress(patient.getAddress());
			existPatient.setDob(patient.getDob());
			existPatient.setEmail(patient.getEmail());
			existPatient.setNumber(patient.getNumber());

			Patient result = repository.save(existPatient);

			return result;
		}

	}

	public Patient updatePatientStatus(Patient patient, Long id) {

		Patient existPatient = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundExceptionHandler("Patient", "id", id));

		existPatient.setStatus(patient.getStatus());

		Patient result = repository.save(existPatient);

		return result;

	}

	public void deletePatient(Long id) {

		repository.deleteById(id);

	}

}
