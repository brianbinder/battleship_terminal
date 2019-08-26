package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.io.*;

public class DotComGame {
  static final String[] dotComNames = {"Amazon", "EBay", "PayPal"};
  static final char missChar = '☹';
  static final char shipChar = '□';
  static final char hitChar = '☒';
  static final char killChar = '☠';
  static final char emptyChar = ' ';
  static final int pieceSize = 4;

  ArrayList<DotCom> dotComs;
  int[][] grid;
  private boolean vertical = false;
  private boolean showPieces = true;

  private void createGrid() {
    grid = new int[7][7];
    for (int i = 0; i < 7; i++) grid[i] = new int[7];
  }

  private void createDotComs() {
    dotComs = new ArrayList<DotCom>();
    for (String name : DotComGame.dotComNames) dotComs.add(new DotCom(name));
    for (DotCom piece : dotComs) placeDotCom(piece);
  }

  private void placeDotCom(DotCom piece) {
    int maxAttempts = 200;
    int currentAttempt = 0;
    vertical = !vertical;
    while (currentAttempt < maxAttempts) {
      ++currentAttempt;
      int y = (int)(Math.random() * 7);
      int x = (int)(Math.random() * 7);
      try {
        if (isValidPlacement(y, x)) {
          String[] locations = new String[DotComGame.pieceSize];
          for (int i = 0; i < DotComGame.pieceSize; i++) {
            if (vertical) {
              locations[i] = Board.yToC(y + i) + Integer.toString(x);
              grid[y + i][x] = 1;
            } else {
              locations[i] = Board.yToC(y) + Integer.toString(x + i);
              grid[y][x + i] = 1;
            }
          }
          piece.setLocationCells(locations);
          break;
        }
      } catch (ArrayIndexOutOfBoundsException e) {}
    }
    if (currentAttempt == maxAttempts) System.out.printf("Unable to place a Dotcome in %d attempts!!!%nPerhaps the game is incorrectly formatted%n", currentAttempt);
  }

  private boolean isValidPlacement(int y, int x) {
    for (int i = 0; i < DotComGame.pieceSize; i++) {
      if (vertical) {
        if (!isEmpty(y + i, x)) return false;
      } else {
        if (!isEmpty(y, x + i)) return false;
      }
    }
    return true;
  }


  private boolean isEmpty(int y, int x) {
    return grid[y][x] == 0;
  }

  public void start(boolean hide) {
    showPieces = !hide;
    start();
  }
  public void start() {
    createGrid();
    createDotComs();
    printBoard();
  }

  private char gridChar(int val) {
    switch (val) {
      case 1:
        return showPieces ? DotComGame.shipChar : DotComGame.emptyChar;
      case 2:
        return DotComGame.hitChar;
      case 3:
        return DotComGame.missChar;
      case 4:
        return DotComGame.killChar;
      default:
        return DotComGame.emptyChar;
    }
  }

  private int resultValue(String result) {
    switch (result) {
      case "hit":
        return 2;
      case "miss":
        return 3;
      case "kill":
        return 4;
      default:
        return 0;
    }
  }

  private void updateGrid(String result, String guess) {
    int y = Board.cToY(guess.charAt(0));
    int x = Integer.valueOf(guess.substring(1));
    grid[y][x] = resultValue(result);
  }

  private void handleGuess(String guess) {
    String result = "miss";
    for (DotCom piece : dotComs) {
      result = piece.checkYourself(guess);
      if (result != "miss") {
        if (result == "kill") removePiece(piece);
        break;
      }
    }
    updateGrid(result, guess);
    if(!result.equals("kill")) System.out.println(result);
  }

  private void removePiece(DotCom piece) {
    for (String cell : piece.readHits()) {
      updateGrid("kill", cell);
    }
    System.out.printf(
      "%n%n%c  %c%n%s has run out of funding!!!%n%c  %c%n%n%n",
      DotComGame.killChar,
      DotComGame.killChar,
      piece.name,
      DotComGame.killChar,
      DotComGame.killChar
    );
  }
  
  private void printBoard() {
    for (int i = 0; i < grid.length; i++) {
      String rowString = String.valueOf(Board.yToC(i));
      for (int j = 0; j < grid[i].length; j++) rowString += " " + String.valueOf(gridChar(grid[i][j])) + " ";
      System.out.println(rowString);
    }
    System.out.println("  0  1  2  3  4  5  6 ");
  }
  
  private static String query() {
    return query("Enter a guess ");
  }
  private static String query(String prompt) {
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

  public static void main(String[] args) {
    // move more functionality to the board class
    // rewrite to use enums for things like cToY
    // handle invalid user input gracefully
    // get terminal width and center board accordingly
    boolean hidden = false;
    int printEvery = 1;
    try {
      hidden = args[0].toLowerCase().equals("hide");
      printEvery = Integer.parseInt(args[1]);
    } catch (ArrayIndexOutOfBoundsException e) {}
    DotComGame activeGame = new DotComGame();
    activeGame.start(hidden);
    int numOfGuesses = 0;
    while (activeGame.dotComs.size() > 0) {
      ++numOfGuesses;
      activeGame.handleGuess(query());
      if (numOfGuesses % printEvery == 0) activeGame.printBoard();
    }
    System.out.printf("You win! It took %d guesses.", numOfGuesses);
  }
}