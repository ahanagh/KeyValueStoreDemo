package com.amazonaws.lambda.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteLambdaFunctionHandler implements RequestHandler<Integer, CustomerResponse>  {

    private String DYNAMODB_TABLE_NAME = "CustomerData";
 
	@Override
    public CustomerResponse handleRequest(
      Integer id, Context context) {
 
        DeleteItemOutcome item = deleteData(id);
        CustomerResponse customerResponse = new CustomerResponse();
        if(item != null) {
        	customerResponse.setMessage(String.valueOf(id) + " deleted");
        }
        else {
        	customerResponse.setMessage("Item not found");
        }
        
        return customerResponse;
    }
 
    private DeleteItemOutcome deleteData(Integer id) 
      throws ConditionalCheckFailedException {
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    	DynamoDB dynamoDB = new DynamoDB(client);
    	Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);
    	DeleteItemOutcome outcome = table.deleteItem("id", id);
        return outcome;
        
    }

}

