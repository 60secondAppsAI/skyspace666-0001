package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.Baggage;
import com.skyspace666.dto.BaggageDTO;
import com.skyspace666.dto.BaggageSearchDTO;
import com.skyspace666.dto.BaggagePageDTO;
import com.skyspace666.dto.BaggageConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BaggageService extends GenericService<Baggage, Integer> {

	List<Baggage> findAll();

	ResultDTO addBaggage(BaggageDTO baggageDTO, RequestDTO requestDTO);

	ResultDTO updateBaggage(BaggageDTO baggageDTO, RequestDTO requestDTO);

    Page<Baggage> getAllBaggages(Pageable pageable);

    Page<Baggage> getAllBaggages(Specification<Baggage> spec, Pageable pageable);

	ResponseEntity<BaggagePageDTO> getBaggages(BaggageSearchDTO baggageSearchDTO);
	
	List<BaggageDTO> convertBaggagesToBaggageDTOs(List<Baggage> baggages, BaggageConvertCriteriaDTO convertCriteria);

	BaggageDTO getBaggageDTOById(Integer baggageId);







}





