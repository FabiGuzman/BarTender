package co.com.bar.mapper;

import co.com.bar.model.BarTenderInformation;
import co.com.bar.utils.GenericBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class BarTenderMapperImpl implements BarTenderMapper {

  private static final Logger LOGGER = LogManager.getLogger(BarTenderMapperImpl.class);

  @Override
  public Try<Optional<BarTenderInformation>> map(final Item item) {

    LOGGER.debug(item.toJSON());

    return Try.of(
        () ->
            Optional.ofNullable(
                GenericBuilder.of(BarTenderInformation::new)
                    .with(BarTenderInformation::setIdSecuenceCups, item.getString(BarTenderInformation.ID_SECUENCE_CUPS))
                    .with(BarTenderInformation::setSecuenceCups, item.getString(BarTenderInformation.SECUENCE_CUPS))
                    .build()));
  }
}