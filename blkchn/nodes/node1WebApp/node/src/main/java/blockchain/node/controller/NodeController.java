package blockchain.node.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.node.core.Block;
import blockchain.node.core.BlockChain;
import blockchain.node.core.Node;
import blockchain.node.core.Transaction;
import blockchain.node.utils.Clients;
import blockchain.node.utils.Lists;
import blockchain.node.utils.Logs;

@RestController
public class NodeController {
  @Autowired
  Node node;
  @Autowired
  Lists list;
  @Autowired
  Logs logs;
  
  @GetMapping("/init")
  public String nodeInit(String[] args) {
	BlockChain blockchain = node.getBlockChain();
    Block      root       = blockchain.getLastBlock();
    
    return "Blockchain with root node that has previous hash eq = " + root.getPrevHash() + " was created";
  }
  
  @GetMapping("/ping")
  public String ping(String[] args) {
    
    return "Node 1 is up and running !!!";
  }
  
  @GetMapping("/ping-nodes")
  public void pingNodes(String[] args) {
	  node.pingNodes();
  }
  
  @PostMapping(value = "/mine", headers="Accept=application/json", consumes = "application/JSON")
  public Map<String, Boolean> postMine(@RequestBody String params) throws IOException, Exception {
    String[] mineParams = parseJsonMine(params);
    
    logs.addLog("receiving a new nonce: " + mineParams[1] + "from: " + mineParams[0]);
    
    BlockChain blockchain = node.getBlockChain();
    
    Map<String, Boolean> response = blockchain.mineBlock(Integer.parseInt(mineParams[1]), mineParams[0]);
    
    return response;
  }
  
  @GetMapping("/last-nonce")
  public String getLastNounce(String[] args) {
	BlockChain blockchain = node.getBlockChain();
    Block      block       = blockchain.getLastBlock();
    
    JSONObject json = new JSONObject();
    
    json.put("difficulty", blockchain.getDifficulty());
    json.put("nonce", block.getNonce());
    
    return json.toString();
  }
  
  @GetMapping("/blockchain-info")
  public String getBlockChainInfo(String[] args) {
	BlockChain blockchain = node.getBlockChain();
    Block      block       = blockchain.getLastBlock();
    
    logs.addLog("BlockChain length: " + blockchain.getIndex());
    logs.addLog("Previews hash: " + block.getPrevHash());
    
    List<Transaction> pendingTransactions = blockchain.getPendingTransactions();
    
    list.printOutTransactions(pendingTransactions);
    
    JSONObject json = new JSONObject();
    
    json.put("BlockChain length", blockchain.getIndex());
    json.put("previeus hash",     block.getPrevHash());
    
    return json.toString();
  }
  
  @PostMapping(value = "/add-transaction", headers="Accept=application/json", consumes = "application/JSON")
  public void postAddTransaction(@RequestBody String params) throws IOException, Exception {
    String[] transactionParams = parseJsonTransaction(params);
    
    BlockChain blockchain = node.getBlockChain();
    
    int value = Integer.parseInt(transactionParams[2]);
    
    blockchain.addTransaction(transactionParams[0], transactionParams[1], value); //convert to has map?
    
    List<Transaction> pendingTransactions = blockchain.getPendingTransactions();
    Transaction transaction;
    for (int i = 1; i < pendingTransactions.size(); i++) {
    	transaction = pendingTransactions.get(i);
    	System.out.println(transaction.getReceiver());
    }
  }
  
  private String[] parseJsonMine(String json) {
    JSONObject obj = new JSONObject(json);
    String[] stringArray = new String[2];
    
    stringArray[0] = obj.getString("address");
    stringArray[1] = obj.getString("nonce");
    //stringArray[2] = Integer.toString((int) obj.get("value"));

    return stringArray;
  }
  
  private String[] parseJsonTransaction(String json) {
    JSONObject obj = new JSONObject(json);
    String[] stringArray = new String[3];
    
    stringArray[0] = obj.getString("sender");
    stringArray[1] = obj.getString("receiver");
    stringArray[2] = obj.getString("value");
    //stringArray[2] = Integer.toString((int) obj.get("value"));

    return stringArray;
  }
  
  private String sendNodePing(WebClient client) {
    String result = client.get()
     .uri("/ping")
     .retrieve()
     .bodyToMono(String.class)
     .block();

    return result;
  }
  
  /*
   * Return a JSON object (but is not working) or an POO object?
  private JSONObject buildResponse(String status, int index) { // This kind of things made me think to pass this to a service package
	    JSONObject response = new JSONObject();
	    
	    response.put("status", status);
	    response.put("blockId", Integer.toString(index));
	    
	    return response;
	  }*/
}
