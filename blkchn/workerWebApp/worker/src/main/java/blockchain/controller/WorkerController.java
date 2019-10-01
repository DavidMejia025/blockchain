package blockchain.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import blockchain.core.Worker;

@RestController
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
}



  
