package com.icarrolab.monitor.controller;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icarrolab.monitor.model.MicroserviceData;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class MicroMonitorizedController {

	@Value("${spring.application.name}")
	private String microservice;

	@Autowired
	private RSocketRequester rSocketRequester;

//    @GetMapping(value = "/current/{stock}")
//	public Publisher<MicroserviceData> current(@PathVariable("stock") String stock) {
//		return rSocketRequester.route("currentMicroserviceData").data(new MonitorizedDataRequest(stock))
//				.retrieveMono(MicroserviceData.class);
//    }
//
//    @GetMapping(value = "/feed/{stock}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Publisher<MicroserviceData> feed(@PathVariable("stock") String stock) {
//		return rSocketRequester.route("feedMicroserviceData").data(new MonitorizedDataRequest(stock))
//				.retrieveFlux(MicroserviceData.class);
//    }

    @GetMapping(value = "/collect")
    public Publisher<Void> collect() {
    	MicroserviceData msData = getMicroserviceData();
		log.info("Data send: {}", msData.toString());
		return rSocketRequester.route("collectMicroserviceData").data(msData).send();
    }

	@GetMapping(value = "/hello")
	public Publisher<String> hello() {
		return Mono.just("Hola figura!!!");
	}

	private MicroserviceData getMicroserviceData() {

		System.gc();
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;

		return new MicroserviceData(0, microservice, usedMB);
    }
}
