package co.com.bar.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import co.com.bar.exceptions.ItemNotFoundException;
import co.com.bar.model.BarTenderInformation;
import co.com.bar.model.BarTenderRequest;
import co.com.bar.repository.BarTenderRepository;
import co.com.bar.utils.GenericBuilder;
import co.com.bar.utils.MessageCode;
import io.vavr.control.Try;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BarTenderComponentImplTest {

  @Mock
  private BarTenderRepository repository;

  @InjectMocks
  private BarTenderComponentImpl barTenderComponent;


  @Test
  public void getSecuenceCupsTest() {

    // setup

    BarTenderRequest barTenderRequest = new BarTenderRequest();
    barTenderRequest.setIdSecuenceCups("1");
    barTenderRequest.setIterations(3);

    Map<String, String> headers = new HashMap<>();

    BarTenderInformation barTenderInformation =
        GenericBuilder.of(BarTenderInformation::new)
            .with(BarTenderInformation::setCode, MessageCode.SUCCESSFUL.getCode())
            .with(BarTenderInformation::setSecuenceCups, "1 2 3 4 5 6")
            .build();

    when(repository.findBarTenderInformationById(Mockito.anyString()))
        .thenReturn(Try.success(Optional.of(barTenderInformation)));

    // execution

    BarTenderInformation information = barTenderComponent.getSecuenceCups(barTenderRequest);

    // assertions
    assertNotNull(information);
    assertEquals(information.getCode(), MessageCode.SUCCESSFUL.getCode());
    assertEquals(information.getSecuenceCups(), "1 2 3 4 5 6");
  }


  @Test
  public void getCityWithError() {
    // setup

    BarTenderRequest barTenderRequest = new BarTenderRequest();
    barTenderRequest.setIdSecuenceCups("1");
    barTenderRequest.setIterations(3);

    Map<String, String> headers = new HashMap<>();

    BarTenderInformation barTenderInformation =
        GenericBuilder.of(BarTenderInformation::new)
            .with(BarTenderInformation::setCode, MessageCode.SUCCESSFUL.getCode())
            .with(BarTenderInformation::setSecuenceCups, "1 2 3 4 5 6")
            .build();

    when(repository.findBarTenderInformationById(Mockito.anyString()))
        .thenReturn(
            Try.failure(
                new ItemNotFoundException(MessageCode.ID_BAR_TENDER_NOT_FOUND.getMessage("1"))));

    // execution

    BarTenderInformation response = barTenderComponent.getSecuenceCups(barTenderRequest);

    // assertions
    assertNotNull(response);
    assertEquals(MessageCode.ID_BAR_TENDER_NOT_FOUND.getMessage("1"), response.getMessage());
  }

}
