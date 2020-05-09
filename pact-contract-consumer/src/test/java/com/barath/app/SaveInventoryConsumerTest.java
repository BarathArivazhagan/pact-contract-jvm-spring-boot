package com.barath.app;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;



public class SaveInventoryConsumerTest{
	
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("inventory_provider","localhost", 8080, this);
    private RestTemplate restTemplate=new RestTemplate();


    @Pact(provider = "inventory_provider", consumer = "inventory_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);


        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .stringValue("productName", "TV")
                 .stringType("locationName", "CHENNAI")               
                .integerType("quantity", 100);

        return builder
        		.given("create inventory").uponReceiving("a request to save inventory")
                .path("/api/inventory")
                .body(bodyResponse)
                .headers(headers)
                .method(RequestMethod.POST.name())
                .willRespondWith()
                .headers(headers)
                .status(200).body(bodyResponse).toPact();
    }

   

	
	
	@Test
	@PactVerification
	public void testCreateInventoryConsumer() throws IOException {
		
		Inventory inventory=new Inventory("TV", "CHENNAI", 100);
    	HttpHeaders headers=new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Object> request=new HttpEntity<Object>(inventory, headers);
    	System.out.println("MOCK provider URL"+mockProvider.getUrl());
    	ResponseEntity<String> responseEntity=restTemplate.postForEntity(mockProvider.getUrl()+"/api/inventory", request, String.class);
    	assertEquals("TV", JsonPath.read(responseEntity.getBody(),"$.productName"));
    	assertEquals("CHENNAI", JsonPath.read(responseEntity.getBody(),"$.locationName"));
    	assertEquals((Integer)100, (Integer)JsonPath.read(responseEntity.getBody(),"$.quantity"));
	}

}
