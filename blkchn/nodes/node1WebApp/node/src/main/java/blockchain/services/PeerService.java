package blockchain.services;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import blockchain.node.core.Block;

public class PeerService {
  /*public static String handShake(String nodeUrl) {
    JSONObject params = new JSONObject();
    params.put("url", nodeUrl);
    String stringParams = params.toString();
    
    String response = HttpClient.getWebClient().post()
    .uri("/hand-shake")
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    .accept(MediaType.APPLICATION_JSON)
    .body(BodyInserters.fromObject(stringParams))
    .retrieve()
    .bodyToMono(String.class)
    .block();

     setPeerLinked(true);
      
     return response;
  }
  
  public static String getBlocks(int blockChainIndex) {
    //String params = "{ \"blockChainLength\": \""+ blockChainIndex +"\"}";
    JSONObject params = new JSONObject();
    params.put("blockChainLength", blockChainIndex);
    String stringParams = params.toString();
    
     String response = HttpClient.getWebClient().post()
      .uri("/get-blocks")
      .body(BodyInserters.fromObject(stringParams))
      .retrieve()
      .bodyToMono(String.class)
      .block();
      
    return response;
  }
  
  public static String sendBlock(String nodeUrl, Block block) throws JsonProcessingException {
    ObjectMapper jacksonObj = new ObjectMapper(); 
    String       jsonBlock  = jacksonObj.writeValueAsString(block);
    
    JSONObject params = new JSONObject();
    params.put("peerUrl", nodeUrl);
    params.put("block",   jsonBlock);
    String stringParams = params.toString();

     String response = HttpClient.getWebClient().post()
      .uri("/valid-block")
      .body(BodyInserters.fromObject(stringParams))
      .retrieve()
      .bodyToMono(String.class)
      .block();
      
    return response;
  }*/
}
