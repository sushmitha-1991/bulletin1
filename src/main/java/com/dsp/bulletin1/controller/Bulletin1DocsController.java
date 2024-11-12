package com.dsp.bulletin1.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dsp.bulletin1.dto.Bulletin1DocsDTO;
import com.dsp.bulletin1.response.GenericResponse;
import com.dsp.bulletin1.service.Bulletin1DocsService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/bulletin1/docs")
public class Bulletin1DocsController 
{

    private static final Logger LOGGER = LoggerFactory.getLogger(Bulletin1DocsController.class);
	// private static final String EXTERNAL_API_URL = "http://localhost:8887/bulletin1create/getSessionId";

    @Autowired
    private Bulletin1DocsService service;
	public Bulletin1DocsController(Bulletin1DocsService service) {
		this.service = service;
	}

	@PostMapping("/save")
	public ResponseEntity<GenericResponse<Bulletin1DocsDTO>> createBulletin1(@RequestBody Bulletin1DocsDTO bulletin1DocsDTO) 
	{
		Bulletin1DocsDTO bulletin1DTODocs = service.createBulletin1(bulletin1DocsDTO);
		GenericResponse<Bulletin1DocsDTO> response = new GenericResponse<>(HttpStatus.CREATED.value(), true,
				"Bulletin1 Docs created successfully.",bulletin1DTODocs );
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	@PutMapping("update/{id}")
	public ResponseEntity<GenericResponse<Bulletin1DocsDTO>> updateBulletin1(@PathVariable Long id,
			@RequestBody Bulletin1DocsDTO bulletin1DTO) {
				Bulletin1DocsDTO updatedBulletin1DTO = service.updateBulletin1(id,bulletin1DTO );
		GenericResponse<Bulletin1DocsDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin1 Docs updated successfully.", updatedBulletin1DTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<GenericResponse<Bulletin1DocsDTO>> getBulletin1ById(@PathVariable Long id) {
		Bulletin1DocsDTO bulletin1DocsDTO = service.getBulletin1ById(id);

		GenericResponse<Bulletin1DocsDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin1 Docs retrieved successfully.", bulletin1DocsDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<GenericResponse<List<Bulletin1DocsDTO>>> getAllbulletin1s() {
		List<Bulletin1DocsDTO> bulletin1DocsDTO = service.getAllBulletin1s();
		GenericResponse<List<Bulletin1DocsDTO>> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"All Bulletin1 Details retrieved successfully.", bulletin1DocsDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}




