package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.TravelInsurance;
import com.skyspace666.dto.TravelInsuranceDTO;
import com.skyspace666.dto.TravelInsuranceSearchDTO;
import com.skyspace666.dto.TravelInsurancePageDTO;
import com.skyspace666.dto.TravelInsuranceConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TravelInsuranceService extends GenericService<TravelInsurance, Integer> {

	List<TravelInsurance> findAll();

	ResultDTO addTravelInsurance(TravelInsuranceDTO travelInsuranceDTO, RequestDTO requestDTO);

	ResultDTO updateTravelInsurance(TravelInsuranceDTO travelInsuranceDTO, RequestDTO requestDTO);

    Page<TravelInsurance> getAllTravelInsurances(Pageable pageable);

    Page<TravelInsurance> getAllTravelInsurances(Specification<TravelInsurance> spec, Pageable pageable);

	ResponseEntity<TravelInsurancePageDTO> getTravelInsurances(TravelInsuranceSearchDTO travelInsuranceSearchDTO);
	
	List<TravelInsuranceDTO> convertTravelInsurancesToTravelInsuranceDTOs(List<TravelInsurance> travelInsurances, TravelInsuranceConvertCriteriaDTO convertCriteria);

	TravelInsuranceDTO getTravelInsuranceDTOById(Integer travelInsuranceId);







}





