package blockchain.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import blockchain.core.Worker;

public class WorkerController {
  @Autowired
  Worker worker;
  
  @GetMapping("/mine")
  public String Mine(String[] args) {
    //background job? If for any reason the services needs to be available for another queries maybe not.
    worker.mine();
    
    return "Finish mining";
  }
  
  @GetMapping(value = "/balance")
  public String getBalance() throws IOException, Exception {
    return "Nothing mined yet";
  }
  
  /*
  @PostMapping(value = "/mine-response", headers="Accept=application/json", consumes = "application/JSON")
  public void postMine(@RequestBody String params) throws IOException, Exception {
    String[] transactionParams = parseJson(params);
    
    System.out.println(transactionParams[1]);
  }*/
  
  private String[] parseJson(String json) {
    JSONObject obj = new JSONObject(json);
    String[] stringArray = new String[3];
    
    stringArray[0] = obj.getString("sender");
    stringArray[1] = obj.getString("receiver");
    stringArray[2] = Integer.toString((int) obj.get("value"));

    return stringArray;
  }
}



  
