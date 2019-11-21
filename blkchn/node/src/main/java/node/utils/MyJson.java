package blockchain.node.utils;

import java.util.List;

import org.json.JSONObject;

public class MyJson {
  public static String getJsonString(String params, String key) {
    JSONObject obj = new JSONObject(params);
    String result = obj.getString(key); 
    
    return result;
  }
  
  public static int getJsonInt(String params, String key) {
    JSONObject obj = new JSONObject(params);
    int result = obj.getInt(key); 
    
    return result;
  }
}

