package com.icarrolab.monitor.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;

@Configuration
public class RSocketClientConfiguration {

	@Value("${app.rsocket.client.port}")
	int clientPort;

	@Bean
	public RSocket rSocket() {
		return RSocketFactory.connect()
				.mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
				.frameDecoder(PayloadDecoder.ZERO_COPY).transport(TcpClientTransport.create(this.clientPort)).start()
				.block();
	}

	@Bean
	RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
		return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON,
				rSocketStrategies);
	}

}
