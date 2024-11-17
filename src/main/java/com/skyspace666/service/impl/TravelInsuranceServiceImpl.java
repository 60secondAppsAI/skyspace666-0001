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
import com.skyspace666.dao.TravelInsuranceDAO;
import com.skyspace666.domain.TravelInsurance;
import com.skyspace666.dto.TravelInsuranceDTO;
import com.skyspace666.dto.TravelInsuranceSearchDTO;
import com.skyspace666.dto.TravelInsurancePageDTO;
import com.skyspace666.dto.TravelInsuranceConvertCriteriaDTO;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import com.skyspace666.service.TravelInsuranceService;
import com.skyspace666.util.ControllerUtils;





@Service
public class TravelInsuranceServiceImpl extends GenericServiceImpl<TravelInsurance, Integer> implements TravelInsuranceService {

    private final static Logger logger = LoggerFactory.getLogger(TravelInsuranceServiceImpl.class);

	@Autowired
	TravelInsuranceDAO travelInsuranceDao;

	


	@Override
	public GenericDAO<TravelInsurance, Integer> getDAO() {
		return (GenericDAO<TravelInsurance, Integer>) travelInsuranceDao;
	}
	
	public List<TravelInsurance> findAll () {
		List<TravelInsurance> travelInsurances = travelInsuranceDao.findAll();
		
		return travelInsurances;	
		
	}

	public ResultDTO addTravelInsurance(TravelInsuranceDTO travelInsuranceDTO, RequestDTO requestDTO) {

		TravelInsurance travelInsurance = new TravelInsurance();

		travelInsurance.setTravelInsuranceId(travelInsuranceDTO.getTravelInsuranceId());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		travelInsurance = travelInsuranceDao.save(travelInsurance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<TravelInsurance> getAllTravelInsurances(Pageable pageable) {
		return travelInsuranceDao.findAll(pageable);
	}

	public Page<TravelInsurance> getAllTravelInsurances(Specification<TravelInsurance> spec, Pageable pageable) {
		return travelInsuranceDao.findAll(spec, pageable);
	}

	public ResponseEntity<TravelInsurancePageDTO> getTravelInsurances(TravelInsuranceSearchDTO travelInsuranceSearchDTO) {
	
			Integer travelInsuranceId = travelInsuranceSearchDTO.getTravelInsuranceId(); 
 			String sortBy = travelInsuranceSearchDTO.getSortBy();
			String sortOrder = travelInsuranceSearchDTO.getSortOrder();
			String searchQuery = travelInsuranceSearchDTO.getSearchQuery();
			Integer page = travelInsuranceSearchDTO.getPage();
			Integer size = travelInsuranceSearchDTO.getSize();

	        Specification<TravelInsurance> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, travelInsuranceId, "travelInsuranceId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<TravelInsurance> travelInsurances = this.getAllTravelInsurances(spec, pageable);
		
		//System.out.println(String.valueOf(travelInsurances.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(travelInsurances.getTotalPages()));
		
		List<TravelInsurance> travelInsurancesList = travelInsurances.getContent();
		
		TravelInsuranceConvertCriteriaDTO convertCriteria = new TravelInsuranceConvertCriteriaDTO();
		List<TravelInsuranceDTO> travelInsuranceDTOs = this.convertTravelInsurancesToTravelInsuranceDTOs(travelInsurancesList,convertCriteria);
		
		TravelInsurancePageDTO travelInsurancePageDTO = new TravelInsurancePageDTO();
		travelInsurancePageDTO.setTravelInsurances(travelInsuranceDTOs);
		travelInsurancePageDTO.setTotalElements(travelInsurances.getTotalElements());
		return ResponseEntity.ok(travelInsurancePageDTO);
	}

	public List<TravelInsuranceDTO> convertTravelInsurancesToTravelInsuranceDTOs(List<TravelInsurance> travelInsurances, TravelInsuranceConvertCriteriaDTO convertCriteria) {
		
		List<TravelInsuranceDTO> travelInsuranceDTOs = new ArrayList<TravelInsuranceDTO>();
		
		for (TravelInsurance travelInsurance : travelInsurances) {
			travelInsuranceDTOs.add(convertTravelInsuranceToTravelInsuranceDTO(travelInsurance,convertCriteria));
		}
		
		return travelInsuranceDTOs;

	}
	
	public TravelInsuranceDTO convertTravelInsuranceToTravelInsuranceDTO(TravelInsurance travelInsurance, TravelInsuranceConvertCriteriaDTO convertCriteria) {
		
		TravelInsuranceDTO travelInsuranceDTO = new TravelInsuranceDTO();
		
		travelInsuranceDTO.setTravelInsuranceId(travelInsurance.getTravelInsuranceId());

	

		
		return travelInsuranceDTO;
	}

	public ResultDTO updateTravelInsurance(TravelInsuranceDTO travelInsuranceDTO, RequestDTO requestDTO) {
		
		TravelInsurance travelInsurance = travelInsuranceDao.getById(travelInsuranceDTO.getTravelInsuranceId());

		travelInsurance.setTravelInsuranceId(ControllerUtils.setValue(travelInsurance.getTravelInsuranceId(), travelInsuranceDTO.getTravelInsuranceId()));



        travelInsurance = travelInsuranceDao.save(travelInsurance);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TravelInsuranceDTO getTravelInsuranceDTOById(Integer travelInsuranceId) {
	
		TravelInsurance travelInsurance = travelInsuranceDao.getById(travelInsuranceId);
			
		
		TravelInsuranceConvertCriteriaDTO convertCriteria = new TravelInsuranceConvertCriteriaDTO();
		return(this.convertTravelInsuranceToTravelInsuranceDTO(travelInsurance,convertCriteria));
	}







}
