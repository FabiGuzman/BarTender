package co.com.bar.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import co.com.bar.components.BarTenderComponent;
import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import co.com.bar.utils.GenericBuilder;
import co.com.bar.utils.MessageCode;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BarTenderServiceImplTest {

  @Mock
  private BarTenderComponent barTenderComponent;

  @Mock
  private BarTenderInformation barTenderInformation;

  @InjectMocks
  private BarTenderServiceImpl service;

  @Test
  public void getBarTenderInformationWithResponseSuccess() {

    // setup

    BarTenderInformation barTenderInformation = GenericBuilder.of(BarTenderInformation::new)
        .with(BarTenderInformation::setCode, MessageCode.SUCCESSFUL.getCode())
        .with(BarTenderInformation::setSecuenceCups, "7,10,15,11,9")
        .build();

    BarTenderRequest barTenderRequest = new BarTenderRequest();
    barTenderRequest.setIterations(2);
    barTenderRequest.setIdSecuenceCups("5");

    Map<String, String> headers = new HashMap<>();

    when(barTenderComponent.getSecuenceCups(Mockito.any(BarTenderRequest.class)))
        .thenReturn(barTenderInformation);

    //executions

    BarTenderInformation barTender = service.getBarTenderInformation(barTenderRequest, headers);

    // assertions

    Assert.assertNotNull(barTender);
    assertEquals(barTender.getCode(), MessageCode.SUCCESSFUL.getCode());
    assertEquals("5", barTender.getIdSecuenceCups());
    assertEquals(2, (int) barTender.getIterations());

  }


  @Test
  public void getCityInformationWithResponseNotSuccess() {

    // setup

    BarTenderInformation cityInformation = GenericBuilder.of(BarTenderInformation::new)
        .with(BarTenderInformation::setCode, "11100")
        .with(BarTenderInformation::setMessage, MessageCode.SUCCESSFUL.getMessage())
        .build();

    BarTenderRequest barTenderRequest = new BarTenderRequest();
    barTenderRequest.setIdSecuenceCups("1");
    barTenderRequest.setIterations(3);

    Map<String, String> headers = new HashMap<>();

    when(barTenderComponent.getSecuenceCups(Mockito.any(BarTenderRequest.class)))
        .thenReturn(cityInformation);

    //executions

    BarTenderInformation barTenderInformation = service.getBarTenderInformation(barTenderRequest, headers);

    // assertions

    Assert.assertNotNull(barTenderInformation);
    assertEquals("500", barTenderInformation.getCode());
    assertEquals(barTenderInformation.getMessage(), MessageCode.ID_BAR_TENDER_NOT_FOUND.getMessage("1"));

  }

}
