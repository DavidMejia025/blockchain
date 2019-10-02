package blockchain.node.core;

import java.sql.Timestamp;
import java.util.List;

public class Block {
  private String             prevHash;
  private int                nonce;
  private int                index;
  private Timestamp          timestamp;
  private List<Transaction>  transactions; 
  
  public Block(int index, List<Transaction> transactions, String  prevHash, int nonce) {
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
