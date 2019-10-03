package client.CLA;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import client.CLA.utils.Logs;
import client.CLA.utils.ParameterStringBuilder;

public class Client {
    public static void main( String[] args ) throws IOException {
    	Logs logs            = new Logs();
    	String senderAddress = "AS23245#%%3vFG43HD234@#$%$%234";
    	
    	logs.addLog("Hi #name would you like to create a new transaction?");
    	
    	logs.addLog("Welcome to Wallet.io.");
    	logs.addLog("Please Log in:");
    	
    	//To be implemented
    	
    	logs.addLog("Hi #name would you like to create a new transaction?");
    	
    	if (true) {
    		createNewTransaction(senderAddress);
    	}else {
    		logs.addLog("Do you want to see your balance?");
    		if (true) {
    			//show balance
    			logs.addLog("Hi #name would you like to create a new transaction?");
    			if (true) {
    				createNewTransaction(senderAddress);	
    			}else {
    				//log out and end process
    			}    				
    		}else {
    			//log out and end process
    		}
    		// 
    	}    	
    }
    
    public static void createNewTransaction(String address) throws IOException {
    	String senderAddress = address;
    	Logs logs = new Logs();
    	
    	logs.addLog("Please type the recipent address below:");
    	String recipentAddress = "324FDFVDRG%%$f2345FVFF";
    	
    	logs.addLog("Add the desired amount of X Coins that you want to transfer:");
    	int value = 100;
    	
    	logs.addLog("Transaction information");
    	logs.addLog("Sending " + value + " X Coins to " + recipentAddress + "Confirm transaction?");
    	
    	if (true) {
    		sendTransaction(senderAddress, recipentAddress, value);
    	}else {
    		logs.addLog("Create new transaction");
    		if (true) {
    			createNewTransaction(senderAddress);	
    		}    		
    	}
    	
    	logs.addLog("Thank you for using Wallet.io we are happy helping you out, see you next time.");
    }
    
    public static void sendTransaction(String sender, String recipent, Integer value) throws IOException {
    	URL url;
    	
		try {
			url = new URL("http://example.com");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestMethod("POST");
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("sender", sender);
			parameters.put("recipent", recipent);
			parameters.put("value", "10");
			
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(
			  new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			
			System.out.println(".............................................");
			System.out.println(content);
			
			in.close();
			con.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

