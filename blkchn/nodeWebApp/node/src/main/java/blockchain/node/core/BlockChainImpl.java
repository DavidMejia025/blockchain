package blockchain.node.core;

import java.util.ArrayList;
import java.util.List;

import blockchain.node.utils.Hashes;

public class BlockChainImpl implements BlockChain{
  public  List<Block> chain = new ArrayList<Block>();
  private List<Transaction> pendingTransactions = new ArrayList<Transaction>();
  String difficulty = "0";
  
  public BlockChainImpl() {
    System.out.println("Creating a bc with the root block");
    
    String prevHash = Hashes.calculateHash("0");
    
    Transaction firstTransaction = new Transaction("0", "1", 1); // who owns this Coin?
    
    pendingTransactions.add(firstTransaction);
    
    Block root = createBlock(prevHash, 100);

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

    Block block = chain.get(lenght - 1);
          
    return block;
  }
  
  public List<Transaction> getPendingTransactions() {
    return this.pendingTransactions;
  }
  
  public int getIndex() {
    return chain.size();
  }
  
  public String getDifficulty() {
    return this.difficulty;
  }
  
  public void addTransaction(Transaction newTransaction) {
    this.pendingTransactions.add(newTransaction);
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
}
