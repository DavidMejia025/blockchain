package blockchain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.utils.Hashes;
import reactor.util.MultiValueMap;


public class Worker  {
  List<String> nodeUrls   = new ArrayList<String>();
  String       difficulty = "0"; // get this from node
  @Autowired
  WebClient client;
  
  public Worker(String url) {
    this.nodeUrls.add(url); //need to be a loop on list if nodeUrls > 0
  }
  
  private void setDifficulty(String value) {
	  this.difficulty = value;
  }
  
  public void mine() {
    Integer prevNonce = getPreviousNonce();
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(prevNonce);
    int nonce = proofOfWork(prevNonce);
    
    String nodeResponse = sendMineRequest(nonce);
    
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(nodeResponse);
    String result;
    if (true) {
      result = "Earn 1 Coin from block #Hash transaction #T";
    }else {
      result = "nonce failed, calculating new nonce again";
    }
    
    System.out.println(nonce);
    System.out.println(result);
    
    mine();
  }
  
  private Integer getPreviousNonce() {
	String response = client.get()
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
      System.out.println("Currently minning a new block ");
      
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
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (hashTest.equals(difficulty));
  }
  
  private String sendMineRequest(int nonce) {
	//LinkedMultiValueMap<String, Integer> params = new LinkedMultiValueMap();
	    
    //params.add("nonce", nonce);
	
    //JSONObject params = new JSONObject();
    
    //params.put("nonce", nonce);
    //BodyInserters.FormInserter<Object> bodyInserter = BodyInserters.fromObject(params);
	HashMap<String, Integer> params = new HashMap<>();
	      
	params.put("nonce", nonce);
	
    System.out.println("  Send request mine");
    System.out.println(params);
    String result = client.post()
		     .uri("/mine")
		     .body(BodyInserters.fromObject(params))
		     .retrieve()
		     .bodyToMono(String.class)
		     .block();
    //JSONObject obj = new JSONObject(result);
    return result;
  }
		
  
  private HashMap<String, String> parseJsonPrevNonce(String json) {
	JSONObject obj = new JSONObject(json);
	
	HashMap<String, String> response = new HashMap<>();
    
    response.put("difficulty", obj.getString("difficulty"));
    response.put("prevNonce" , Integer.toString((int) obj.get("nonce")));
    
    return response;
  }
}
