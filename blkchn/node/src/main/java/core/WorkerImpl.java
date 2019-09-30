package core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import utils.Hashes;

public class WorkerImpl implements Worker {
  private String            url;
  private Block             block;
  private List<Transaction> pendingTransactions = new ArrayList<Transaction>();
  private List<Block>       blocksEarned;
  public  BlockChainImpl    chain;

  public WorkerImpl(String url) {
    this.url = url;
  }

  public void createBlockChain() {
    this.chain = new BlockChainImpl();
  }
  
  public Block createBlock(String  prevHash, int nonce) {
    int index = this.chain.getIndex();
    // here I can implement a pattern for add this list of parameters config or something
    Block newBlock =  new Block(
        index,
        this.pendingTransactions,
        prevHash, // revious_hash or self.hash(self.chain[-1]),
        nonce
      );
    
    this.pendingTransactions = new ArrayList<Transaction>();
    
    return newBlock;  // append new block when created? or after mined? or after approval ?
  }
  
  public void addWorker(WorkerImpl newWorker) {
    newWorker.getBlockChain(this.chain);
  }
  
  public void getBlockChain(BlockChainImpl blockChain) {
    this.chain = blockChain;
  }
  
  public void addTransaction(Transaction newTransaction) {
    this.pendingTransactions.add(newTransaction);
  //return self.last_block['index'] + 1 transaction will be added to next block (the one to be mined) index = this return
  }
  
  //mine block is to test poW until get the winner nonce.
  public void mineBlock() {
    Block prevBlock = this.chain.getLastBlock();
    
    int nonce = proofOfWork(prevBlock);
    
    String prevBlockStringfy = prevBlocktoString(prevBlock);
    
    String prevHash = Hashes.calculateHash(prevBlockStringfy);
    
    Block newBlock = createBlock(prevHash, nonce);
    
    this.chain.addBlock(newBlock);
  }
  
  public int proofOfWork (Block prevBlock) {
    boolean proofOfWorkResult;
    boolean currentProofOfWork = false;
    int     nonce = 0;
    int     prevNonce = prevBlock.getNonce();
    
    while (currentProofOfWork == false) {
      System.out.println("Currently minning a new block ");
      
      nonce += 1;
      
      proofOfWorkResult = this.chain.validProofOfWork(prevNonce, nonce);
      
      if (proofOfWorkResult == true) currentProofOfWork = true; 
      //# We must receive a reward for finding the proof.
      //# The sender is "0" to signify that this node has mined a new coin.
    }
    
    return nonce;
  }
  
  public BlockChainImpl getBlockChain() {
    return this.chain;
  }
  
  private String prevBlocktoString(Block block) {
    String blockString = "" +
        block.getIndex() +
        block.getTransactions() +
        block.getTimestamp() +
        block.getHash() +
        block.getNonce() +
        "";
    
    return blockString;
  }
}
