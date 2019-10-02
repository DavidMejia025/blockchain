package blockchain.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.utils.Hashes;


public class Worker  {
  List<String> nodeUrls   = new ArrayList<String>();
  String       difficulty = "0"; // get this from node
  @Autowired
  WebClient client;
  
  public Worker(String url) {
    this.nodeUrls.add(url); //need to be a loop on list if nodeUrls > 0
  }
  
  public void mine() {
    Integer prevNonce = getPreviousNonce();
    
    int nonce = proofOfWork(prevNonce);
    
    /*String result = client.get()
		     .uri("/last-nonce")
		     .retrieve()
		     .bodyToMono(String.class)
		     .block();
    */
    
    String result;
    
    Boolean mined = true;
    
    if (mined == true) {
      result = "Earn 1 Coin from block #Hash transaction #T";
    }else {
      result = "nonce failed, calculating new nonce again";
    }
    
    System.out.println(nonce);
    System.out.println(result);
    
    mine();
  }
  
  private Integer getPreviousNonce() {
	 String prevNonce = client.get()
			     .uri("/last-nonce")
			     .retrieve()
			     .bodyToMono(String.class)
			     .block();

    return Integer.parseInt(prevNonce);
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
}
