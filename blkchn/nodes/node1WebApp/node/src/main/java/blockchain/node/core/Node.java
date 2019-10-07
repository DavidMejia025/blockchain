package blockchain.node.core;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class Node{
  private String  url;
  @Autowired
  public  BlockChain chain;

  public Node() {}
  
  public BlockChain getBlockChain() {
    return this.chain;
  }
}
