package blockchain.node.utils;

import java.util.Arrays;
import java.util.Date;

public class Logs {

  public Logs(){
    this.addLog("Log system start...");
  }
  public void addBlankLine() {
	  System.out.println(" ");
  }
  
  public void addLog(String msj) {
    System.out.println(preMessage() + msj + postMessage());
  }
  
  private String preMessage() {
    Date dateobj = new Date();  
    
    return "[LOG] >>> " + "|" + dateobj +"| ";
  }
  
  private String postMessage() {
    String arr[] = new String[20];
    Arrays.fill(arr, ".");
    
    return " " + String.join("", arr);
  }
}

