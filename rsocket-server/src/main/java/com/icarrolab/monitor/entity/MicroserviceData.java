package com.icarrolab.monitor.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table("MICROSERVICEDATA")
@AllArgsConstructor
@NoArgsConstructor
public class MicroserviceData {

	@Id
	private Integer id;

	private String microservice;
	private double memory;


	public static MicroserviceData fromException(Exception e) {
		MicroserviceData microserviceData = new MicroserviceData();
		microserviceData.setMicroservice(e.getMessage());
		return microserviceData;
	}
}
