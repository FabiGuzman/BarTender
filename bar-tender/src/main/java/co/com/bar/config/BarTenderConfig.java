package co.com.bar.config;

import co.com.bar.components.BarTenderComponent;
import co.com.bar.components.BarTenderComponentImpl;
import co.com.bar.mapper.BarTenderMapper;
import co.com.bar.mapper.BarTenderMapperImpl;
import co.com.bar.repository.BarTenderRepository;
import co.com.bar.repository.BarTenderRepositoryImpl;
import co.com.bar.services.BarTenderService;
import co.com.bar.services.BarTenderServiceImpl;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class BarTenderConfig extends AbstractModule {

  private static final String DYNAMO_DB_TABLE = "dynamoDbTable";

  @Override
  protected void configure() {
    bind(BarTenderRepository.class).to(BarTenderRepositoryImpl.class);
    bind(BarTenderMapper.class).to(BarTenderMapperImpl.class);
    bind(BarTenderComponent.class).to(BarTenderComponentImpl.class);
    bind(BarTenderService.class).to(BarTenderServiceImpl.class);
  }

  @Provides
  DynamoDB dynamoDb() {
    final AmazonDynamoDB clientDynamoDb = AmazonDynamoDBClientBuilder.standard().build();
    return new DynamoDB(clientDynamoDb);
  }

  @Provides
  String tableName() {
    return System.getenv(DYNAMO_DB_TABLE);
  }
}
