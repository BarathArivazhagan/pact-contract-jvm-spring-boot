# Consumer Driven Contracts Using Pact framework


<b>pact-contract-provider</b>: provider application

<b>pact-contract-consumer </b> : consumer application. 

### Start with consumer : As it is consumer driven contracts. 

##### step 1: Add below dependencies in pom.xml

```
    <dependency>
		    <groupId>au.com.dius</groupId>
		    <artifactId>pact-jvm-consumer-junit_2.11</artifactId>
		    <version>3.5.7</version>
		    <scope>test</scope>
		</dependency>
```
#### step 2: Add pact jvm plugin in pom.xml : 

```
	      <plugin>
                <groupId>au.com.dius</groupId>
                <artifactId>pact-jvm-provider-maven_2.11</artifactId>
                <version>3.5.7</version>
                <configuration>
                   <pactBrokerUrl>http://localhost:8500</pactBrokerUrl>
				            	<pactDirectory>target/pacts</pactDirectory>               
                </configuration>
        </plugin>

```

#### step 3: Write the consumer contract as in below exmaple: 

```
@Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("inventory_provider",PactSpecVersion.V3, this);
    private RestTemplate restTemplate=new RestTemplate();


    @Pact(provider = "inventory_provider", consumer = "inventory_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);


        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .stringValue("productName", "TV")
                 .stringType("locationName", "CHENNAI")               
                .integerType("quantity", 100);

        return builder.given("create inventory").uponReceiving("a request to save inventory")
                .path("/api/inventory")
                .body(bodyResponse)
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
    	headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    	HttpEntity<Object> request=new HttpEntity<Object>(inventory, headers);
    	ResponseEntity<String> responseEntity=restTemplate.postForEntity(mockProvider.getUrl()+"/api/inventory", request, String.class);
    	assertEquals("TV", JsonPath.read(responseEntity.getBody(),"$.productName"));
    	assertEquals("CHENNAI", JsonPath.read(responseEntity.getBody(),"$.locationName"));
    	assertEquals((Integer)100, (Integer)JsonPath.read(responseEntity.getBody(),"$.quantity"));
	}


```

#### step 4: Run maven build to publish the pacts to the pact broker 
```
mvn clean install pact:publish
```

#### step 5: Now go to pact provider application and write the pact provider test case 




