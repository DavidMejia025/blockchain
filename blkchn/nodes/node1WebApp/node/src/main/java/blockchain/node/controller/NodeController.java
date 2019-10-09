package blockchain.node.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import blockchain.node.core.Block;
import blockchain.node.core.BlockChain;
import blockchain.node.core.Node;
import blockchain.node.core.Peer;
import blockchain.node.core.Transaction;
import blockchain.node.utils.Lists;
import blockchain.node.utils.Logs;
import blockchain.node.utils.MyJson;

@RestController
public class NodeController {
  @Autowired
  Node node;
  @Autowired
  Lists list;
  @Autowired
  Logs logs;
  
  @PostMapping(value = "/set-url", headers="Accept=application/json", consumes = "application/JSON")
  public void setUrl(@RequestBody String params) {
	   node.setUrl(MyJson.getJsonString(params, "url"));
	   
	   logs.addLog("The current Url is: " + node.getUrl());
  }
  
  @GetMapping("/init-blockchain")
  public String nodeInit(String[] args) {
	   BlockChain blockchain = node.getBlockChain();
	   blockchain.init(node.getUrl());
	
    Block root = blockchain.getLastBlock();
    
    return "Blockchain with root node that has previous hash eq = " + root.getPrevHash() + " was created succesfully";
  }
  
  @GetMapping("/list-peers")
  public List<String> listPeers(String[] args) {
	   return node.listPeers();
  }
  
  @PostMapping(value = "/new-peer", headers="Accept=application/json", consumes = "application/JSON")
  public void newPeer(@RequestBody String params) {
    String peerUrl = MyJson.getJsonString(params, "url");  

	   Peer peer = node.addPeer(peerUrl);
	
	   String response          = peer.handShake(node.getUrl());
	   int peerBlockChainLength = MyJson.getJsonInt(response, "blockChainLength");
	   logs.addLog("The peer Blockchain length is: " + peerBlockChainLength);
	   
	   node.matchBlockChain(peer, peerBlockChainLength);
  }
  
  @PostMapping(value = "/hand-shake", headers="Accept=application/json", consumes = "application/JSON")
  public String handShake(@RequestBody String params) {
	   String peerUrl = MyJson.getJsonString(params, "url");
	   
	   int blockChainLength = node.handShake(peerUrl);

	   JSONObject response = new JSONObject();
	   response.put("blockChainLength", blockChainLength);
	   
	   return  response.toString();
  }
  
  @PostMapping(value = "/get-blocks", headers="Accept=application/json", consumes = "application/JSON", produces = "application/json")
  public String getBlocks(@RequestBody String params) {
	   int peerBlockChainLength = MyJson.getJsonInt(params, "blockChainLength");
	   
	   JSONObject jsonBlocks = node.sendBlocks(peerBlockChainLength);
	   
	   return jsonBlocks.toString();
  }
  
  @PostMapping(value = "/mine", headers="Accept=application/json", consumes = "application/JSON")
  public String postMine(@RequestBody String params) throws IOException, Exception {
    String[] mineParams = parseJsonMine(params);    
    logs.addLog("Receiving a new nonce: " + mineParams[1] + ", from: " + mineParams[0]);
    
    BlockChain blockchain = node.getBlockChain();
    
    Block mined = blockchain.mineBlock(node.getUrl(), Integer.parseInt(mineParams[1]), mineParams[0]);
    
    Boolean minedResult = false;
    HashMap<Peer, Boolean> broadCastResult;
    if (mined != null) {
      if (node.getPeers().size() > 0) {
        minedResult = node.validateBlock("send to all", mined);
      }else {
        blockchain.addBlock(mined);
        
        minedResult = true;
      }
    }
    
    JSONObject response = new JSONObject();
    response.put("result", minedResult);
    return response.toString();
  }
  
