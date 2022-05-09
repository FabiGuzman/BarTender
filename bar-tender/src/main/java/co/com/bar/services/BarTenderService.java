package co.com.bar.services;

import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import java.util.Map;

public interface BarTenderService {

  BarTenderInformation getBarTenderInformation(BarTenderRequest request, Map<String, String> headers);
}
