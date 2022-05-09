package co.com.bar.services;

import co.com.bar.components.BarTenderComponent;
import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import co.com.bar.utils.GenericBuilder;
import co.com.bar.utils.MessageCode;
import com.google.inject.Inject;
import io.vavr.control.Try;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarTenderServiceImpl implements BarTenderService {

  private static final Logger LOGGER = LogManager.getLogger(BarTenderServiceImpl.class);
  private final BarTenderInformation barTenderInformation;
  private BarTenderComponent barTender;

  @Inject
  public BarTenderServiceImpl(
      final BarTenderComponent barTender, final BarTenderInformation barTenderInformation) {
    this.barTender = barTender;
    this.barTenderInformation = barTenderInformation;
  }

  @Override
  public BarTenderInformation getBarTenderInformation(final BarTenderRequest request,
      final Map<String, String> headers) {
    LOGGER.debug("********** getBarTenderInformation Start **********");

    return Try.of(() -> barTender.getSecuenceCups(request))
        .map(secuence -> {

          final BarTenderInformation barTenderInfo = new BarTenderInformation();
          LOGGER.info("Recovery secuence cups {} ", secuence.getSecuenceCups());
          barTenderInfo.setIterations(request.getIterations());
          barTenderInfo.setIdSecuenceCups(request.getIdSecuenceCups());
          barTenderInfo.setSecuenceCups(secuence.getSecuenceCups());
          LOGGER.info("BarTender iterations {} ", barTenderInfo.getIterations());
          LOGGER.info("BarTender secuenceCups {} ", barTenderInfo.getSecuenceCups());
          LOGGER.info("BarTender idSecuenceCups {} ", barTenderInfo.getIdSecuenceCups());
          barTenderInfo.buildOutput();
          barTenderInfo.setCode(secuence.getCode());
          barTenderInfo.setMessage(barTenderInfo.getOutput());
          return barTenderInfo;
        })
        .getOrElse(
            GenericBuilder.of(BarTenderInformation::new)
                .with(
                    BarTenderInformation::setCode,
                    MessageCode.ID_BAR_TENDER_NOT_FOUND.getCode())
                .with(
                    BarTenderInformation::setMessage,
                    MessageCode.ID_BAR_TENDER_NOT_FOUND.getMessage(request.getIdSecuenceCups()))
                .build());

  }
}