package co.com.bar.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import co.com.bar.model.BarTenderResponse;
import co.com.bar.services.BarTenderService;
import com.adl.bc.common.controller.lambda.ProxyRequest;
import com.adl.bc.common.controller.lambda.ProxyResponse;
import com.google.inject.Injector;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BarTenderControllerTest {

  @Mock
  private BarTenderService service;

  @Mock
  private BarTenderResponse response;

  @Spy
  private Injector injector;

  @InjectMocks
  private BarTenderController controller;

  private Writer writer;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void handlerRequestTest() {
    // setup
    ProxyRequest proxyRequest = new ProxyRequest();
    proxyRequest.setBody("{\n"
        + "  \"idSecuenceCups\": \"5\",\n"
        + "  \"iterations\": 3\n"
        + "}");
    Map<String, Object> headers = new HashMap<>();
    proxyRequest.setHeaders(headers);
    BarTenderInformation barTenderInformation = new BarTenderInformation();
    barTenderInformation.setIterations(3);
    barTenderInformation.setIdSecuenceCups("5");
    barTenderInformation.setSecuenceCups("7,10,15,11,9");
    barTenderInformation.setCode("500");
    when(service.getBarTenderInformation(Mockito.any(BarTenderRequest.class), Mockito.anyMap()))
        .thenReturn(barTenderInformation);

    response.setOutputTender(barTenderInformation.getMessage());
    response.setCode(barTenderInformation.getCode());

    // execution
    ProxyResponse response = controller.handleRequest(proxyRequest, null);
    // assertions
    assertNotNull(response);
    assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


  @Test
  public void processWarmUpEventTest() {
    // setup
    ProxyRequest proxyRequest = new ProxyRequest();

    proxyRequest.setBody("{\n"
        + "  \"idSecuenceCups\": \"5\",\n"
        + "  \"iterations\": 4\n"
        + "}");

    Map<String, Object> headers = new HashMap<>();
    proxyRequest.setHeaders(headers);
    BarTenderInformation barTenderInformation = new BarTenderInformation();
    barTenderInformation.setIterations(3);
    barTenderInformation.setIdSecuenceCups("5");
    barTenderInformation.setSecuenceCups("7,10,15,11,9");
    barTenderInformation.setMessage("7,10,15,11,9");
    barTenderInformation.setCode("0");
    when(service.getBarTenderInformation(Mockito.any(BarTenderRequest.class), Mockito.anyMap()))
        .thenReturn(barTenderInformation);

    response.setOutputTender(barTenderInformation.getMessage());
    response.setCode(barTenderInformation.getCode());

    // execution
    ProxyResponse responseExecution = controller.handleRequest(proxyRequest, null);

    // assertions
    assertEquals(200, responseExecution.getStatusCode());
  }

}
