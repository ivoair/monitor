package com.icarrolab.monitor.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;

import com.icarrolab.monitor.entity.MicroserviceData;

import reactor.core.publisher.Flux;

public interface MicroserviceDataRepository extends R2dbcRepository<MicroserviceData, Integer> {

	@Query("SELECT ID, MICROSERVICE, MEMORY FROM MICROSERVICEDATA WHERE MICROSERVICE = $1")
	Flux<MicroserviceData> findByMicroservice(String microservice);

}
