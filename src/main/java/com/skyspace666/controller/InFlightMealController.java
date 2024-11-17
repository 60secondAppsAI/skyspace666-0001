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

import com.skyspace666.domain.InFlightMeal;
import com.skyspace666.dto.InFlightMealDTO;
import com.skyspace666.dto.InFlightMealSearchDTO;
import com.skyspace666.dto.InFlightMealPageDTO;
import com.skyspace666.service.InFlightMealService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/inFlightMeal")
@RestController
public class InFlightMealController {

	private final static Logger logger = LoggerFactory.getLogger(InFlightMealController.class);

	@Autowired
	InFlightMealService inFlightMealService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InFlightMeal> getAll() {

		List<InFlightMeal> inFlightMeals = inFlightMealService.findAll();
		
		return inFlightMeals;	
	}

	@GetMapping(value = "/{inFlightMealId}")
	@ResponseBody
	public InFlightMealDTO getInFlightMeal(@PathVariable Integer inFlightMealId) {
		
		return (inFlightMealService.getInFlightMealDTOById(inFlightMealId));
	}

 	@RequestMapping(value = "/addInFlightMeal", method = RequestMethod.POST)
	public ResponseEntity<?> addInFlightMeal(@RequestBody InFlightMealDTO inFlightMealDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = inFlightMealService.addInFlightMeal(inFlightMealDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/inFlightMeals")
	public ResponseEntity<InFlightMealPageDTO> getInFlightMeals(InFlightMealSearchDTO inFlightMealSearchDTO) {
 
		return inFlightMealService.getInFlightMeals(inFlightMealSearchDTO);
	}	

	@RequestMapping(value = "/updateInFlightMeal", method = RequestMethod.POST)
	public ResponseEntity<?> updateInFlightMeal(@RequestBody InFlightMealDTO inFlightMealDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = inFlightMealService.updateInFlightMeal(inFlightMealDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
