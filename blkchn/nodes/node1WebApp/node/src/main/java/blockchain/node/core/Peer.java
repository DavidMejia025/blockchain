package blockchain.node.core;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import blockchain.node.utils.HttpClient;
import blockchain.node.utils.Logs;

public class Peer {
 	Boolean linked = false;
 	String  url;
 	HttpClient HttpClient = new HttpClient();
 	@Autowired
 	Logs logs;
 		
 	public Peer(String url) {
 	  this.url = url;
 	  this.HttpClient.addClient(url);
 	}
  
  public Boolean getPeerLinked() {
   return linked;
  }
  
  public void setPeerLinked(Boolean status) {
   this.linked = status;
  }
   
  public String getPeerUrl() {
   return this.url;
  }
  
 	public String handShake(String nodeUrl) {
 	  JSONObject params = new JSONObject();
    params.put("url", nodeUrl);
    String stringParams = params.toString();
    
    String response = HttpClient.sendPost("/hand-shake",stringParams);

    setPeerLinked(true);
 	    
 		 return response;
 	}
 	
 	public String getBlocks(int blockChainIndex) {
    JSONObject params = new JSONObject();
    params.put("blockChainLength", blockChainIndex);
    String stringParams = params.toString();
    
    String response = HttpClient.sendPost("/get-blocks",stringParams);
 	    
 		 return response;
 	}
 	
 	public String sendBlock(String nodeUrl, Block block) throws JsonProcessingException {
 	  ObjectMapper jacksonObj = new ObjectMapper(); 
 	  String       jsonBlock  = jacksonObj.writeValueAsString(block);
 	  
    JSONObject params = new JSONObject();
    params.put("peerUrl", nodeUrl);
    params.put("block",   jsonBlock);
    String stringParams = params.toString();

    String response = HttpClient.sendPost("/valid-block",stringParams);

    return response;
 	}
}

