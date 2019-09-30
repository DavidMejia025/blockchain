package blockchain_node.core;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class Node {
  private String            url;
  private Block             block;
  private List<Block>       blocksEarned;
  public  BlockChainImpl    chain;

  public Node(String url) {
    this.url = url;
  }

  public void createBlockChain() {
    this.chain = new BlockChainImpl();
  }
  
  public void getBlockChain(BlockChainImpl blockChain) {
    this.chain = blockChain;
  }
  
  public void addTransaction(Transaction newTransaction) {
    this.chain.addTransaction(newTransaction);
  //return self.last_block['index'] + 1 transaction will be added to next block (the one to be mined) index = this return
  }
  
  public Transaction createTransaction(String sender, String receiver, int value) {
    Transaction newTransaction = new Transaction(sender, receiver, value);
    
    return newTransaction;
  }
  
  //mine block is to test poW until get the winner nonce.
  public void mineBlock() {
   /* Block prevBlock = this.chain.getLastBlock();
    
    int nonce = proofOfWork(prevBlock);
    
    String prevBlockStringfy = prevBlocktoString(prevBlock);
    
    String prevHash = Hashes.calculateHash(prevBlockStringfy);
    
    this.chain.addBlock(newBlock);*/
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
