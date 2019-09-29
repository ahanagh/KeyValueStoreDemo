package com.amazonaws.lambda.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<CustomerRequest, CustomerResponse> {
	
	private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "CustomerData";
 
    @Override
    public CustomerResponse handleRequest(
      CustomerRequest CustomerRequest, Context context) {
  
        this.initDynamoDbClient();
 
        persistData(CustomerRequest);
 
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setMessage("Saved Successfully!!!");
        return customerResponse;
    }
 
    private PutItemOutcome persistData(CustomerRequest CustomerRequest) 
      throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
          .putItem(
            new PutItemSpec().withItem(new Item()
              .withInt("id", CustomerRequest.getId())
              .withString("firstName", CustomerRequest.getFirstName())
              .withString("lastName", CustomerRequest.getLastName())
              .withString("gender", CustomerRequest.getGender())
              .withString("address", CustomerRequest.getAddress())));
    }
    
    private void initDynamoDbClient() {
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        this.dynamoDb = new DynamoDB(client);
    }

}

