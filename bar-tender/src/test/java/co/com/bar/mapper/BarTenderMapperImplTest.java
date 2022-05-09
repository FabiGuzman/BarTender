package co.com.bar.mapper;

import static org.junit.Assert.assertEquals;

import co.com.bar.model.BarTenderInformation;
import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.Test;

public class BarTenderMapperImplTest {

  public static final String ID_SECUENCE_CUPS = "idSecuenceCups";
  public static final String SECUENCE_CUPS = "secuenceCups";

  @Test
  public void mapTest() {
    // setup
    Item item = new Item();
    item.withString(BarTenderInformation.ID_SECUENCE_CUPS, ID_SECUENCE_CUPS);
    item.withString(BarTenderInformation.SECUENCE_CUPS, SECUENCE_CUPS);

    BarTenderMapperImpl customerMapper = new BarTenderMapperImpl();
    // executions
    BarTenderInformation cityInformation = customerMapper.map(item).get().get();
    // asserts
    assertEquals(ID_SECUENCE_CUPS, BarTenderInformation.ID_SECUENCE_CUPS);
    assertEquals(SECUENCE_CUPS, BarTenderInformation.SECUENCE_CUPS);
  }

}
