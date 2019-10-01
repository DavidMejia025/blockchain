package blockchain.core;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import blockchain.utils.Hashes;


public class WorkerImpl implements Worker {
  String url;
  String nodeUrl = null;
  String difficulty = "0"; // get this from node
  
  public WorkerImpl(String url) {
    this.url = url;
  }
  
  public void mine() {
    Integer prevNonce = getPreviousNonce();
    
    int nonce = proofOfWork(prevNonce);
    
    //send post request to Node with the corresponding nonce in order to mine end point
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
  
  // this kind of methods should return string or boolean with the confirmation of the operation?
  public void findNode() {
  //ping random ports to get one node     
    this.nodeUrl = "3000";
  }
  
  private Integer getPreviousNonce() {
  // if(this.nodeUrl == null) findNode;
  // make request to the corresponding node    
    int prevNonce = 10;
    
    return prevNonce;
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
