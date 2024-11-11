package com.dsp.bulletin1.controller;

import java.util.List;
import java.util.UUID;

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

import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.response.GenericResponse;
import com.dsp.bulletin1.service.Bulletin1DetailsService;

import jakarta.servlet.http.HttpSession;

// import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/bulletin1/details")
public class Bulletin1DetailsController {

	private Bulletin1DetailsService service;
	@Autowired
	public Bulletin1DetailsController(Bulletin1DetailsService service) {
		this.service = service;
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(Bulletin1DetailsController.class);

	@PostMapping("/create")
	public ResponseEntity<GenericResponse<Object>> createBulletin1(@RequestBody Bulletin1DetailsDTO bulletin1DTO) 
	{
		System.out.println("uuuuuuuuu");
		System.out.println(bulletin1DTO);
		if(bulletin1DTO.getRefNo()==null)
		{
			String refNo="BTN-"+(UUID.randomUUID().toString());
			bulletin1DTO.setRefNo(refNo);
		}
		if(bulletin1DTO.getDraftStatus()==null)
		{
			bulletin1DTO.setDraftStatus(false);
		}
		System.out.println("bbbbbbbbbbbbbb");
		System.out.println(bulletin1DTO);
		Bulletin1DetailsDTO bulletin1DTODetails = service.createBulletin1(bulletin1DTO);
		GenericResponse<Object> response = new GenericResponse<>(HttpStatus.CREATED.value(), true,
				"Bulletin1 Details created successfully.",bulletin1DTODetails );
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	@GetMapping("/findByLokSabhaNo/{sessionNo}")
    public List<Bulletin1DetailsDTO> findByLokSabhaSessionNo(@PathVariable Long sessionNo) {
        return service.getByLokSabhaSessionNo(sessionNo);
    }
	@PutMapping("update/{id}")
	public ResponseEntity<GenericResponse<Bulletin1DetailsDTO>> updateBulletin1(@PathVariable Long id,
			@RequestBody Bulletin1DetailsDTO bulletin1DTO) {
				Bulletin1DetailsDTO updatedbulletin1DTO = service.updateBulletin1(id,bulletin1DTO );
		GenericResponse<Bulletin1DetailsDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin1 Details updated successfully.", updatedbulletin1DTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<GenericResponse<Bulletin1DetailsDTO>> getBulletin1ById(@PathVariable Long id) {
		Bulletin1DetailsDTO bulletin1DTO = service.getBulletin1ById(id);
		GenericResponse<Bulletin1DetailsDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin1 Details retrieved successfully.", bulletin1DTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<GenericResponse<List<Bulletin1DetailsDTO>>> getAllbulletin1s() {
		List<Bulletin1DetailsDTO> bulletin1DTO = service.getAllBulletin1s();
		GenericResponse<List<Bulletin1DetailsDTO>> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"All Bulletin1 Details retrieved successfully.", bulletin1DTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@DeleteMapping("delete/{id}")
	public ResponseEntity<GenericResponse<Void>> deletebulletin1ById(@PathVariable Long id) {
		service.deletebulletin1ById(id);
		GenericResponse<Void> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin1 Details deleted successfully.", null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/setSession/{id}")
	public ResponseEntity<String> setSessionId(@PathVariable Long id,HttpSession session) 
	{
        if (id != null) {
            service.setSessionId(id,session);
            return ResponseEntity.ok("User ID " + id + " set in session.");
        } else {
            LOGGER.warn("Attempted to set null User ID in session.");
            return ResponseEntity.badRequest().body("Invalid User ID.");
		}
    }
	@GetMapping("/getSessionId")
    public String getSessionId(HttpSession session) {
        Long userId = service.getSessionId(session);
        if (userId != null) {
            return "Session ID is: " + userId;
        } else {
            return "No Session ID found.";
        }
    }
	
	@GetMapping("/getAll/inactive")
    public List<Bulletin1DetailsDTO> getAllInactive() {
        return service.getAllInactive();
    }

	@GetMapping("/getAll/active")
    public List<Bulletin1DetailsDTO> getAllActive() {
        return service.getAllActive();
    }
}



