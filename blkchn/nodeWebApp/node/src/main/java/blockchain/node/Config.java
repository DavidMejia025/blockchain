package blockchain.node;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blockchain.node.core.BlockChain;
import blockchain.node.core.BlockChainImpl;
import blockchain.node.core.Node;

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
}
