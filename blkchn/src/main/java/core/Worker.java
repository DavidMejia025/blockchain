package core;

import java.util.List;

public interface Worker{
  Block createBlock(String  prevHash, int nonce);
  
  BlockChain createBlockChain();
  
  //Transaction createTransaction();
  
  void addWorker();
  
  void mineBlock();
  
  // this would be validProofOfWork
  //boolean testProove ();
}
