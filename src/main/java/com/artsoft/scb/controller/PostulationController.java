package com.artsoft.scb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artsoft.scb.model.bll.PostulationService;
import com.artsoft.scb.model.entity.Postulation;

@RestController
@RequestMapping(path = "/postulation")

public class PostulationController {

	@Autowired
	private PostulationService postulationService;
	
	@GetMapping(path = "/getPostulation/{mail:.+}")
	public ResponseEntity<?> getPostulationByMail(@PathVariable("mail") String mail){
		List<Postulation> postulations;
		try {
			postulations = postulationService.getPostulationsByEmail(mail);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(postulations);
	}
}
