package co.com.bar.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ItemNotFoundExceptionTest {

  @Test
  public void exceptionWithConstructorTest() {

    //setup

    ItemNotFoundException itemNotFoundException = new ItemNotFoundException(
        new Throwable("exception one"));

    //asserts

    assertEquals("java.lang.Throwable: exception one", itemNotFoundException.getMessage());
  }

}
