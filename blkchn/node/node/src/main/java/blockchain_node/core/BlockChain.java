package blockchain_node.core;

import blockchain_node.core.Block;

public interface BlockChain {
  Block createBlock(String  prevHash, int nonce);
  
  boolean validProofOfWork(int prevNonce, int nonce);
  
  boolean validChain();
}

