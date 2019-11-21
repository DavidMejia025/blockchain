package blockchain.node.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import blockchain.node.utils.Lists;
import blockchain.node.utils.Logs;

public class Node{
  @Autowired
  BlockChain chain;
  @Autowired
  Lists list;
  @Autowired
  Logs logs;
  List<Peer> peers = new ArrayList<Peer>();
  String url;

  public Node() {}
  
  public BlockChain getBlockChain() {
    return this.chain;
  }
  
  public String getUrl() {
	  return this.url;
  }
  
  public void setUrl(String url) {
	  this.url = url;
  }
  
  public Peer addPeer(String peerUrl) {
	  Peer peer = new Peer(peerUrl);
	  peers.add(peer);
	   
	  return peer; 
  }
	  
	  public List<Peer> getPeers() {
		  return peers;
	  }
	  
	  public List<String> listPeers(){
    List<String> peersUrl = new ArrayList<String>();

    try { 
      int countPeers = this.peers.size();
 
      for (int i = 0; i < countPeers; i++) {
        peersUrl.add(this.peers.get(i).getPeerUrl());
      }
    } catch (Exception e) {
      peersUrl.add("No peer found");
    }
 
    return peersUrl;
  }
  
  public Integer handShake(String peerUrl) {
	  Peer peer = new Peer(peerUrl);
	  peers.add(peer);
    logs.addLog("New peer added, peer Url: " + peerUrl);
    
    return chain.getIndex();
  }
   
  public void matchBlockChain(Peer peer, Integer peerBlockChainLength) {
    int index = chain.getIndex();
    
    if (peerBlockChainLength > index) {
      String peerResponse = peer.getBlocks(index);
      
      JSONObject obj = new JSONObject(peerResponse);
      JSONArray blocks = obj.getJSONArray("blocks");
      
      List<Block> blockList = chain.createBlocks(blocks);
      
      chain.appendBlocks(blockList);
    }else if (peerBlockChainLength > index){
       //To be implemented
      logs.addLog("send my blocks");
    }else {
      logs.addLog("we have the same bc");
    }
  }

  public HashMap<Peer, Boolean> broadcastBlock(String peerUrl, Block block) throws JsonProcessingException {
    HashMap<Peer, Boolean> result = new HashMap<Peer, Boolean>();
    Peer peer;
    
    for (int i = 0; i < peers.size(); i++) {
      peer = peers.get(i);

      if (!(peer.getPeerUrl().equals(peerUrl))) {
        String peerResponse = peer.sendBlock(this.url, block);  
        
        JSONObject obj = new JSONObject(peerResponse);
        Boolean valid  = obj.getBoolean("valid");
        
        result.put(peer, valid);
      }
    }
    
	  return result;
  }
  
  public Boolean peersValidation(HashMap<Peer, Boolean> peerValidation) {
    Boolean result = true;
    
    for(int i = 0; i < peerValidation.size(); i++) {
      if(peerValidation.values().contains(false) == true) {
        result = false;
      }
    
    return result; 
  }
   
  public JSONObject sendBlocks(Integer peerBlockChainLength){
    List<Block> blocks = new ArrayList<Block>();
   
    int blockChainIndex = chain.getIndex();

    int remainingBlocks = blockChainIndex - peerBlockChainLength;
    logs.addLog("number of blocks to be sended to the peer: " + remainingBlocks);
   
    if (remainingBlocks > 0) {
      for (int i = 0; i < remainingBlocks; i++) {
        Block newBlock = chain.getBlock(peerBlockChainLength + i);

        blocks.add(newBlock);
        blockChainIndex -= 1;
      }
    }
   
    JSONObject params = new JSONObject();
    params.put("blocks", blocks);

    return params;
  }
   
  public Boolean validateBlock(String senderUrl, Block block) throws JsonProcessingException {
    HashMap<Peer, Boolean> broadCastResult;
    Boolean                validationResult;
    Boolean                result;
    
    broadCastResult  = broadcastBlock(senderUrl, block);
    validationResult = peersValidation(broadCastResult);
      
    if(validationResult == true) {
      chain.addBlock(block);
      printNewBlock(block);
      
      result = true;
    }else {
      result = false;
    }
    
    return result;
  }
  
  private void printNewBlock(Block block) {
    logs.addBlankLine();
    logs.addLog("New block created:  ");
    logs.addLog("Previews hash: " + block.getPrevHash());
    logs.addLog("Transactions:");
    
    List<Transaction> transactionsAppended = block.getTransactions();
   
    list.printOutTransactions(transactionsAppended);
    logs.addBlankLine();
  }
}