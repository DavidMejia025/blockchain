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
    
    logs.addLog("The current Url is: " + worker.getAddress());
  }
  
  @PostMapping(value = "/add-node", headers="Accept=application/json", consumes = "application/JSON")
  public void addNode(@RequestBody String params) {
    worker.addNode(MyJson.getJsonString(params, "url"));
  }
  
  @GetMapping("/list-nodes")
  public List<String> listNodes(String[] args) {
    return worker.listNodes();
  }
  
  @GetMapping("/mine")
  public String Mine(String[] args) {
    // background job? If for any reason the services needs to be available for another queries such as balance?
    worker.mine();
    
    return "Finish mining";
  }
  
  @GetMapping(value = "/balance")
  public String getBalance() throws IOException, Exception {
    return "Nothing mined yet";
  }
}
/*
curl -i -X GET http://localhost:8081/list-nodes
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8081\"}" -X POST http://localhost:8081/set-address
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8081/add-node


curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8081\"}" -X POST http://localhost:8081/set-address
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8081/add-node
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8081/add-node
curl -i -X GET http://localhost:8081/list-nodes

curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8072/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8073\"}" -X POST http://localhost:8073/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8074\"}" -X POST http://localhost:8074/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8075\"}" -X POST http://localhost:8075/set-url
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8076\"}" -X POST http://localhost:8076/set-url
curl -i -X GET http://localhost:8072/init
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8071/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8073/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8072\"}" -X POST http://localhost:8074/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8073\"}" -X POST http://localhost:8075/new-peer
curl -i -H "Content-Type: application/json" -d "{ \"url\": \"http://localhost:8071\"}" -X POST http://localhost:8076/new-peer
curl -i -X GET http://localhost:8081/mine
//add node
 }*/


  
