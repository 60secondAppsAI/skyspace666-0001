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
import com.skyspace666.dao.PlaneDAO;
import com.skyspace666.domain.Plane;
import com.skyspace666.dto.PlaneDTO;
import com.skyspace666.dto.PlaneSearchDTO;
import com.skyspace666.dto.PlanePageDTO;
import com.skyspace666.dto.PlaneConvertCriteriaDTO;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import com.skyspace666.service.PlaneService;
import com.skyspace666.util.ControllerUtils;





@Service
public class PlaneServiceImpl extends GenericServiceImpl<Plane, Integer> implements PlaneService {

    private final static Logger logger = LoggerFactory.getLogger(PlaneServiceImpl.class);

	@Autowired
	PlaneDAO planeDao;

	


	@Override
	public GenericDAO<Plane, Integer> getDAO() {
		return (GenericDAO<Plane, Integer>) planeDao;
	}
	
	public List<Plane> findAll () {
		List<Plane> planes = planeDao.findAll();
		
		return planes;	
		
	}

	public ResultDTO addPlane(PlaneDTO planeDTO, RequestDTO requestDTO) {

		Plane plane = new Plane();

		plane.setPlaneId(planeDTO.getPlaneId());


		plane.setModel(planeDTO.getModel());


		plane.setCapacity(planeDTO.getCapacity());


		plane.setManufacturer(planeDTO.getManufacturer());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		plane = planeDao.save(plane);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Plane> getAllPlanes(Pageable pageable) {
		return planeDao.findAll(pageable);
	}

	public Page<Plane> getAllPlanes(Specification<Plane> spec, Pageable pageable) {
		return planeDao.findAll(spec, pageable);
	}

	public ResponseEntity<PlanePageDTO> getPlanes(PlaneSearchDTO planeSearchDTO) {
	
			Integer planeId = planeSearchDTO.getPlaneId(); 
 			String model = planeSearchDTO.getModel(); 
  			String manufacturer = planeSearchDTO.getManufacturer(); 
 			String sortBy = planeSearchDTO.getSortBy();
			String sortOrder = planeSearchDTO.getSortOrder();
			String searchQuery = planeSearchDTO.getSearchQuery();
			Integer page = planeSearchDTO.getPage();
			Integer size = planeSearchDTO.getSize();

	        Specification<Plane> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, planeId, "planeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, model, "model"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, manufacturer, "manufacturer"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("model")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("manufacturer")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Plane> planes = this.getAllPlanes(spec, pageable);
		
		//System.out.println(String.valueOf(planes.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(planes.getTotalPages()));
		
		List<Plane> planesList = planes.getContent();
		
		PlaneConvertCriteriaDTO convertCriteria = new PlaneConvertCriteriaDTO();
		List<PlaneDTO> planeDTOs = this.convertPlanesToPlaneDTOs(planesList,convertCriteria);
		
		PlanePageDTO planePageDTO = new PlanePageDTO();
		planePageDTO.setPlanes(planeDTOs);
		planePageDTO.setTotalElements(planes.getTotalElements());
		return ResponseEntity.ok(planePageDTO);
	}

	public List<PlaneDTO> convertPlanesToPlaneDTOs(List<Plane> planes, PlaneConvertCriteriaDTO convertCriteria) {
		
		List<PlaneDTO> planeDTOs = new ArrayList<PlaneDTO>();
		
		for (Plane plane : planes) {
			planeDTOs.add(convertPlaneToPlaneDTO(plane,convertCriteria));
		}
		
		return planeDTOs;

	}
	
	public PlaneDTO convertPlaneToPlaneDTO(Plane plane, PlaneConvertCriteriaDTO convertCriteria) {
		
		PlaneDTO planeDTO = new PlaneDTO();
		
		planeDTO.setPlaneId(plane.getPlaneId());

	
		planeDTO.setModel(plane.getModel());

	
		planeDTO.setCapacity(plane.getCapacity());

	
		planeDTO.setManufacturer(plane.getManufacturer());

	

		
		return planeDTO;
	}

	public ResultDTO updatePlane(PlaneDTO planeDTO, RequestDTO requestDTO) {
		
		Plane plane = planeDao.getById(planeDTO.getPlaneId());

		plane.setPlaneId(ControllerUtils.setValue(plane.getPlaneId(), planeDTO.getPlaneId()));

		plane.setModel(ControllerUtils.setValue(plane.getModel(), planeDTO.getModel()));

		plane.setCapacity(ControllerUtils.setValue(plane.getCapacity(), planeDTO.getCapacity()));

		plane.setManufacturer(ControllerUtils.setValue(plane.getManufacturer(), planeDTO.getManufacturer()));



        plane = planeDao.save(plane);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PlaneDTO getPlaneDTOById(Integer planeId) {
	
		Plane plane = planeDao.getById(planeId);
			
		
		PlaneConvertCriteriaDTO convertCriteria = new PlaneConvertCriteriaDTO();
		return(this.convertPlaneToPlaneDTO(plane,convertCriteria));
	}







}
