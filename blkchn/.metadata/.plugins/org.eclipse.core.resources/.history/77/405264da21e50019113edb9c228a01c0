package blockchain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blockchain.core.Worker;
import blockchain.core.WorkerImpl;

@Configuration
public class Config {
  @Bean
  public Worker WorkerBean() {
    return new WorkerImpl("8081");
  }
}