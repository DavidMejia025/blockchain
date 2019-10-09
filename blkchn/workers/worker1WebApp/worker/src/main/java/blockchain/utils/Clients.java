package blockchain.utils;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Clients {
  WebClient client;
  @Autowired
  Logs logs;
  
  public Clients() {}
  
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
 }
