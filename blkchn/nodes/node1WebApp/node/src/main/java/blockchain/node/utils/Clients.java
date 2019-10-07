package blockchain.node.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class Clients {
	List<String>    ports   = new ArrayList<String>();
	List<WebClient> clients = new ArrayList<WebClient>();
	@Autowired
	Logs logs;
	
	public Clients() {
		ports.add("http://node2:8072");
		
		for (int i = 0; i < ports.size(); i++) {
			addCLient(ports.get(i));
		}
	}
	
	public String addCLient(String port) {
		  WebClient client = WebClient
			  .builder()
			    .baseUrl(port)
			    .defaultCookie("cookieKey", "cookieValue")
			    //.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json") 
			    .defaultUriVariables(Collections.singletonMap("url", port))
			  .build();
		  
		  this.clients.add(client);
		  
		  return "New Node added. Node port: "+ port;  
	  }
	
    public void sendGetRequestToAll(String uri) {
    	for (int i = 0; i < clients.size(); i++){
    		String result = clients.get(i).get()
    		         .uri(uri)
    		         .retrieve()
    		         .bodyToMono(String.class)
    		         .block();
    		
    		logs.addLog("Response from node: 2 is: " + result);
    	}
    }
    
	public List<WebClient>  getClients() {
		return this.clients;
	}
}
