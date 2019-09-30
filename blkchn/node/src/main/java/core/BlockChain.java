package core;

import java.util.List;

public interface BlockChain {
  boolean validProofOfWork(int prevNonce, int nonce);
  
  boolean validChain();
}

