package co.com.bar.exceptions;

public class ItemNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7350435682815643474L;

  public ItemNotFoundException(final String message) {
    super(message);
  }

  public ItemNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ItemNotFoundException(final Throwable cause) {
    super(cause);
  }
}
