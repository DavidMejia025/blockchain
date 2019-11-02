package blockchain.utils;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class HttpClient {
  WebClient client;
  @Autowired
  Logs logs;
  
  public HttpClient() {}
  
  public void addClient(String url) {
    WebClient client = WebClient
     .builder()
       .baseUrl(url)
       .defaultCookie("cookieKey", "cookieValue")
       .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
       .defaultUriVariables(Collections.singletonMap("url", url))
     .build();
    
    this.client = client;  
   }
  
   public WebClient getWebClient() {
     return this.client;
   }
   
   public String sendGet(String url) {
     String response = this.client.get()
       .uri(url)
       .retrieve()
       .bodyToMono(String.class)
       .block();
     
     return response;
   }
   
   public String sendPost(String url, String params) {
     String response = this.client.post()
       .uri(url)
       .body(BodyInserters.fromObject(params))
       .retrieve()
       .bodyToMono(String.class)
       .block();
     
     return response;
   }
 }
