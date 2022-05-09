package co.com.bar.model;

import java.io.Serializable;

public class BarTenderRequest implements Serializable {

  private static final long serialVersionUID = 6817873680895418071L;

  private String idSecuenceCups;
  private int iterations;

  public BarTenderRequest() {
  }

  public BarTenderRequest(final String idSecuenceCups, int iterations) {
    this.idSecuenceCups = idSecuenceCups;
    this.iterations = iterations;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getIdSecuenceCups() {
    return idSecuenceCups;
  }

  public void setIdSecuenceCups(final String idSecuenceCups) {
    this.idSecuenceCups = idSecuenceCups;
  }

  public int getIterations() {
    return iterations;
  }

  public void setIterations(final int iterations) {
    this.iterations = iterations;
  }
}
