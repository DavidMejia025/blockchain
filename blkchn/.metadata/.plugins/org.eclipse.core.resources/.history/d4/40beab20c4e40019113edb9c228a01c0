package blockchain.node;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blockchain.node.core.BlockChain;
import blockchain.node.core.BlockChainImpl;
import blockchain.node.core.Node;
import blockchain.node.core.NodeImpl;

@Configuration
public class Config {
  @Bean
  public Node NodeBean() {
    return new NodeImpl("8081");
  }
  
  @Bean
  public BlockChain BlockChainBean() {
    return new BlockChainImpl();
  }
}
