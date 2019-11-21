package com.icarrolab.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroserviceData {

	private Integer id;

	private String microservice;
	private double memory;

	@Override
	public String toString() {
		return "MicroserviceData [microservice=" + microservice + ", memory=" + memory + "]";
	}

}
