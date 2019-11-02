package blockchain.node.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import blockchain.node.core.Transaction;

public class Lists {
  @Autowired
  Logs logs;
  
  public Lists() {}
  
  public String transactionsToString(List<Transaction> transactions) {
	  String transactionsString = "";
	  Transaction transaction;
	  
	  for (int i = 1; i < transactions.size(); i++) {
		transaction = transactions.get(i);
		
		transactionsString += "" +
		  transaction.getSender() +
		  transaction.getReceiver() +
		  transaction.getValue()+
          "    ";
	  }
	 
	  return transactionsString;
  }
  
  public void printOutTransactions(List<Transaction> transactions) {
	  Transaction transaction;
	  
	  for (int i = 1; i < transactions.size(); i++) {
	  	transaction = transactions.get(i);
	  	logs.addLog("Transaction: From: " + transaction.getSender() + 
	  	" to: " + transaction.getReceiver() + 
	  	" value: " +  transaction.getValue());
	  }
  }
}