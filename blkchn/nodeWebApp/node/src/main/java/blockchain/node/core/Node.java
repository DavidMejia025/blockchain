package blockchain.node.core;

import org.json.JSONObject;

public interface Node {
  BlockChain getBlockChain();
  
  JSONObject mineBlock(int nonce);
}
