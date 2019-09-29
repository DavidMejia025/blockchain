package utils;

import org.apache.commons.codec.digest.DigestUtils;

import core.Block;

public class Hashes {
  public static String calculateHash(String string) {
    String sha256hex = DigestUtils.sha256Hex(string);
    
    return sha256hex;
  }
}
