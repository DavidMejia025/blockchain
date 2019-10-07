package blockchain.node.core;

import org.springframework.beans.factory.annotation.Autowired;

import blockchain.node.utils.Clients;

public class Node{
  private String  url;
  @Autowired
  public  BlockChain chain;
  @Autowired
  Clients nodeClient;

  public Node() {}
  
  public BlockChain getBlockChain() {
    return this.chain;
  }
  
  public String pingNodes() {
	  nodeClient.sendGetRequestToAll("/ping");
	  
	  return "all nodes up and running";
  }
  
  public String broadcastBlock() {
	  return "all nodes up and running";
  }
  
  public String broadcastTransaction() {
	  return "all nodes up and running";
  }
  
  
}
