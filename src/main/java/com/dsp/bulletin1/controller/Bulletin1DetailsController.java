package com.dsp.bulletin1.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
              
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.response.GenericResponse;
import com.dsp.bulletin1.service.Bulletin1DetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping("/details")
public class Bulletin1DetailsController {

    private Bulletin1DetailsService service;

    @Autowired
    public Bulletin1DetailsController(Bulletin1DetailsService service) {
        this.service = service;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Bulletin1DetailsController.class);

    @Operation(summary = "Create a new Bulletin1", description = "Creates a new Bulletin1 and returns the created Bulletin1 details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Bulletin1 created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/create")
    public ResponseEntity<GenericResponse<Object>> createBulletin1(@RequestBody Bulletin1DetailsDTO bulletin1DTO) {
        Bulletin1DetailsDTO bulletin1DTODetails = service.createBulletin1(bulletin1DTO);
        GenericResponse<Object> response = new GenericResponse<>(HttpStatus.CREATED.value(), true,
                "Bulletin-Part1 created successfully.", bulletin1DTODetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Bulletin1", description = "Retrieves a list of all Bulletin1.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bulletin1 Details retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/getAll")
    public List<Bulletin1DetailsDTO> getAllBulletin1Details(
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Boolean draftStatus,
            @RequestParam(required = false) Long lokSabhaNo,
            @RequestParam(required = false) Long lokSabhaSessionNo,
            @RequestParam(required = false) Long rajyaSabhaSessionNo,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String status) {
        // return service.getAllBulletin1Details(sessionNo);
        return service.getBulletin1s(isActive, draftStatus, lokSabhaNo, lokSabhaSessionNo,
                rajyaSabhaSessionNo, startDate, endDate, status);
    }

    @Operation(summary = "Update Bulletin-part1 Details", description = "Updates a Bulletin1 Details by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bulletin1 Details updated successfully"),
        @ApiResponse(responseCode = "404", description = "Bulletin1 Details not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping("update/{id}")
    public ResponseEntity<GenericResponse<Bulletin1DetailsDTO>> updateBulletin1(@PathVariable Long id,
            @RequestBody Bulletin1DetailsDTO bulletin1DTO) {
        Bulletin1DetailsDTO updatedbulletin1DTO = service.updateBulletin1(id, bulletin1DTO);
        String message = "";
        if (updatedbulletin1DTO.getStatus().equalsIgnoreCase("returned")) {
            message = "Bulletin-Part1 returned successfully.";
        } else {
            message = "Bulletin-Part1 updated successfully.";

        }
        GenericResponse<Bulletin1DetailsDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
                message, updatedbulletin1DTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get Bulletin1 By Id", description = "Retrieve all Bulletin1 By its Id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bulletin1 retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("get/{id}")
    public ResponseEntity<GenericResponse<Bulletin1DetailsDTO>> getBulletin1ById(@PathVariable Long id) {
        Bulletin1DetailsDTO bulletin1DTO = service.getBulletin1ById(id);
        GenericResponse<Bulletin1DetailsDTO> response = new GenericResponse<>(HttpStatus.OK.value(), true,
                "Bulletin-Part1 Details retrieved successfully.", bulletin1DTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
