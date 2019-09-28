package core;

import java.util.ArrayList;
import java.util.List;

public class BlockChainImpl implements BlockChain{
  List<Block>       chain;
  List<Transaction> transactions;
  
  public List<Block> BlockChainImpl() {
    this.chain.add(new Block("1", null));
    this.pendingTransactions = new ArrayList<Transaction>();
    return chain;
  }
  
  public Block getLastBlock() {
    int   lenght = chain.size();
    Block block;

    block = chain.get(lenght - 1);
          
    return block;
  }
  
  public String calculateHash() {
    return "not implemented jet";
  }
}
