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
    this.transactions = transactions;
    this.timestamp    = new Timestamp(System.currentTimeMillis());
    this.prevHash     = prevHash;
    this.nonce        = nonce;
  }
  
  public String getPrevHash() {
    return this.prevHash;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public List<Transaction>  getTransactions() {
    // will be enough with the transactions signature? or require the exact values?
    return this.transactions;
  }
  
  public Timestamp getTimestamp() {
    return this.timestamp;
  }
  
  public String getHash() {
    return this.prevHash;
  }
  
  public int getNonce() {
    return this.nonce;
  }
}
