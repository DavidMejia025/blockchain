package blockchain.node.core;

public interface BlockChain {
  Block createBlock(String  prevHash, int nonce);
  
  boolean validProofOfWork(int prevNonce, int nonce);
  
  boolean validChain();
  
  Block getLastBlock();
  
  String getDifficulty();
  
  String prevBlocktoString(Block block);
  
  void addTransaction(Transaction newTransaction);
}