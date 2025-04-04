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
import com.skyspace666.dao.TicketDAO;
import com.skyspace666.domain.Ticket;
import com.skyspace666.dto.TicketDTO;
import com.skyspace666.dto.TicketSearchDTO;
import com.skyspace666.dto.TicketPageDTO;
import com.skyspace666.dto.TicketConvertCriteriaDTO;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import com.skyspace666.service.TicketService;
import com.skyspace666.util.ControllerUtils;





@Service
public class TicketServiceImpl extends GenericServiceImpl<Ticket, Integer> implements TicketService {

    private final static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	TicketDAO ticketDao;

	


	@Override
	public GenericDAO<Ticket, Integer> getDAO() {
		return (GenericDAO<Ticket, Integer>) ticketDao;
	}
	
	public List<Ticket> findAll () {
		List<Ticket> tickets = ticketDao.findAll();
		
		return tickets;	
		
	}

	public ResultDTO addTicket(TicketDTO ticketDTO, RequestDTO requestDTO) {

		Ticket ticket = new Ticket();

		ticket.setTicketId(ticketDTO.getTicketId());


		ticket.setPrice(ticketDTO.getPrice());


		ticket.setIssueDate(ticketDTO.getIssueDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		ticket = ticketDao.save(ticket);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Ticket> getAllTickets(Pageable pageable) {
		return ticketDao.findAll(pageable);
	}

	public Page<Ticket> getAllTickets(Specification<Ticket> spec, Pageable pageable) {
		return ticketDao.findAll(spec, pageable);
	}

	public ResponseEntity<TicketPageDTO> getTickets(TicketSearchDTO ticketSearchDTO) {
	
			Integer ticketId = ticketSearchDTO.getTicketId(); 
    			String sortBy = ticketSearchDTO.getSortBy();
			String sortOrder = ticketSearchDTO.getSortOrder();
			String searchQuery = ticketSearchDTO.getSearchQuery();
			Integer page = ticketSearchDTO.getPage();
			Integer size = ticketSearchDTO.getSize();

	        Specification<Ticket> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, ticketId, "ticketId"); 
			
			
 			

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

		Page<Ticket> tickets = this.getAllTickets(spec, pageable);
		
		//System.out.println(String.valueOf(tickets.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(tickets.getTotalPages()));
		
		List<Ticket> ticketsList = tickets.getContent();
		
		TicketConvertCriteriaDTO convertCriteria = new TicketConvertCriteriaDTO();
		List<TicketDTO> ticketDTOs = this.convertTicketsToTicketDTOs(ticketsList,convertCriteria);
		
		TicketPageDTO ticketPageDTO = new TicketPageDTO();
		ticketPageDTO.setTickets(ticketDTOs);
		ticketPageDTO.setTotalElements(tickets.getTotalElements());
		return ResponseEntity.ok(ticketPageDTO);
	}

	public List<TicketDTO> convertTicketsToTicketDTOs(List<Ticket> tickets, TicketConvertCriteriaDTO convertCriteria) {
		
		List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
		
		for (Ticket ticket : tickets) {
			ticketDTOs.add(convertTicketToTicketDTO(ticket,convertCriteria));
		}
		
		return ticketDTOs;

	}
	
	public TicketDTO convertTicketToTicketDTO(Ticket ticket, TicketConvertCriteriaDTO convertCriteria) {
		
		TicketDTO ticketDTO = new TicketDTO();
		
		ticketDTO.setTicketId(ticket.getTicketId());

	
		ticketDTO.setPrice(ticket.getPrice());

	
		ticketDTO.setIssueDate(ticket.getIssueDate());

	

		
		return ticketDTO;
	}

	public ResultDTO updateTicket(TicketDTO ticketDTO, RequestDTO requestDTO) {
		
		Ticket ticket = ticketDao.getById(ticketDTO.getTicketId());

		ticket.setTicketId(ControllerUtils.setValue(ticket.getTicketId(), ticketDTO.getTicketId()));

		ticket.setPrice(ControllerUtils.setValue(ticket.getPrice(), ticketDTO.getPrice()));

		ticket.setIssueDate(ControllerUtils.setValue(ticket.getIssueDate(), ticketDTO.getIssueDate()));



        ticket = ticketDao.save(ticket);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TicketDTO getTicketDTOById(Integer ticketId) {
	
		Ticket ticket = ticketDao.getById(ticketId);
			
		
		TicketConvertCriteriaDTO convertCriteria = new TicketConvertCriteriaDTO();
		return(this.convertTicketToTicketDTO(ticket,convertCriteria));
	}







}
