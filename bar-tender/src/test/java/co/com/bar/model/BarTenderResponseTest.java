package co.com.bar.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BarTenderResponseTest {

  @Test
  public void fieldsRequestTest() {

    //setup

    BarTenderResponse barTenderResponse = new BarTenderResponse();
    barTenderResponse.setOutputTender("2 3 4 5 6");

    //asserts

    assertEquals("2 3 4 5 6", barTenderResponse.getOutputTender());
  }
}
