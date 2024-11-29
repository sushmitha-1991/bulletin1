package com.dsp.bulletin1.bulletin2.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dsp.bulletin1.bulletin2.dto.Bulletin2DTO;
import com.dsp.bulletin1.bulletin2.service.Bulletin2Service;
import com.dsp.bulletin1.response.GenericResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/bulletin2")
public class Bulletin2Controller 
{
	@Autowired
	private static final Logger LOGGER =
	 LogManager.getLogger(Bulletin2Controller.class);
	 
	@Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Bulletin2Service service;
	public Bulletin2Controller(Bulletin2Service service) {
		this.service = service;
	}
	@Operation(summary = "Create a new Bulletin2", description = "Creates a new Bulletin2 and returns the created Lob details.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Lob created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/save")
	public ResponseEntity<GenericResponse<Bulletin2DTO>> createLobInput(@RequestBody Bulletin2DTO lobCreateDTO) 
	{
		Bulletin2DTO lobCreateDTODetails = service.createBulletin2(lobCreateDTO);
		GenericResponse<Bulletin2DTO> response = 
		new GenericResponse<>(HttpStatus.CREATED.value(), true,
				"List Of Business created successfully.",lobCreateDTODetails );
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Lob By Id", description = "Get Lob By Id and Update Lob Details by Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lob Details Updated successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PutMapping("update/{id}")
	public ResponseEntity<GenericResponse<Bulletin2DTO>> updateLobCreate(@PathVariable Long id,
			@RequestBody Bulletin2DTO bulletin2DTO) {
		Bulletin2DTO updatedbulletin2DTO = service.updateById(id,bulletin2DTO );
		GenericResponse<Bulletin2DTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin2 details updated successfully.", updatedbulletin2DTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@Operation(summary = "Get Bulletin2 By Id", description = "Retrieve Bulletin2 By its Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Bulletin2 retrieved successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("get/{id}")
	public ResponseEntity<GenericResponse<Bulletin2DTO>> getById(@PathVariable Long id) {
		LOGGER.error("Trying to get Bulletin2 Create by Id: {}", id);
		Bulletin2DTO bulletin2dto = service.getById(id);
		GenericResponse<Bulletin2DTO> response = 
		new GenericResponse<>(HttpStatus.OK.value(), true,
		"Bulletin2 Input retrieved successfully.", bulletin2dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@Operation(summary = "Delete Bulletin2 By Id", description = "Delete Bulletin2 By its Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete retrieved successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@DeleteMapping("delete/{id}")
	public ResponseEntity<GenericResponse<Void>> deleteLobById(@PathVariable Long id) {
		service.deleteById(id);
GenericResponse<Void> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"List Of Business deleted successfully.", null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
@Operation(summary = "Get all Bulletin2s", description = "Retrieves a list of all Bulletin2s.")
@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Bulletin2 Details retrieved successfully"),
		@ApiResponse(responseCode = "500", description = "Internal server error") })
@GetMapping("/getAll")
public ResponseEntity<List<Bulletin2DTO>> getFilteredBulletin2s(
        @RequestParam(required = false) Boolean isActive,
        @RequestParam(required = false) Boolean saveAsDraft,
        @RequestParam(required = false) Long lokSabhaNo,
        @RequestParam(required = false) Long lokSabhaSessionNo,
        @RequestParam(required = false) Long rajyaSabhaSessionNo,
		@RequestParam(required = false) LocalDate startDate,
		@RequestParam(required = false) LocalDate endDate) {
    List<Bulletin2DTO> dto = service.getFilteredBulletins(isActive,saveAsDraft,lokSabhaNo,
	lokSabhaSessionNo,rajyaSabhaSessionNo,startDate,endDate) ;
    return ResponseEntity.ok(dto);
}

}




