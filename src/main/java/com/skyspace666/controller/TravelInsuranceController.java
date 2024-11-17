package com.skyspace666.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.skyspace666.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.skyspace666.domain.TravelInsurance;
import com.skyspace666.dto.TravelInsuranceDTO;
import com.skyspace666.dto.TravelInsuranceSearchDTO;
import com.skyspace666.dto.TravelInsurancePageDTO;
import com.skyspace666.service.TravelInsuranceService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/travelInsurance")
@RestController
public class TravelInsuranceController {

	private final static Logger logger = LoggerFactory.getLogger(TravelInsuranceController.class);

	@Autowired
	TravelInsuranceService travelInsuranceService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<TravelInsurance> getAll() {

		List<TravelInsurance> travelInsurances = travelInsuranceService.findAll();
		
		return travelInsurances;	
	}

	@GetMapping(value = "/{travelInsuranceId}")
	@ResponseBody
	public TravelInsuranceDTO getTravelInsurance(@PathVariable Integer travelInsuranceId) {
		
		return (travelInsuranceService.getTravelInsuranceDTOById(travelInsuranceId));
	}

 	@RequestMapping(value = "/addTravelInsurance", method = RequestMethod.POST)
	public ResponseEntity<?> addTravelInsurance(@RequestBody TravelInsuranceDTO travelInsuranceDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = travelInsuranceService.addTravelInsurance(travelInsuranceDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/travelInsurances")
	public ResponseEntity<TravelInsurancePageDTO> getTravelInsurances(TravelInsuranceSearchDTO travelInsuranceSearchDTO) {
 
		return travelInsuranceService.getTravelInsurances(travelInsuranceSearchDTO);
	}	

	@RequestMapping(value = "/updateTravelInsurance", method = RequestMethod.POST)
	public ResponseEntity<?> updateTravelInsurance(@RequestBody TravelInsuranceDTO travelInsuranceDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = travelInsuranceService.updateTravelInsurance(travelInsuranceDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
