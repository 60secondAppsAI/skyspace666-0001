package com.skyspace666.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skyspace666.dao.GenericDAO;
import com.skyspace666.service.GenericService;
import com.skyspace666.service.impl.GenericServiceImpl;
import com.skyspace666.dao.InsuranceDAO;
import com.skyspace666.domain.Insurance;
import com.skyspace666.dto.InsuranceDTO;
import com.skyspace666.dto.InsuranceSearchDTO;
import com.skyspace666.dto.InsurancePageDTO;
import com.skyspace666.dto.InsuranceConvertCriteriaDTO;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import com.skyspace666.service.InsuranceService;
import com.skyspace666.util.ControllerUtils;





@Service
public class InsuranceServiceImpl extends GenericServiceImpl<Insurance, Integer> implements InsuranceService {

    private final static Logger logger = LoggerFactory.getLogger(InsuranceServiceImpl.class);

	@Autowired
	InsuranceDAO insuranceDao;

	


	@Override
	public GenericDAO<Insurance, Integer> getDAO() {
		return (GenericDAO<Insurance, Integer>) insuranceDao;
	}
	
	public List<Insurance> findAll () {
		List<Insurance> insurances = insuranceDao.findAll();
		
		return insurances;	
		
	}

	public ResultDTO addInsurance(InsuranceDTO insuranceDTO, RequestDTO requestDTO) {

		Insurance insurance = new Insurance();

		insurance.setInsuranceId(insuranceDTO.getInsuranceId());


		insurance.setProvider(insuranceDTO.getProvider());


		insurance.setCoverageAmount(insuranceDTO.getCoverageAmount());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		insurance = insuranceDao.save(insurance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Insurance> getAllInsurances(Pageable pageable) {
		return insuranceDao.findAll(pageable);
	}

	public Page<Insurance> getAllInsurances(Specification<Insurance> spec, Pageable pageable) {
		return insuranceDao.findAll(spec, pageable);
	}

	public ResponseEntity<InsurancePageDTO> getInsurances(InsuranceSearchDTO insuranceSearchDTO) {
	
			Integer insuranceId = insuranceSearchDTO.getInsuranceId(); 
 			String provider = insuranceSearchDTO.getProvider(); 
  			String sortBy = insuranceSearchDTO.getSortBy();
			String sortOrder = insuranceSearchDTO.getSortOrder();
			String searchQuery = insuranceSearchDTO.getSearchQuery();
			Integer page = insuranceSearchDTO.getPage();
			Integer size = insuranceSearchDTO.getSize();

	        Specification<Insurance> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, insuranceId, "insuranceId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, provider, "provider"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("provider")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Insurance> insurances = this.getAllInsurances(spec, pageable);
		
		//System.out.println(String.valueOf(insurances.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(insurances.getTotalPages()));
		
		List<Insurance> insurancesList = insurances.getContent();
		
		InsuranceConvertCriteriaDTO convertCriteria = new InsuranceConvertCriteriaDTO();
		List<InsuranceDTO> insuranceDTOs = this.convertInsurancesToInsuranceDTOs(insurancesList,convertCriteria);
		
		InsurancePageDTO insurancePageDTO = new InsurancePageDTO();
		insurancePageDTO.setInsurances(insuranceDTOs);
		insurancePageDTO.setTotalElements(insurances.getTotalElements());
		return ResponseEntity.ok(insurancePageDTO);
	}

	public List<InsuranceDTO> convertInsurancesToInsuranceDTOs(List<Insurance> insurances, InsuranceConvertCriteriaDTO convertCriteria) {
		
		List<InsuranceDTO> insuranceDTOs = new ArrayList<InsuranceDTO>();
		
		for (Insurance insurance : insurances) {
			insuranceDTOs.add(convertInsuranceToInsuranceDTO(insurance,convertCriteria));
		}
		
		return insuranceDTOs;

	}
	
	public InsuranceDTO convertInsuranceToInsuranceDTO(Insurance insurance, InsuranceConvertCriteriaDTO convertCriteria) {
		
		InsuranceDTO insuranceDTO = new InsuranceDTO();
		
		insuranceDTO.setInsuranceId(insurance.getInsuranceId());

	
		insuranceDTO.setProvider(insurance.getProvider());

	
		insuranceDTO.setCoverageAmount(insurance.getCoverageAmount());

	

		
		return insuranceDTO;
	}

	public ResultDTO updateInsurance(InsuranceDTO insuranceDTO, RequestDTO requestDTO) {
		
		Insurance insurance = insuranceDao.getById(insuranceDTO.getInsuranceId());

		insurance.setInsuranceId(ControllerUtils.setValue(insurance.getInsuranceId(), insuranceDTO.getInsuranceId()));

		insurance.setProvider(ControllerUtils.setValue(insurance.getProvider(), insuranceDTO.getProvider()));

		insurance.setCoverageAmount(ControllerUtils.setValue(insurance.getCoverageAmount(), insuranceDTO.getCoverageAmount()));



        insurance = insuranceDao.save(insurance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InsuranceDTO getInsuranceDTOById(Integer insuranceId) {
	
		Insurance insurance = insuranceDao.getById(insuranceId);
			
		
		InsuranceConvertCriteriaDTO convertCriteria = new InsuranceConvertCriteriaDTO();
		return(this.convertInsuranceToInsuranceDTO(insurance,convertCriteria));
	}







}