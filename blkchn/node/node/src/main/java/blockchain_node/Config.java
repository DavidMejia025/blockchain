package blockchain_node;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blockchain_node.core.BlockChainImpl;
import blockchain_node.core.Node;
import blockchain_node.core.BlockChain;
import blockchain_node.core.Node;


@Configuration
public class Config {
  @Bean
  public BlockChain BlockChainBean() {
    return new BlockChainImpl();
  }
  
  @Bean
  public Node NodeBean() {
    return new Node("192.123.43");
  }
}