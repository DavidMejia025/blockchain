package blockchain.node.core;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import blockchain.node.utils.Clients;
import blockchain.node.utils.Logs;

public class Peer {
 	Boolean linked = false;
 	String  url;
 	//@Autowired
 	Clients peerClient = new Clients();
 	@Autowired
 	Logs logs;
 		
 	public Peer(String url) {
 	  this.url = url;
 	  this.peerClient.addClient(url);
 	}
 	
 	public String handShake(String nodeUrl) {
 	  String params = "{ \"url\": \""+ nodeUrl +"\"}";
    System.out.println(params);
    System.out.println(params.getClass());
    
    String response = peerClient.getWebClient().post()
    .uri("/hand-shake")
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    .accept(MediaType.APPLICATION_JSON)
    .body(BodyInserters.fromObject(params))
    .retrieve()
    .bodyToMono(String.class)
    .block();

     setPeerLinked(true);
 	    
 		  return response;
 	}
 	
 	public String getBlocks(int blockChainIndex) {
    String params = "{ \"blockChainLength\": \""+ blockChainIndex +"\"}";
 		
     String response = peerClient.getWebClient().post()
      .uri("/get-blocks")
      .body(BodyInserters.fromObject(params))
      .retrieve()
      .bodyToMono(String.class)
      .block();
 	    
 		 return response;
 	}
 	
 	public String sendBlock(String nodeUrl, Block block) throws JsonProcessingException {
 	  ObjectMapper jacksonObj = new ObjectMapper(); 
 	  String       jsonBlock  = jacksonObj.writeValueAsString(block);
 	  
    JSONObject params = new JSONObject();
    params.put("peerUrl", nodeUrl);
    params.put("block",   jsonBlock);
    String stringParams = params.toString();

     String response = peerClient.getWebClient().post()
      .uri("/valid-block")
      .body(BodyInserters.fromObject(stringParams))
      .retrieve()
      .bodyToMono(String.class)
      .block();
      
    return response;
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
}
