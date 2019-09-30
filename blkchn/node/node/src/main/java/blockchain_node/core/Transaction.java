package blockchain_node.core;

public class Transaction {
  private  String sender;
  private  String recipient;
  private  int    value;
  //autowired
  private BlockChain blockChain;
  
  public Transaction(String sender, String recipient, int value) {
    this.sender    = sender;
    this.recipient = recipient;
    this.value     = value;
  }
}
