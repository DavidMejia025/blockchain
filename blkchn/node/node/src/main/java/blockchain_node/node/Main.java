package blockchain_node.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main 
{
    public static void main( String[] args )
    {
      SpringApplication.run(Main.class, args);
    }
}

/*
 * 
 * 
headers  = {"content-type":"application/json"}
params   = {sender: "david", receiver:"german", value: 100}
response = HTTParty.post('http://localhost:8080/v1', body: params, headers: headers)
*/