package co.com.bar.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BarTenderRequestTest {

  @Test
  public void fieldsRequestTest() {

    //setup

    BarTenderRequest barTenderRequest = new BarTenderRequest();
    barTenderRequest.setIterations(5);
    barTenderRequest.setIdSecuenceCups("2");

    //asserts

    assertEquals(5, barTenderRequest.getIterations());
    assertEquals("2", barTenderRequest.getIdSecuenceCups());
  }
}
