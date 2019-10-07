package blockchain.node;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.node.core.BlockChain;
import blockchain.node.core.BlockChainImpl;
import blockchain.node.core.Node;
import blockchain.node.utils.Lists;
import blockchain.node.utils.Logs;

@Configuration
public class Config {
  @Bean
  public Node NodeBean() {
    return new Node();
  }
  
  @Bean
  public BlockChain BlockChainBean() {
    return new BlockChainImpl();
  }
  
  @Bean
  public Logs LogsBean() {
	return new Logs();
  }
  
  @Bean
  public Lists ListsBean() {
	return new Lists();
  }

  @Bean
  public WebClient WebCLientBean() { //refresh DI constructor to pass arguments such as url. and also what if i want to create two or more Webclient beans
	  WebClient client = WebClient
		  .builder()
		    .baseUrl("http://node1:8071")
		    .defaultCookie("cookieKey", "cookieValue")
		    //.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json") 
		    .defaultUriVariables(Collections.singletonMap("url", "http://node1:8071"))
		  .build();
	  
	  return client;  
  }
}
