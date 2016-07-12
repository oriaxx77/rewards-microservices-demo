package com.oriaxx77.microservicedemo.discovery;


import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.oriaxx77.microservicedemo.discovery.ServiceDiscoveryServerApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=ServiceDiscoveryServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
public class ServiceDiscoveryServerApplicationTests {

	@Value("${local.server.port}")
	private int port = 0;
	
	@Test
	public void catalogLoads() {
		assertUrlAccess( "http://localhost:" + port + "/eureka/apps" );
	}
	
	@Test
	public void adminLoads() {
		assertUrlAccess( "http://localhost:" + port + "/env" ) ;
	}
	
	private void assertUrlAccess( String url) {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate().getForEntity( url, Map.class);
		assertEquals( HttpStatus.OK, entity.getStatusCode() );
	}
	
}
