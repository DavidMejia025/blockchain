package blockchain.node.core;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeImpl implements Node{
  private String  url;
  @Autowired
  public  BlockChain    chain;

  public NodeImpl(String url) {
    this.url = url;
  }
  
  public String getUrl() {
    return this.url;
  }
  public void addTransaction(Transaction newTransaction) {
    this.chain.addTransaction(newTransaction);
  //return self.last_block['index'] + 1 transaction will be added to next block (the one to be mined) index = this return
  }
  
  public Transaction createTransaction(String sender, String receiver, int value) {
    Transaction newTransaction = new Transaction(sender, receiver, value);
    
    return newTransaction;
  }
  
  //mine block is to test poW until get the winner nonce.
  public JSONObject mineBlock(int nonce) {
    JSONObject response;
    
    BlockChain blockchain = getBlockChain();
    Block      prevBlock  = blockchain.getLastBlock();
    
    boolean proveOfWorkResult = blockchain.validProofOfWork(prevBlock.getNonce(), nonce);
    
    proveOfWorkResult = true;
    
    if (proveOfWorkResult == false) {
// createBlock
      String prevHash = blockchain.prevBlocktoString(prevBlock); //this should not be a property of the Node?
      
      Block newBlock = blockchain.createBlock(prevHash, nonce);
      
      blockchain.addBlock(newBlock); // this can be inside create Block? but it seems to me more understandable here what do you think?
// but more reasons to move to a service?
      response = buildResponse("created", newBlock.getIndex());
    }else {
      response = buildResponse("failed", -1);
    }
    
    System.out.println(nonce);
    System.out.println(proveOfWorkResult);
    System.out.println(response);
    
    return response;
  }
  
  public int proofOfWork (Block prevBlock) {
    boolean proofOfWorkResult;
    boolean currentProofOfWork = false;
    int     nonce = 0;
    int     prevNonce = prevBlock.getNonce();
    
    while (currentProofOfWork == false) {
      System.out.println("Currently minning a new block ");
      
      nonce += 1;
      
      proofOfWorkResult = this.chain.validProofOfWork(prevNonce, nonce);
      
      if (proofOfWorkResult == true) currentProofOfWork = true; 
      //# We must receive a reward for finding the proof.
      //# The sender is "0" to signify that this node has mined a new coin.
    }
    
    return nonce;
  }
  
  public BlockChain getBlockChain() {
    return this.chain;
  }
  
  private JSONObject buildResponse(String status, int index) { // This kind of things made me think to pass this to a service package
    JSONObject response = new JSONObject();
    
    response.put("status", status);
    response.put("blockId", Integer.toString(index));
    
    return response;
  }
}
