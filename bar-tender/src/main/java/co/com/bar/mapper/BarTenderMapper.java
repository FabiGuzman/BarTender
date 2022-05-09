package co.com.bar.mapper;

import co.com.bar.model.BarTenderInformation;
import com.amazonaws.services.dynamodbv2.document.Item;
import io.vavr.control.Try;
import java.util.Optional;

public interface BarTenderMapper {

  public Try<Optional<BarTenderInformation>> map(Item item);
}
