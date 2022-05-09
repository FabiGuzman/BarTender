package co.com.bar.repository;

import co.com.bar.exceptions.ItemNotFoundException;
import co.com.bar.mapper.BarTenderMapper;
import co.com.bar.model.BarTenderInformation;
import co.com.bar.utils.MessageCode;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.google.inject.Inject;
import io.vavr.control.Try;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarTenderRepositoryImpl implements BarTenderRepository {

  private final DynamoDB dynamoDb;
  private final BarTenderMapper barTenderMapper;
  private final String tableName;

  private static final Logger LOGGER = LogManager.getLogger(BarTenderRepositoryImpl.class);

  @Inject
  public BarTenderRepositoryImpl(
      final DynamoDB dynamoDb,
      final BarTenderMapper barTenderMapper,
      final String tableName) {
    this.dynamoDb = dynamoDb;
    this.barTenderMapper = barTenderMapper;
    this.tableName = tableName;
    LOGGER.debug(tableName);
  }

  @Override
  public Try<Optional<BarTenderInformation>> findBarTenderInformationById(final String idSecuenceCups) {
    LOGGER.debug("********** findBarTenderInformationById started **********");
    LOGGER.info("idSecuenceCups : {} ",idSecuenceCups);
    return Try.of(() -> dynamoDb.getTable(tableName))
        .map(table -> Optional.ofNullable(table.getItem(getPrimaryKey(idSecuenceCups))))
        .map(
            item ->
                item.orElseThrow(
                    () ->
                        new ItemNotFoundException(MessageCode.ID_BAR_TENDER_NOT_FOUND.getMessage(String.valueOf(idSecuenceCups)))))
        .flatMap(barTenderMapper::map);
  }

  private PrimaryKey getPrimaryKey(final String idSecuenceCups) {
    final PrimaryKey primaryKey = new PrimaryKey();
    LOGGER.debug("findBarTenderInformationByIdSecuenceCups: {}", idSecuenceCups);
    primaryKey.addComponent("idSecuenceCups", idSecuenceCups);
    return primaryKey;
  }
}