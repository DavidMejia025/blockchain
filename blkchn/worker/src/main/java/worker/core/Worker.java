package blockchain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import blockchain.utils.Hashes;
import blockchain.utils.Logs;

public class Worker  {
  List<Node> nodes      = new ArrayList<Node>();
  Node       defaultNode;
  String     difficulty;
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

    JSONObject obj   = new JSONObject(nodeResponse);
    boolean    mined = obj.getBoolean("result");
    
    if (mined) {
      logs.addLog("Yei, 25 Coins earned from block #Hash with transaction #T");
    }else {
      logs.addLog("Sorry the nonce failed, now calculating a new nonce again");
    }
    
    mine();
  }
  
  private Integer getPreviousNonce() {
   	String response = defaultNode.httpClient.sendGet("/last-nonce");
   	
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
    String hashTest = hash.substring(0, 1);
    
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return (hashTest.equals(difficulty));
  }     
  
  private String sendMineRequest(int nonce) {
   	JSONObject params = new JSONObject();
   	params.put("address", this.address);
   	params.put("nonce", Integer.toString(nonce));
   	String stringParams = params.toString();
   	
    String result = defaultNode.httpClient.sendPost("/mine", stringParams);

    return result;
  }
  
  private HashMap<String, String> parseJsonPrevNonce(String json) {
	   JSONObject obj = new JSONObject(json);
	
	   HashMap<String, String> response = new HashMap<>();
    
    response.put("difficulty", obj.getString("difficulty"));
    response.put("prevNonce" , Integer.toString((int) obj.get("nonce")));
    
    return response;
  }
  
  public Boolean addNode(String url) {
    Node newNode = new Node(url);
    nodes.add(newNode);
    newNode.setNodeLinked(true);
    
    setDefaultNode(newNode);
    
    return true;
  }
  
  private void setDefaultNode(Node node) {
    this.defaultNode = node;
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
  
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String url) {
    this.address = url;
  }
}
