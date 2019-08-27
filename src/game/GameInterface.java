package game;

import java.io.*;

public class GameInterface {
  public static String query() {
    return query("Enter a guess ");
  }
  public static String query(String prompt) {
    String inputLine = null;
    System.out.printf("%s ", prompt);
    try {
      BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
      inputLine = is.readLine();
    } catch (IOException e) {
      System.out.println("IOException when attempting to receive input: " + e);
    }
    return inputLine.toUpperCase();
  }
}