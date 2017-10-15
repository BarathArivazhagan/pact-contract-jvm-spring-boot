package com.barath.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(PactRunner.class)
@PactBroker(host="localhost",port="8500")
@Provider("test_provider")
public class SaveInventoryProviderTest {
	
	private static MockMvc mockMvc;
	
	private static ConfigurableApplicationContext context;
	
	private static final ObjectMapper mapper=new ObjectMapper();
	
  @BeforeClass
  public static void setUpContext(){
	  context= SpringApplication.run(PactContractProviderApplication.class);
	  mockMvc=MockMvcBuilders.webAppContextSetup((WebApplicationContext) context).build();
  }
  
  
  @TestTarget
  public final Target target = new HttpTarget(8080);
  
  @State(value="a request to save inventory")
  public void test() throws Exception{
	  
	  Inventory inventory=new Inventory(1L, "TV", "CHENNAI", 100);
	  mockMvc.perform(post("/api/inventory").content(mapper.writeValueAsString(inventory)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
	  		.andExpect(status().isOk())
	  		.andExpect(jsonPath("$.productName").value("TV"));
  }
}