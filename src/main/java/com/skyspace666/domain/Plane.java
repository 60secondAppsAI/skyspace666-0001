package com.skyspace666.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="planes")
@Getter @Setter @NoArgsConstructor
public class Plane {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="plane_id")
	private Integer planeId;
    
  	@Column(name="model")
	private String model;
    
  	@Column(name="capacity")
	private int capacity;
    
  	@Column(name="manufacturer")
	private String manufacturer;
    
	




}
