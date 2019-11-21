package com.icarrolab.monitor.monitorized;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketRequester.RequestSpec;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.icarrolab.monitor.controller.MicroMonitorizedController;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(value = MicroMonitorizedController.class)
public class MicroMonitorizedControllerTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private RSocketRequester rSocketRequester;

    @Mock
    private RequestSpec requestSpec;

	// @Test
    public void whenInitiatesFireAndForget_ThenGetsNoResponse() throws Exception {
		when(rSocketRequester.route("collectMicroserviceData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        when(requestSpec.send()).thenReturn(Mono.empty());

        testClient.get()
                  .uri("/collect")
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody(Void.class);
    }
}
