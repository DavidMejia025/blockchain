package core;

public class Client {
  public  String publicKey;
  private String privateKey;
  public  String url;
  public  String name;
  
  public Client(String url, String name) {
    createPublicKey();
    createPrivateKey();
    this.url        = url;
    this.name       = name;
  }
  
  private void createPublicKey() {
    this.publicKey = "myPublicKey";
    }
  
  private void createPrivateKey() {
    this.privateKey = "My hidden key";
  }
  
  public Transaction createTransaction(String to, int value) {
    Transaction newTransaction = new Transaction(privateKey, to, value);
    
    return newTransaction;
  }
  
  // create transaction
}