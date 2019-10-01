package blockchain.node.core;

import java.util.List;

public interface BlockChain {
  Block createBlock(String  prevHash, int nonce);
  
  boolean validProofOfWork(int prevNonce, int nonce);
  
  boolean validChain();
  
  void addBlock(Block block);
  
  Block getLastBlock();
  
  String getDifficulty();
  
  List<Transaction> getPendingTransactions();
  
  String prevBlocktoString(Block block);
  
  void addTransaction(Transaction newTransaction);
}