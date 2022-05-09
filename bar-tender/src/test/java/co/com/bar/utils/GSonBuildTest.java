package co.com.bar.utils;

import static org.junit.Assert.assertNotNull;

import co.com.bar.model.BarTenderRequest;
import org.junit.Test;

public class GSonBuildTest {

  @Test
  public void toStringTest() {

    //setup

    BarTenderRequest barTenderRequest = new BarTenderRequest();
    barTenderRequest.setIterations(4);
    barTenderRequest.setIdSecuenceCups("3");

    //executions
    String jsonString = GsonUtil.toString(barTenderRequest);

    //asserts

    assertNotNull(jsonString);
  }

}
