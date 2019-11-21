package blockchain.node;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blockchain.node.core.BlockChain;
import blockchain.node.core.BlockChainImpl;
import blockchain.node.core.Node;
import blockchain.node.utils.HttpClient;
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
    return new BlockChain();
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
  public HttpClient HttpCLientBean() { //refresh DI constructor to pass arguments such as url. and also what if i want to create two or more Webclient beans
	  return  new HttpClient();  
  }
}
