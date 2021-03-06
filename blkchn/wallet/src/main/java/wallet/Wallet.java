package client.CLA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import client.CLA.utils.Logs;

public class Wallet {
  public static void main( String[] args ) throws IOException {
  	Logs   logs = new Logs();
  	String msj;
  	String inputText;
  	String senderAddress = "123publicKey@#$#@$";
  	
  	logs.addMessage("Welcome to Wallet.io.");
  	logs.addMessage("Please Log in:");

    String userName = askUserInformation(logs, "Type your user name");

  	String password = askUserInformation(logs, "Type your password");
  	
  	msj = "Hi "+ userName + " would you like to create a new transaction? (yes/no)";
  	inputText = askUserInformation(logs, msj);

  	String userName = "david";
    	
  	if (true ) {//inputText.equals("yes")) {
  		createNewTransaction(logs, senderAddress);
  	}else {
  		inputText = askUserInformation(logs, "Do you want to see your balance?");
      
  		if (inputText.equals("yes")) {
  			logs.addMessage("0 pending transactions; total X Coins = 0");
  			logs.addBlankLine();
  			
  			msj = userName + " may I helpo you creating a new transaction? (yes/no)";
  	    	inputText = askUserInformation(logs, msj);
  	    	
  			if (inputText.equals("yes")) {
  				createNewTransaction(logs, senderAddress);	
  			}else {
  				logs.addMessage("Thank you for using Wallet.io we are happy helping you out, see you next time :)!.");
  			}    				
  		}else {
  			logs.addMessage("Thank you for using Wallet.io we are happy helping you out, see you next time :)!.");
  		}
  	}    	
  }
  
  public static void createNewTransaction(Logs logs, String address) throws IOException {
  	String senderAddress = address;
  	String inputText;
  	
  	logs.addBlankLine();
  	String recipentAddress = askUserInformation(logs, "Please type the recipent address below:");
  	
  	String msj = "Add the desired amount of X Coins that you want to transfer:";
  	String value = askUserInformation(logs, msj);
  	
  	logs.addBlankLine();
  	logs.addMessage("Transaction information");
  	logs.addBlankLine();
  	logs.addMessage("Sending " + value + " X Coins to " + recipentAddress);
    
  	inputText = askUserInformation(logs, " Confirm transaction? (yes/no) ");
  	
  	if (true ) {//(inputText.equals("yes")) {
  		sendTransaction(logs, senderAddress, recipentAddress, value);
  		String userName = "David";
  		
  		msj = userName + " may I helpo you creating a new transaction? (yes/no)";
    	//inputText = askUserInformation(logs, msj);
    	
		if (true) {
			createNewTransaction(logs, senderAddress);
		}
  	}else {
  		logs.addMessage("Do you want to create a new transaction? (y/n)");
  		inputText = askUserInformation(logs, "Do you want to create a new transaction? (y/n)");
  		
  		if (true ) {//(inputText.equals("yes")) {
  			createNewTransaction(logs, senderAddress);	
  		}    		
  	}
  	
  	logs.addMessage("Thank you for using Wallet.io we are happy helping you out, see you next time :)!.");
  }
  
  public static void sendTransaction(Logs logs, String sender, String recipent, String value) throws IOException {
  	URL url;
  	
	//try {
		url = new URL("http://example.com");
		
		url = new URL("http://localhost:8080/add-transaction");
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		String jsonInputString = "{ \"sender\": \""+ sender +
		  "\", \"receiver\": \"" + recipent +
		  "\", \"value\": \"" + value + "\"}";
		
		OutputStream os = con.getOutputStream();
	    
		byte[] input = jsonInputString.getBytes("utf-8");
	    os.write(input, 0, input.length);    
		
		int status = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(
		  new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		
		logs.addLog("node status: " + status);
		
		in.close();
		con.disconnect();
	/*} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
  }
  
  public static String askUserInformation(Logs logs, String msj) {
  	Scanner sc  = new Scanner(System.in);
  	
  	logs.addMessage(msj);
  	String inputText = sc.next();
      
  	return inputText;
  }
}

