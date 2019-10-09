package blockchain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blockchain.core.Worker;
import blockchain.utils.Logs;

@Configuration
public class Config {
  @Bean
  public Worker WorkerBean() {
    return new Worker("http://localhost:8082");
  }
  
  @Bean
  public Logs LogsBean() {
	   return new Logs();
  }
}
