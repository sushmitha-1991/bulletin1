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

import com.dsp.bulletin1.bulletin2.dto.Bulletin2InputDTO;
import com.dsp.bulletin1.bulletin2.service.Bulletin2InputService;
import com.dsp.bulletin1.response.GenericResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/bulletin2Input")
public class Bulletin2InputController 
{
	@Autowired
	private static final Logger LOGGER =
	 LogManager.getLogger(Bulletin2Controller.class);
	 
	@Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Bulletin2InputService service;
	public Bulletin2InputController(Bulletin2InputService service) {
		this.service = service;
	}
	@Operation(summary = "Create a new Bulletin2", description = "Creates a new Bulletin2 and returns the created Lob details.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Lob created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/save")
	public ResponseEntity<GenericResponse<Bulletin2InputDTO>> createLobInput(@RequestBody Bulletin2InputDTO lobCreateDTO) 
	{
		Bulletin2InputDTO lobCreateDTODetails = service.createBulletin2(lobCreateDTO);
		GenericResponse<Bulletin2InputDTO> response = 
		new GenericResponse<>(HttpStatus.CREATED.value(), true,
				"List Of Business created successfully.",lobCreateDTODetails );
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Lob By Id", description = "Get Lob By Id and Update Lob Details by Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lob Details Updated successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PutMapping("update/{id}")
	public ResponseEntity<GenericResponse<Bulletin2InputDTO>> updateLobCreate(@PathVariable Long id,
			@RequestBody Bulletin2InputDTO bulletin2InputDTO) {
		Bulletin2InputDTO updatedBulletin2DTO = service.updateById(id, bulletin2InputDTO );
		GenericResponse<Bulletin2InputDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
				"Bulletin2 details updated successfully.", updatedBulletin2DTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@Operation(summary = "Get Bulletin2 By Id", description = "Retrieve Bulletin2 By its Id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Bulletin2 retrieved successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("get/{id}")
	public ResponseEntity<GenericResponse<Bulletin2InputDTO>> getById(@PathVariable Long id) {
		LOGGER.error("Trying to get Bulletin2 Create by Id: {}", id);
		Bulletin2InputDTO bulletin2dto = service.getById(id);
		GenericResponse<Bulletin2InputDTO> response = 
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
public ResponseEntity<List<Bulletin2InputDTO>> getFilteredBulletin2s(
        @RequestParam(required = false) Boolean isActive,
        @RequestParam(required = false) Boolean saveAsDraft,
        @RequestParam(required = false) Long lokSabhaNo,
        @RequestParam(required = false) Long lokSabhaSessionNo,
        @RequestParam(required = false) Long rajyaSabhaSessionNo,
		@RequestParam(required = false) LocalDate startDate,
		@RequestParam(required = false) LocalDate endDate) {
    List<Bulletin2InputDTO> dto = service.getFilteredBulletins(isActive,saveAsDraft,lokSabhaNo,
	lokSabhaSessionNo,rajyaSabhaSessionNo,startDate,endDate) ;
    return ResponseEntity.ok(dto);
}

}




