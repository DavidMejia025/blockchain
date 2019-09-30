package core;

import java.util.List;

public interface Worker{
  Block createBlock(String  prevHash, int nonce);
  
  void createBlockChain();
  
  //Transaction createTransaction();
  
  void addWorker(WorkerImpl newWorker);
  
  void mineBlock();
  
  // this would be validProofOfWork
  //boolean testProove ();
}
