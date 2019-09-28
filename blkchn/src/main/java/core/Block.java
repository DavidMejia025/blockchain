package core;
import java.util.Date;
import java.util.List;
public class Block {
  private String             prevHash;
  private String             proove;
  private int                index;
  private Date               timeStamp;
  private List<Transaction>  transactions; 
  //autowired
  private List<Block>        blockChain;
  
  public Block Block(String prevHash, String proove) {
    this.index        = blockChain.size();
    this.transactions = blockChain.pendingTransactions(); // this is just one the transaction that will be associate to the block? queue? or the Transactions list
    this.timeStamp    = new Date();
    this.prevHash     = prevHash;
    this.proove       = proove;
    
    return this;
    //  noonce wtf is noonce 
  }
  
  public String getPrevHash() {
    return this.prevHash;
  }
}
