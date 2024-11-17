package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.InFlightMeal;
import com.skyspace666.dto.InFlightMealDTO;
import com.skyspace666.dto.InFlightMealSearchDTO;
import com.skyspace666.dto.InFlightMealPageDTO;
import com.skyspace666.dto.InFlightMealConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InFlightMealService extends GenericService<InFlightMeal, Integer> {

	List<InFlightMeal> findAll();

	ResultDTO addInFlightMeal(InFlightMealDTO inFlightMealDTO, RequestDTO requestDTO);

	ResultDTO updateInFlightMeal(InFlightMealDTO inFlightMealDTO, RequestDTO requestDTO);

    Page<InFlightMeal> getAllInFlightMeals(Pageable pageable);

    Page<InFlightMeal> getAllInFlightMeals(Specification<InFlightMeal> spec, Pageable pageable);

	ResponseEntity<InFlightMealPageDTO> getInFlightMeals(InFlightMealSearchDTO inFlightMealSearchDTO);
	
	List<InFlightMealDTO> convertInFlightMealsToInFlightMealDTOs(List<InFlightMeal> inFlightMeals, InFlightMealConvertCriteriaDTO convertCriteria);

	InFlightMealDTO getInFlightMealDTOById(Integer inFlightMealId);







}





