package blockchain_node.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.json.JSONObject;

import blockchain_node.core.Node;

@RestController
public class NodeController {
  @Autowired
  Node node;
  
  @PostMapping(value = "/v1", headers="Accept=application/json", consumes = "application/JSON")
  public void postAddTransaction(@RequestBody String params) throws IOException, Exception {
    String transactionParams = parseJson(params);
    System.out.println(transactionParams);
    node.createTransaction("david", "juan", 2000);
  }
  /*
  @PostMapping(value = "/mine", headers="Accept=application/json", consumes = "application/JSON")
  public Response postEvaluate(@RequestBody String params) throws IOException, Exception {
    Response newCodeEvaluator =  new CodeEvaluatorImpl(params).runEval();
    
    return newCodeEvaluator;
  }
  */
  private String parseJson(String json) {
    JSONObject obj = new JSONObject(json);

    String jsonParsed = obj.getString("code");

    return jsonParsed;
  }
  
}