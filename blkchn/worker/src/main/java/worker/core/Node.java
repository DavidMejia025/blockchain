package blockchain.core;

import org.springframework.beans.factory.annotation.Autowired;

import blockchain.utils.HttpClient;
import blockchain.utils.Logs;

public class Node {
  Boolean linked = false;
  String  url;  
  HttpClient httpClient = new HttpClient();
  @Autowired
  Logs logs;
  
  public Node(String url) {
    this.url = url;
    this.httpClient.addClient(url);
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

 
