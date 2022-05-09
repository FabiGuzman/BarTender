package co.com.bar.components;

import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;

public interface BarTenderComponent {

  BarTenderInformation getSecuenceCups(BarTenderRequest request);
}
