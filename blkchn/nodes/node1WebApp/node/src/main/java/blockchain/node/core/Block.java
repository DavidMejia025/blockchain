package blockchain.node.core;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import blockchain.node.utils.Lists;

public class Block { 
  @Autowired
  Lists list;
  private String             createdBy;         
  private String             prevHash;
  private int                nonce;
  private int                index;
  private Timestamp          timestamp;
  private List<Transaction>  transactions; 
  
  public Block(int index, String createdBy, List<Transaction> transactions, Timestamp timestamp, String  prevHash, int nonce) {
    this.index        = index;
    this.createdBy    = createdBy;
    this.transactions = transactions;
    this.timestamp    = timestamp;
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
  
  public String getCreatedBy() {
    return this.createdBy;
  }
  
  public int getNonce() {
    return this.nonce;
  }
}
