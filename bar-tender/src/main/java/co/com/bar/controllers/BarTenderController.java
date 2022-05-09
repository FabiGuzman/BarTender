package co.com.bar.controllers;

import static co.com.bar.model.BarTenderInformation.ID_SECUENCE_CUPS;
import static co.com.bar.model.BarTenderInformation.ITERATIONS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import co.com.bar.model.BarTenderResponse;
import co.com.bar.utils.MessageCode;
import co.com.bar.config.BarTenderConfig;
import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import co.com.bar.services.BarTenderService;
import co.com.bar.utils.GenericBuilder;
import co.com.bar.utils.ResponseWrapper;
import com.adl.bc.common.controller.lambda.ProxyRequest;
import com.adl.bc.common.controller.lambda.ProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarTenderController implements RequestHandler<ProxyRequest, ProxyResponse> {

  private static final Logger LOGGER = LogManager.getLogger(BarTenderController.class);

  private BarTenderService service;
  private BarTenderResponse response;

  private Injector injector;

  @Override
  public ProxyResponse handleRequest(final ProxyRequest proxyRequest, final Context context) {
    if (Objects.isNull(proxyRequest.getBody())) {
      LOGGER.info("Lambda is warming up");
      return null;
    }

    BarTenderInformation barTender = validateRequest(proxyRequest);
    if (barTender.getCode().equals(MessageCode.BAD_REQUEST.getCode())) {
      LOGGER.info("The request is bad formed : {} ", proxyRequest.getBodyString());
      BarTenderResponse barTenderResponse = new BarTenderResponse();
      barTenderResponse.setCode(barTender.getCode());
      barTenderResponse.setMessage(barTender.getMessage());
      return ResponseWrapper.withStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
          .withResponse(barTenderResponse)
          .build();
    } else {
      LOGGER.info("The request is successfully formed : {} ", proxyRequest.getBodyString());
    }

    service = getService();

    final BarTenderRequest request =
        GenericBuilder.of(BarTenderRequest::new)
            .with(
                BarTenderRequest::setIdSecuenceCups,
                proxyRequest.getBody().get(ID_SECUENCE_CUPS).toString())
            .with(
                BarTenderRequest::setIterations,
                (int) (Double.parseDouble(proxyRequest.getBody().get(ITERATIONS).toString())))
            .build();
    LOGGER.debug("idSecuenceCups: {}", request.getIdSecuenceCups());
    final Map<String, String> headers = new HashMap<>();
    if (proxyRequest.getHeaders() != null && !proxyRequest.getHeaders().isEmpty()) {
      proxyRequest.getHeaders().forEach((k, v) -> headers.put(k, String.valueOf(v)));
    }
    final BarTenderInformation barTenderInformation = service.getBarTenderInformation(request, headers);
    response = new BarTenderResponse();
    response.setOutputTender(barTenderInformation.getMessage());
    response.setCode(barTenderInformation.getCode());

    return ResponseWrapper.withStatusCode(
        response.getCode().compareTo(MessageCode.SUCCESSFUL.getCode()) == INTEGER_ZERO
            ? HttpStatus.SC_OK
            : HttpStatus.SC_INTERNAL_SERVER_ERROR)
        .withResponse(response)
        .build();
  }

  public BarTenderInformation validateRequest(ProxyRequest proxyRequest) {
    try {
          proxyRequest.getBody().get(ID_SECUENCE_CUPS).toString();
          int iteration = (int) (Double.parseDouble(proxyRequest.getBody().get(ITERATIONS).toString()));
    } catch (NumberFormatException | NullPointerException exception){
      return GenericBuilder.of(BarTenderInformation::new)
          .with(BarTenderInformation::setCode, MessageCode.BAD_REQUEST.getCode())
          .with(
              BarTenderInformation::setMessage,
              MessageCode.BAD_REQUEST.getMessage())
          .build();
    }

    return GenericBuilder.of(BarTenderInformation::new)
        .with(BarTenderInformation::setCode, MessageCode.SUCCESSFUL.getCode())
        .build();
  }

  private BarTenderService getService() {
    if (injector == null) {
      this.injector = Guice.createInjector(new BarTenderConfig());
    }
    return service == null ? injector.getInstance(BarTenderService.class) : service;
  }

  public void setInjector(final Injector injector) {
    this.injector = injector;
  }
}