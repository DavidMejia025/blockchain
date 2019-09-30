package core;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

import utils.Hashes;

public class BlockChainImpl implements BlockChain{
  List<Block> chain = new ArrayList<Block>();
  String difficulty = "0";
  
  public BlockChainImpl() {
    System.out.println("Creating a bc with the root block");
    Transaction firstTransaction = new Transaction("0", "1", 1);
    List<Transaction> transactions = new ArrayList<Transaction>();
    
    Block root = new Block(1, transactions, "1", 103);

    this.chain.add(root); 
  }
  
  public void addBlock(Block newBlock) {
    this.chain.add(newBlock);
  }
  
  public Block getLastBlock() {
    int   lenght = chain.size();

    Block block = chain.get(lenght - 1);
          
    return block;
  }
  public int getIndex() {
    return chain.size();
  }
  
//I have doubts on the place for the PoW, should it belongs to the chain or the worker?
  public boolean validProofOfWork(int prevNonce, int nonce) {
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
  
  public boolean validChain() {
    return true;
  }
}
