package blockchain.node.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import blockchain.node.core.Block;
import blockchain.node.core.BlockChain;
import blockchain.node.core.Node;


@RestController
public class NodeController {
  @Autowired
  Node node;
  
  @GetMapping("/init")
  public String nodeInit(String[] args) {
    BlockChain blockchain = node.getBlockChain();
    
    Block root = blockchain.getLastBlock();
    
    return "Block chain with root node that has previous hash eq = " + root.getPrevHash() + " was created";
  }
  
  @PostMapping(value = "/mine", headers="Accept=application/json", consumes = "application/JSON")
  public boolean postMine(@RequestBody String params) throws IOException, Exception {
    int nonce = parseJsonMine(params);
    
    BlockChain blockchain = node.getBlockChain();
    Block      prevBlock  = blockchain.getLastBlock();
    
    boolean proveOfWorkResult = blockchain.validProofOfWork(prevBlock.getNonce(), nonce);
    
    proveOfWorkResult = true;
    
    if (proveOfWorkResult == true) {
      String prevHash = blockchain.prevBlocktoString(prevBlock);
      
      blockchain.createBlock(prevHash, nonce);
      
      System.out.println(blockchain.getLastBlock().getNonce());
      System.out.println(blockchain.getLastBlock().getPrevHash());
    }else {
      
    }
    System.out.println(nonce);
    System.out.println(proveOfWorkResult);
    
    return proveOfWorkResult;
  }
  
  @GetMapping("/last-nounce")
  public String getLastNounce(String[] args) {
    BlockChain blockchain = node.getBlockChain();
    Block      root       = blockchain.getLastBlock();
    
    JSONObject json = new JSONObject();
    
    json.put("difficulty", blockchain.getDifficulty());
    json.put("nonce", root.getNonce());
    
    return json.toString();
  }
  
  @PostMapping(value = "/add-transaction", headers="Accept=application/json", consumes = "application/JSON")
  public void postAddTransaction(@RequestBody String params) throws IOException, Exception {
    String[] transactionParams = parseJsonTransaction(params);
    
    System.out.println(transactionParams[1]);
  }
  //Need to get a better way to parse any json sending the fields....
  private Integer parseJsonMine(String json) {
    JSONObject obj = new JSONObject(json);

    return (int) obj.get("nonce");
  }
  
  private String[] parseJsonTransaction(String json) {
    JSONObject obj = new JSONObject(json);
    String[] stringArray = new String[3];
    
    stringArray[0] = obj.getString("sender");
    stringArray[1] = obj.getString("receiver");
    stringArray[2] = Integer.toString((int) obj.get("value"));

    return stringArray;
  }
}
