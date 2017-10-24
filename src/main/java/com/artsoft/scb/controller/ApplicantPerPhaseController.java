package com.artsoft.scb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artsoft.scb.model.bll.ApplicantPerPhaseService;
import com.artsoft.scb.model.entity.ApplicantPerPhase;

@RestController
@RequestMapping(path = "/AppPerPhase")
public class ApplicantPerPhaseController {
	
	@Autowired
	private ApplicantPerPhaseService applicantPerPhaseService;
	
	@GetMapping(path = "/byId/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		List<ApplicantPerPhase> applicantsPerPhase = applicantPerPhaseService.getApplicantPerPhaseByState(id);
		return ResponseEntity.status(HttpStatus.OK).body(applicantsPerPhase);
		
	}

}