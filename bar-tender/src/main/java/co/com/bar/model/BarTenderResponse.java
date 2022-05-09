package co.com.bar.model;

import co.com.adl.commons.errors.dto.Response;

public class BarTenderResponse extends Response {

  private String outputTender;

  public String getOutputTender() {
    return outputTender;
  }

  public void setOutputTender(final String outputTender) {
    this.outputTender = outputTender;
  }
}