  @PostMapping(value = "/valid-block", headers="Accept=application/json", consumes = "application/JSON")
  public String postValidBlock(@RequestBody String params) throws IOException, Exception {
    JSONObject obj = new JSONObject(params);
    String     senderUrl = obj.getString("peerUrl");
    JSONObject jsonBlock = new JSONObject(obj.getString("block"));
    
    BlockChain blockchain = node.getBlockChain();
    Block      block      = blockchain.getLastBlock();
    
    Boolean nonceIsValid = blockchain.validProofOfWork(block.getNonce(), jsonBlock.getInt("nonce"));
    
    Boolean nonceResult = false;
    if (nonceIsValid == true) {
      JSONArray blocks = new JSONArray();
      blocks.put(jsonBlock);
      
      List<Block> newBlock = blockchain.createBlocks(blocks);
      Block       mined    = newBlock.get(0);
      
      nonceResult = node.validateBlock(senderUrl, mined);
    }
    
    JSONObject response = new JSONObject();
    response.put("valid", nonceResult);
    
    return response.toString();
  }
  
  @GetMapping("/last-nonce")
  public String getLastNounce(String[] args) {
	   BlockChain blockchain = node.getBlockChain();
    Block      block      = blockchain.getLastBlock();
    
    JSONObject json = new JSONObject();
    json.put("difficulty", blockchain.getDifficulty());
    json.put("nonce",      block.getNonce());
    
    return json.toString();
  }
  
  @GetMapping("/blockchain-info")
  public HashMap<String, String> getBlockChainInfo(String[] args) {
	   BlockChain blockchain = node.getBlockChain();
	   
	   HashMap<String, String> info = blockchain.getInfo();
	
    return info;
  }
  
  @PostMapping(value = "/add-transaction", headers="Accept=application/json", consumes = "application/JSON")
  public void postAddTransaction(@RequestBody String params) throws IOException, Exception {
    String[] transactionParams = parseJsonTransaction(params);
    
    BlockChain blockchain = node.getBlockChain();
    
    int value = Integer.parseInt(transactionParams[2]);
    
    blockchain.addTransaction(transactionParams[0], transactionParams[1], value); //convert to has map?
    
    List<Transaction> pendingTransactions = blockchain.getPendingTransactions();
    
    Transaction transaction;
    for (int i = 1; i < pendingTransactions.size(); i++) {
    	transaction = pendingTransactions.get(i);
    	System.out.println(transaction.getReceiver());
    }
  }
  
  private String[] parseJsonMine(String json) {
    JSONObject obj = new JSONObject(json);
    String[] stringArray = new String[2];
    
    stringArray[0] = obj.getString("address");
    stringArray[1] = obj.getString("nonce");
    //stringArray[2] = Integer.toString((int) obj.get("value"));

    return stringArray;
  }
  
  private String[] parseJsonTransaction(String json) {
    JSONObject obj = new JSONObject(json);
    String[] stringArray = new String[3];
    
    stringArray[0] = obj.getString("sender");
    stringArray[1] = obj.getString("receiver");
    stringArray[2] = obj.getString("value");
    //stringArray[2] = Integer.toString((int) obj.get("value"));

    return stringArray;
  }
  
  /*
curl -i -H "Content-Type: application/json" -d "{ \"blockChainLength\": \"http://localhost:8071\"}" -X POST http://localhost:8071/get-blocks
curl -i -H "Content-Type: application/json" -d "{ \"num\": 2}" -X POST http://localhost:8071/get-blocks
______________________________________________________________
curl -i -X GET http://localhost:8071/blockchain-info
curl -i -X GET http://localhost:8072/blockchain-info
curl -i -X GET http://localhost:8073/blockchain-info
curl -i -X GET http://localhost:8074/blockchain-info
curl -i -X GET http://localhost:8075/blockchain-info
curl -i -X GET http://localhost:8076/blockchain-info
_____________________________________________________________

curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8071/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8072/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8073\"}" -X POST http://localhost:8073/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8074\"}" -X POST http://localhost:8074/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8075\"}" -X POST http://localhost:8075/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8076\"}" -X POST http://localhost:8076/set-url
curl -i -X GET http://localhost:8072/init-blockchain
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8071/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8073/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8074/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8073\"}" -X POST http://localhost:8075/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8076/new-peer
curl -i -X GET http://localhost:8081/mine
//add node
	  }*/
}
