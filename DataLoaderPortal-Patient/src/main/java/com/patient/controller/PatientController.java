package com.patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.patient.entity.Patient;
import com.patient.helper.ExcelHelper;
import com.patient.response.ResponseMessage;
import com.patient.service.PatientService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class PatientController {

	@Autowired
	PatientService patientService;

	@PostMapping("/excel/upload")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			System.out.println("in controller inside if");
			try {
				patientService.save(file);
				System.out.println(" after save");

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				System.out.println("incontroller inside try successfull");
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/allpatients")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Patient>> getAllPatients() {
		try {
			List<Patient> patients = patientService.getAllPatients();

			if (patients.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(patients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/patientbyname/{name}")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Patient> getPatientByName(@PathVariable String name) {
		try {

			Patient patients = patientService.getPatientByName(name);

			return new ResponseEntity<>(patients, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/patientbyid/{id}")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		try {

			Patient patients = patientService.getPatientById(id);

			return new ResponseEntity<>(patients, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/patient/update/{id}")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updatePatient(@RequestBody Patient patient, @PathVariable Long id) {

		patientService.updatePatient(patient, id);

		if (patient.getStatus().equals("Processed")) {

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseMessage("Patient status is in Processed, Details are not updated!"));

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Patient updated successfully!"));
		}

	}

	@PatchMapping("/patient/status/update/{id}")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updatePatientStatus(@RequestBody Patient patient, @PathVariable Long id) {

		patientService.updatePatientStatus(patient, id);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseMessage("Patient Status updated successfully!"));
	}

	@GetMapping("/excel/download")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Resource> getFile() {
		String filename = "patients.xlsx";
		InputStreamResource file = new InputStreamResource(patientService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@DeleteMapping("/delete/patient/{id}")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Patient> deletePatient(@PathVariable Long id) {
		ResponseEntity<Patient> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			patientService.deletePatient(id);
			
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

}
