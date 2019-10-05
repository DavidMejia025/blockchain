package blockchain.node.core;

import java.util.List;
import java.util.Map;

public interface BlockChain {
  Block createBlock(String  prevHash, int nonce);
  
  boolean validProofOfWork(int prevNonce, int nonce);
  
  boolean validChain();
  
  void addBlock(Block block);
  
  Block getLastBlock();
  
  String getDifficulty();
  
  List<Transaction> getPendingTransactions();
  
  String prevBlocktoString(Block block);
  
  Map<String, Boolean> mineBlock(int nonce, String workerAddress);
  
  void addTransaction(String sender, String receiver, int value);
  
  int getIndex();
  
}