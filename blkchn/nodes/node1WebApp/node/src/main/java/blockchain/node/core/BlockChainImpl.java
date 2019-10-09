package blockchain.node.core;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import blockchain.node.utils.Hashes;
import blockchain.node.utils.Lists;
import blockchain.node.utils.Logs;

public class BlockChainImpl implements BlockChain{
  @Autowired
  Logs logs;
  @Autowired
  Lists list;
  
  public  List<Block> chain = new ArrayList<Block>();
  private List<Transaction> pendingTransactions = new ArrayList<Transaction>();
  private final int rootNonce = 100;
  String difficulty = "0";
  
  public BlockChainImpl() {
  }
  
  public void init(String createdBy) {
    Block root = createBlock(createdBy, null, rootNonce);
    logs.addLog("Creating a bc with the root block" + root);
    logs.addLog("Creating a bc with the root block get" + root.getCreatedBy());
    this.chain.add(root); 
  }
  
  public Block createBlock(String createdBy, String  prevHash, int nonce) {
    int index = this.chain.size() + 1;

    Block newBlock =  new Block(
        index,
        createdBy,
        this.pendingTransactions,
        new Timestamp(System.currentTimeMillis()),
        prevHash,
        nonce
      );
    
    this.pendingTransactions = new ArrayList<Transaction>();
    
    return newBlock;
  }
  
  public List<Block> createBlocks(JSONArray blocks){
    List<Block> newBlocks = new ArrayList<Block>();
    JSONObject block;
    JSONArray transactions;
    int       index;
    int       nonce;
    Timestamp timestampParsed;
    String    prevHash;
    String    createdBy;
    
    for (int i = 0; i < blocks.length(); i++) {
      block = blocks.getJSONObject(i);

      transactions = block.getJSONArray("transactions");
      index        = block.getInt("index");
      createdBy    = block.getString("createdBy");
      nonce        = block.getInt("nonce");
      try {
        String timestamp  = block.getString("timestamp");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = dateFormat.parse(timestamp);
        timestampParsed = new java.sql.Timestamp(parsedDate.getTime());
      } catch(Exception e) { 
        int timestamp = block.getInt("timestamp");
        Date newDate = new java.util.Date(timestamp);
        timestampParsed = new java.sql.Timestamp(newDate.getTime()); 
      }
      try {
        prevHash     = block.getString("prevHash");
      } catch(Exception e) { 
        prevHash = (String) null;
      }
      
      Block newBlock =  new Block(
          index,
          createdBy,
          this.pendingTransactions,
          timestampParsed,
          prevHash,
          nonce
        );
      
      newBlocks.add(newBlock);
    }
    
    return newBlocks;
  }
  
  public void appendBlocks(List<Block> blockList) {
    for (int i = 0; i < blockList.size(); i++) {
      this.chain.add(blockList.get(i));
    }
  }
  
  public void addBlock(Block newBlock) {
    this.chain.add(newBlock);
  }
  
  public Block getBlock(int index) {
    Block block  = chain.get(index);
          
    return block;
  }
  
  public Block getLastBlock() {
    int   lenght = chain.size();
    Block block  = chain.get(lenght - 1);
          
    return block;
  }
  
  public List<Transaction> getPendingTransactions() {
    return this.pendingTransactions;
  }
  
  public int getIndex() {
    return this.chain.size();
  }
  
  public String getDifficulty() {
    return this.difficulty;
  }
  
  public HashMap<String, String> getInfo() {
	  HashMap<String, String> obj = new HashMap<>();;
	  
	  int index = getIndex();
	  logs.addLog("BlockChain length: " + index);
	  
	  if (index > 0) {	
		    Block block = getLastBlock();
		    List<Transaction> pendingTransactions = getPendingTransactions();
		    
		    logs.addLog("Previews hash: " + block.getPrevHash());
		    list.printOutTransactions(pendingTransactions);
		    
		    obj.put("BlockChain length",    Integer.toString(index));
		    obj.put("previeus hash",        block.getPrevHash());
		    obj.put("pending transactions", list.transactionsToString(pendingTransactions));
		}else {
			obj.put("BlockChain length",       (String) null);
		    obj.put("previeus hash",        (String) null);
		    obj.put("pending transactions", (String) null);
		}
	  
	  return obj;
  }
  public void addTransaction(String sender, String receiver, int value) {
	Transaction newTransaction = new Transaction(sender, receiver, value);
	
    this.pendingTransactions.add(newTransaction);
  }
  
  public Block mineBlock(String createdBy, int nonce, String workerAddress) {
	   Block response;
	
    Block prevBlock = getLastBlock();
    
    boolean proveOfWorkResult = validProofOfWork(prevBlock.getNonce(), nonce);
    
    if (proveOfWorkResult) {
      String prevHash = prevBlocktoString(prevBlock);
      prevHash = Hashes.calculateHash(prevHash);
      
      addTransaction("!@#$000000 !!on the house!! 00000!@#", workerAddress, 25);
      addTransaction("!@#$000000 !!on the house!! 00000!@#", workerAddress, 25);
      
      Block newBlock = createBlock(createdBy, prevHash, nonce);
      
      logs.addBlankLine();
      logs.addLog("New block created:  ");
      logs.addLog("Previews hash: " + newBlock.getPrevHash());
      logs.addLog("Transactions:");
      
      List<Transaction> transactionsAppended = newBlock.getTransactions();
     
      list.printOutTransactions(transactionsAppended);
      logs.addBlankLine();
      
      response = newBlock;
    }else {
      response = (Block) null;
    }
    
    return response;
  }
  
  public boolean validProofOfWork(int prevNonce, int nonce) {
    String hash     = Hashes.calculateHash("" + prevNonce + nonce + "");
    String hashTest =  hash.substring(0, 1);
    
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    return (hashTest.equals(difficulty));
  }
  
  public String prevBlocktoString(Block block) {
    String blockString = "" +
        block.getIndex() +
        list.transactionsToString(block.getTransactions()) +
        block.getTimestamp() +
        block.getHash() +
        block.getNonce() +
        "";
    
    return blockString;
  }
  
  public boolean validChain() {
    return true;
  }
  
  public Map<String, Boolean> buildResponse(boolean status) { 
	HashMap<String, Boolean> map = new HashMap<>();
  
    map.put("result", status);
  
    return map;
  }
}
