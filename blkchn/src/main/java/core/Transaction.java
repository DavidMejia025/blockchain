package core;

public class Transaction {
  private final String sender;
  private final String recipient;
  private final int    value;
  //autowired
  private BlockChain blockChain;
  
  public Transaction Transaction(String sender, String recipient, int value) {
    this.sender    = sender;
    this.recipient = recipient;
    this.value     = value;
    
    return BlockChain.getlastBlock().index + 1;
  }
}
