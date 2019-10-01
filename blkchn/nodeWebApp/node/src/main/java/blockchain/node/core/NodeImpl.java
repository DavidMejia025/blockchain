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
  public void addTransaction(String sender, String receiver, int value) {
    Transaction newTransaction = createTransaction(sender, receiver, value);
    
    this.chain.addTransaction(newTransaction);
  }
  
  public Transaction createTransaction(String sender, String receiver, int value) {
    Transaction newTransaction = new Transaction(sender, receiver, value);
    
    return newTransaction;
  }
  
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
