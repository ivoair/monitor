package com.icarrolab.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.icarrolab.monitor.entity.MicroserviceData;
import com.icarrolab.monitor.model.MonitorizedDataRequest;
import com.icarrolab.monitor.repository.MicroserviceDataRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class RsocketServerController {

	private Integer mySequence = 1;

	@Autowired
	MicroserviceDataRepository microserviceDataRepository;

	@MessageMapping("currentMicroserviceData")
	public Flux<MicroserviceData> currentMicroserviceData(MonitorizedDataRequest monitorizedDataRequest) {
		return microserviceDataRepository.findByMicroservice(monitorizedDataRequest.getMicroservice());
	}

	@MessageMapping("collectMicroserviceData")
	public Mono<Void> collectMicroserviceData(MicroserviceData microserviceData) {
		microserviceData.setId(mySequence);
		mySequence++;
		microserviceDataRepository.save(microserviceData).subscribe();
		return Mono.empty();
	}

	@MessageMapping("feedMicroserviceData")
	public Flux<MicroserviceData> feedMicroserviceData(MonitorizedDataRequest monitorizedDataRequest) {
		return microserviceDataRepository.findAll();
	}

	@MessageExceptionHandler
	public Mono<MicroserviceData> handleException(Exception e) {
		return Mono.just(MicroserviceData.fromException(e));
	}
}
