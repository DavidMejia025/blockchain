package blockchain.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import blockchain.core.Worker;
import blockchain.utils.Logs;
import blockchain.utils.MyJson;

@RestController
public class WorkerController {
  @Autowired
  Worker worker;
  @Autowired
  Logs logs;
  
  @PostMapping(value = "/set-address", headers="Accept=application/json", consumes = "application/JSON")
  public void setAddress(@RequestBody String params) {
    worker.setAddress(MyJson.getJsonString(params, "url"));
    
    logs.addLog("The current Address is: " + worker.getAddress());
  }
  
  @PostMapping(value = "/add-node", headers="Accept=application/json", consumes = "application/JSON")
  public void addNode(@RequestBody String params) {
    worker.addNode(MyJson.getJsonString(params, "url"));
    
    logs.addLog("New node added with address: " + MyJson.getJsonString(params, "url"));
  }
  
  @GetMapping("/list-nodes")
  public List<String> listNodes(String[] args) {
    return worker.listNodes();
  }
  
  @GetMapping("/mine")
  public void Mine(String[] args) {
    worker.mine();
  }
  
  @GetMapping(value = "/balance")
  public String getBalance() throws IOException, Exception {
    return "To be implemented with a wallet";
  }
}



  
