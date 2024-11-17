package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.Insurance;
import com.skyspace666.dto.InsuranceDTO;
import com.skyspace666.dto.InsuranceSearchDTO;
import com.skyspace666.dto.InsurancePageDTO;
import com.skyspace666.dto.InsuranceConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InsuranceService extends GenericService<Insurance, Integer> {

	List<Insurance> findAll();

	ResultDTO addInsurance(InsuranceDTO insuranceDTO, RequestDTO requestDTO);

	ResultDTO updateInsurance(InsuranceDTO insuranceDTO, RequestDTO requestDTO);

    Page<Insurance> getAllInsurances(Pageable pageable);

    Page<Insurance> getAllInsurances(Specification<Insurance> spec, Pageable pageable);

	ResponseEntity<InsurancePageDTO> getInsurances(InsuranceSearchDTO insuranceSearchDTO);
	
	List<InsuranceDTO> convertInsurancesToInsuranceDTOs(List<Insurance> insurances, InsuranceConvertCriteriaDTO convertCriteria);

	InsuranceDTO getInsuranceDTOById(Integer insuranceId);







}





