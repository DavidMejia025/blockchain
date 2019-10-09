package blockchain;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.core.Worker;
import blockchain.utils.Logs;

@Configuration
public class Config {
  @Bean
  public Worker WorkerBean() {
    return new Worker("http://localhost:8082");
  }
  
  /*@Bean
  public WebClient WebCLientBean() { //refresh DI constructor to pass arguments such as url. and also what if i want to create two or more Webclient beans
	  WebClient client = WebClient
		  .builder()
		    .baseUrl("http://localhost:8072")
		    .defaultCookie("cookieKey", "cookieValue")
		    //.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json") 
		    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8071"))
		  .build();
	  
	  return client;  
  }*/
  
  @Bean
  public Logs LogsBean() {
	return new Logs();
  }
}
