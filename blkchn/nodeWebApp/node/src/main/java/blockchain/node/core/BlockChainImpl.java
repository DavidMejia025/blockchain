package blockchain.node.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blockchain.node.utils.Hashes;

public class BlockChainImpl implements BlockChain{
  public  List<Block> chain = new ArrayList<Block>();
  private List<Transaction> pendingTransactions = new ArrayList<Transaction>();
  private final int rootNonce = 100;
  String difficulty = "0";
  
  public BlockChainImpl() {
    System.out.println("Creating a bc with the root block");
    
    String prevHash = Hashes.calculateHash("0");
    
    //Transaction firstTransaction = new Transaction("0", "1", 1); // who owns this Coin?  On hold for the moment
    //pendingTransactions.add(firstTransaction);
    
    Block root = createBlock(prevHash, rootNonce);

    this.chain.add(root); 
  }
  
  public Block createBlock(String  prevHash, int nonce) {
    int index = this.chain.size() + 1;

    Block newBlock =  new Block(
        index,
        this.pendingTransactions,
        prevHash,
        nonce
      );
    
    this.pendingTransactions = new ArrayList<Transaction>();
    
    return newBlock;
  }
  
  public void addBlock(Block newBlock) {
    this.chain.add(newBlock);
  }
  
  public Block getLastBlock() {
    int   lenght = chain.size();
    Block block  = chain.get(lenght - 1);
          
    return block;
  }
  
  public List<Transaction> getPendingTransactions() {
    return this.pendingTransactions;
  }
  
  public int getIndex() {
    return this.chain.size();
  }
  
  public String getDifficulty() {
    return this.difficulty;
  }
  
  public void addTransaction(String sender, String receiver, int value) {
	Transaction newTransaction = new Transaction(sender, receiver, value);
	
    this.pendingTransactions.add(newTransaction);
  }
  
  public Map<String, Boolean> mineBlock(int nonce) {
	Map<String, Boolean> response;
	
    Block prevBlock = getLastBlock();
    
    boolean proveOfWorkResult = validProofOfWork(prevBlock.getNonce(), nonce);
    
    if (proveOfWorkResult) {
      String prevHash = prevBlocktoString(prevBlock); //this should not be a property of the Node?
      Block  newBlock = createBlock(prevHash, nonce);
      
      addBlock(newBlock);
      
      response = buildResponse(true);
    }else {
      response = buildResponse(false);
    }
    
    return response;
  }
  
  public boolean validProofOfWork(int prevNonce, int nonce) {
    String hash     = Hashes.calculateHash("" + prevNonce + nonce + "");
    String hashTest =  hash.substring(0, 1);
    
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    return (hashTest.equals(difficulty));
  }
  
  public String prevBlocktoString(Block block) {
    String blockString = "" +
        block.getIndex() +
        block.getTransactions() +
        block.getTimestamp() +
        block.getHash() +
        block.getNonce() +
        "";
    
    return blockString;
  }
  
  public boolean validChain() {
    return true;
  }
  
  public Map<String, Boolean> buildResponse(boolean status) { 
	HashMap<String, Boolean> map = new HashMap<>();
  
    map.put("result", status);
  
    return map;
  }
}
