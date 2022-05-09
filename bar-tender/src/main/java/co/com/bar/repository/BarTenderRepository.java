package co.com.bar.repository;

import co.com.bar.model.BarTenderInformation;
import io.vavr.control.Try;

import java.util.Optional;

public interface BarTenderRepository {

  Try<Optional<BarTenderInformation>> findBarTenderInformationById(String idSecuenceCups);
}
