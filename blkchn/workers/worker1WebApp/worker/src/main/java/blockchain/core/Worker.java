package blockchain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.utils.Hashes;
import blockchain.utils.Logs;

public class Worker  {
  List<Node> nodes = new ArrayList<Node>();
  String     difficulty = "0"; // get this from node
  String     address;
  @Autowired
  Logs logs;
  
  public Worker(String url) {
	   this.address = "no address asigned yet";
  }
  
  private void setDifficulty(String value) {
	   this.difficulty = value;
  }
  
  public void mine() {
    int nonce = proofOfWork(getPreviousNonce());
    
    String nodeResponse = sendMineRequest(nonce);

    JSONObject obj = new JSONObject(nodeResponse);
    
    boolean mined;
    mined = obj.getBoolean("result");
    
    if (mined) {
      logs.addLog("Yei, 1 Coin earned from block #Hash with transaction #T");
    }else {
      logs.addLog("Sorry the nonce failed, now calculating a new nonce again");
    }
    
    mine();
  }
  
  private Integer getPreviousNonce() {

	String response = nodes.get(0).nodeClient.getWebClient().get()
     .uri("/last-nonce")
     .retrieve()
     .bodyToMono(String.class)
     .block();
	
	HashMap<String, String> parsedResponse = parseJsonPrevNonce(response);
	 
	setDifficulty(parsedResponse.get("difficulty"));
	 
    return Integer.parseInt(parsedResponse.get("prevNonce"));
  }
  
  private int proofOfWork (int prevNonce) {
    boolean proofOfWorkResult;
    boolean currentProofOfWork = false;
    int     nonce = 0;
    
    while (currentProofOfWork == false) {
      logs.addLog("Currently minning a new block ");
      
      nonce += 1;
      
      proofOfWorkResult = validProofOfWork(prevNonce, nonce);
      
      if (proofOfWorkResult == true) currentProofOfWork = true; 
    }
    
    return nonce;
  }
  
  private boolean validProofOfWork(int prevNonce, int nonce) {
    String hash     = Hashes.calculateHash("" + prevNonce + nonce + "");
    String hashTest =  hash.substring(0, 1);
    
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (hashTest.equals(difficulty));
  }
  
  private String sendMineRequest(int nonce) {
	HashMap<String, String> params = new HashMap<>();
	
	params.put("address", this.address);
	params.put("nonce", Integer.toString(nonce));
	
    String result = nodes.get(0).nodeClient.getWebClient().post()
     .uri("/mine")
     .body(BodyInserters.fromObject(params))
     .retrieve()
     .bodyToMono(String.class)
     .block();

    return result;
  }
  
  private HashMap<String, String> parseJsonPrevNonce(String json) {
	   JSONObject obj = new JSONObject(json);
	
	   HashMap<String, String> response = new HashMap<>();
    
    response.put("difficulty", obj.getString("difficulty"));
    response.put("prevNonce" , Integer.toString((int) obj.get("nonce")));
    
    return response;
  }
  
  public List<String> listNodes(){
    List<String> nodesUrl = new ArrayList<String>();
    try { 
      int countPeers = this.nodes.size();
 
      for (int i = 0; i < countPeers; i++) {
        nodesUrl.add(this.nodes.get(i).getNodeUrl());
      }
    } catch (Exception e) {
      nodesUrl.add("No node linked was found");
    }
 
    return nodesUrl;
  }
  
  public Boolean addNode(String url) {
    Node newNode = new Node(url);
    nodes.add(newNode);
    logs.addLog("New node added with address: " + url);
    
    return true;
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String url) {
    this.address = url;
  }
}
