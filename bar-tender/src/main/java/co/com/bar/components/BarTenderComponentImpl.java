package co.com.bar.components;

import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import co.com.bar.repository.BarTenderRepository;
import co.com.bar.utils.GenericBuilder;
import co.com.bar.utils.MessageCode;
import com.google.inject.Inject;
import io.vavr.control.Try;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarTenderComponentImpl implements BarTenderComponent {

  private static final Logger LOGGER = LogManager.getLogger(BarTenderComponentImpl.class);

  private BarTenderRepository repository;

  @Inject
  public BarTenderComponentImpl(final BarTenderRepository repository) {
    this.repository = repository;
  }

  @Override
  public BarTenderInformation getSecuenceCups(final BarTenderRequest request) {

    LOGGER.debug("Initing get process BarTender for id : {} ", request.getIdSecuenceCups());

    return
        Try.of(() -> repository.findBarTenderInformationById(request.getIdSecuenceCups())
        .filter(Optional::isPresent)
        .map(Optional::get)
            .map(c -> GenericBuilder.of(BarTenderInformation::new)
                .with(BarTenderInformation::setCode, MessageCode.SUCCESSFUL.getCode())
                .with(BarTenderInformation::setSecuenceCups, c.getSecuenceCups())
                .build())).get()
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
