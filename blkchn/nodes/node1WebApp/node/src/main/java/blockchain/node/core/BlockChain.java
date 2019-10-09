package blockchain.node.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public interface BlockChain {
  Block createBlock(String createdBy, String  prevHash, int nonce);
  
  boolean validProofOfWork(int prevNonce, int nonce);
  
  boolean validChain();
  
  void addBlock(Block block);
  
  Block getLastBlock();
  
  String getDifficulty();
  
  List<Transaction> getPendingTransactions();
  
  String prevBlocktoString(Block block);
  
  Block mineBlock(String createdBy, int nonce, String workerAddress);
  
  void addTransaction(String sender, String receiver, int value);
  
  int getIndex();
  
  Block getBlock(int index);
  
  void init(String createdBy);
  
  HashMap<String, String> getInfo();
  
  List<Block> createBlocks(JSONArray blocks);
  
  void appendBlocks(List<Block> blockList);
}