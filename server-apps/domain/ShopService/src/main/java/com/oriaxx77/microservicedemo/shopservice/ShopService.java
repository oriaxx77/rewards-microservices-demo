package com.oriaxx77.microservicedemo.shopservice;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Component
public class ShopService {

	private static final Logger LOG = LoggerFactory.getLogger(ShopService.class);
	
	// Spring RestTemplate as a Load Balancer Client
	// It is automatically configured to use ribbon
	// See the RibbonAutoConfiguration class
	// It doesn't work in this SpringBoot version.
	// So we use LoadBalancerClient instead
	
	
	// private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${shop-service.hello-message}")
	String helloMessage;

//	@Bean
//	@LoadBalanced
//	RestTemplate restTemplate()
//	{
//		return new RestTemplate();
//	}
	
	
//	@Autowired
//    private LoadBalancerClient loadBalancer;
	
	
	public String hello(){
		return helloMessage;
	}
	
	
	@HystrixCommand(fallbackMethod = "defaultHome")
	public String home() throws RestClientException, URISyntaxException{
		
		
		String content = restTemplate.getForEntity( new URI( "http://product-service" ) , String.class).getBody();
		LOG.info( "Load balanced response: {}", content );
		return content;
		
//		// Ribbon is configured. Use a service name instead of a
//		// real URL. E.g. http://{service-name in eureka}/{endpoint url}
//		// You can use Feigh instead. It is a declarativel, interface based
//		// Rest client APIS
//		String serviceId = "product-service";
//		ServiceInstance instance = loadBalancer.choose(serviceId);
//        URI uri = instance.getUri();
//        LOG.debug("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);
//		
//        // TODO: work on this. Use Ribbon with rest-template automatically. new URI( "http://product-service/")
//        // Check fetchregistry of the ribbonclient etc...
//       	return restTemplate.getForEntity( uri, String.class ).getBody();
	}
	
	public String defaultHome(){
		return "Default hystrix response from shop-service";
	}
	
	/*
	@RequestMapping("/{productId}")
    @HystrixCommand(fallbackMethod = "defaultProductComposite")
    public ResponseEntity<String> getProductComposite(
        @PathVariable int productId,
        @RequestHeader(value="Authorization") String authorizationHeader,
        Principal currentUser) {

        LOG.info("ProductApi: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);
        URI uri = loadBalancer.choose("productcomposite").getUri();
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("GetProductComposite from URL: {}", url);

        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        LOG.info("GetProductComposite http-status: {}", result.getStatusCode());
        LOG.debug("GetProductComposite body: {}", result.getBody());

        return result;
    }


    public ResponseEntity<String> defaultProductComposite(
        @PathVariable int productId,
        @RequestHeader(value="Authorization") String authorizationHeader,
        Principal currentUser) {

        LOG.warn("Using fallback method for product-composite-service. User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);
        return new ResponseEntity<String>("", HttpStatus.BAD_GATEWAY);
    }
	 */
	
	
	
}
