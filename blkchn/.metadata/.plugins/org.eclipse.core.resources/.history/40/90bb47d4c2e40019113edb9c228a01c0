package blockchain.node.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    Block      root       = blockchain.getLastBlock();
    
    return "Blockchain with root node that has previous hash eq = " + root.getPrevHash() + " was created";
  }
  
  @PostMapping(value = "/mine", headers="Accept=application/json", consumes = "application/JSON")
  public Map<String, String>  postMine(@RequestBody String params) throws IOException, Exception {
    int nonce = parseJsonMine(params);
    
    JSONObject response1 = node.mineBlock(nonce);
    System.out.println(response1);
    
    Map<String, String> response = sayHello(); //Wy json object is not working on the client side?
    return response;
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
    
    int value = Integer.parseInt(transactionParams[2]);
    
    node.addTransaction(transactionParams[0], transactionParams[1], value); //convert to has map?
    
    System.out.println(transactionParams[1]);
  }
  
  //Need to get a better way to parse any json sending the fields.... ?
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
  
  @GetMapping
  public Map<String, String> sayHello() {
      HashMap<String, String> map = new HashMap<>();
      map.put("key", "value");
      map.put("foo", "bar");
      map.put("aa", "bb");
      return map;
  }
}
