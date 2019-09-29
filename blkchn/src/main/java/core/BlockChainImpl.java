package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BlockChainImpl implements BlockChain{
  List<Block> chain;
  
  public List<Block> BlockChainImpl() {
    this.chain.add(new Block(1, null, "0000", (Integer) null));
    //this.pendingTransactions = new ArrayList<Transaction>();
    return chain;
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
    return true;
  }
  
  public boolean validChain() {
    return true;
  }
}
