package com.amazonaws.lambda.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetLambdaFunctionHandler implements RequestHandler<Integer, CustomerResponse>  {

	private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "CustomerData";
 
    @Override
    public CustomerResponse handleRequest(
      Integer id, Context context) {
  
        this.initDynamoDbClient();
 
        Item item = getData(id);
 
        CustomerResponse customerResponse = new CustomerResponse();
        if(item != null) {
        	customerResponse.setMessage(item.toJSON());
        }
        else {
        	customerResponse.setMessage("Item not found");
        }
        return customerResponse;
    }
 
    private Item getData(Integer id) 
      throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME).getItem("id",id);
    }
    
    private void initDynamoDbClient() {
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        this.dynamoDb = new DynamoDB(client);
    }

}

