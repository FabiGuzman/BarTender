package co.com.bar.utils;

public enum MessageCode {
  SUCCESSFUL("0", "SUCCESS", "Transaction realized successfully"),
  ID_BAR_TENDER_NOT_FOUND("500", "ERROR", "Secuence identified by id %s was not found"),
  BAD_REQUEST("400", "ERROR", "Something went wrong when the request fields were validated");

  private String code;
  private String type;
  private String message;

  MessageCode(final String code, String type, final String message) {
    this.code = code;
    this.type = type;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    StringBuilder finalMessage = new StringBuilder();
    finalMessage.append(type)
        .append("-")
        .append(code)
        .append(": ")
        .append(message);
    return finalMessage.toString();
  }

  public String getMessage(String... details) {
    StringBuilder finalMessage = new StringBuilder();
    String messageFormatted = String.format(this.message, details);
    finalMessage.append(type)
        .append("-")
        .append(code)
        .append(": ")
        .append(messageFormatted);
    return finalMessage.toString();
  }
}
