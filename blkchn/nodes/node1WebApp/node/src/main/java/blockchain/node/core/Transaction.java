package blockchain.node.core;

public class Transaction {
  private  String sender;
  private  String recipient;
  private  int    value;
  
  public Transaction(String sender, String recipient, int value) {
    this.sender    = sender;
    this.recipient = recipient;
    this.value     = value;
  }
  
  public String getSender() {
    return this.sender;
  }
  
  public String getReceiver() {
    return this.recipient;
  }
  
  public Integer getValue() {
    return this.value;
  }
}
