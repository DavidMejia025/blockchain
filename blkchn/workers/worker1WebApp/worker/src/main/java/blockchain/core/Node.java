package blockchain.core;

import org.springframework.beans.factory.annotation.Autowired;

import blockchain.utils.Clients;
import blockchain.utils.Logs;

public class Node {
  Boolean linked = false;
  String  url;
  //@Autowired
  Clients nodeClient = new Clients();
  @Autowired
  Logs logs;
  
  public Node(String url) {
    this.url = url;
    this.nodeClient.addClient(url);
  }
  
  public Boolean getNodeLinked() {
    return linked;
   }
   
   public void setNodeLinked(Boolean status) {
    this.linked = status;
   }
    
   public String getNodeUrl() {
    return this.url;
   }
}

 
