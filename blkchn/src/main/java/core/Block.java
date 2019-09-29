package core;
import java.sql.Timestamp;
import java.util.List;

import utils.Hashes;

public class Block {
  private String             prevHash;
  private int                nonce;
  private int                index;
  private Timestamp          timestamp;
  private List<Transaction>  transactions; 
  //autowired
  private List<Block>        blockChain;
  
  public Block(int index, List<Transaction> transactionsp, String  prevHash, int nonce) {
    this.index        = index;
    this.transactions = transactions; // this is just one the transaction that will be associate to the block? queue? or the Transactions list
    this.timestamp    = new Timestamp(System.currentTimeMillis());
    this.prevHash     = prevHash;
    this.nonce        = nonce;
  }
  
  public String getPrevHash() {
    return this.prevHash;
  }
  
  public int getNonce() {
    return this.nonce;
  }
}
