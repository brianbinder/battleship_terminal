package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class BattleshipGame implements BoardGame {
  static final String[] battleshipNames = {"Frigate", "Battleship", "Destroyer"};
  static final char missChar = '☹';
  static final char pieceChar = '□';
  static final char hitChar = '☒';
  static final char killChar = '☠';
  static final char emptyChar = ' ';
  static final int pieceSize = 4;

  ArrayList<Battleship> battleships;
  
  private Board board;
  private boolean vertical = false;
  private boolean showPieces;

  private void createBattleships() {
    battleships = new ArrayList<Battleship>();
    for (String name : BattleshipGame.battleshipNames) battleships.add(new Battleship(name));
    for (Battleship piece : battleships) placeBattleship(piece);
  }

  private void placeBattleship(Battleship piece) {
    int maxAttempts = 200;
    int currentAttempt = 0;
    vertical = !vertical;
    while (currentAttempt < maxAttempts) {
      ++currentAttempt;
      int y = (int)(Math.random() * 7);
      int x = (int)(Math.random() * 7);
      try {
        if (isValidPlacement(y, x)) {
          String[] locations = new String[BattleshipGame.pieceSize];
          for (int i = 0; i < BattleshipGame.pieceSize; i++) {
            if (vertical) {
              locations[i] = Board.coordsToString(y + i, x);
              board.setValue(y + i, x, 1);
            } else {
              locations[i] = Board.coordsToString(y, x + i);
              board.setValue(y, x + i, 1);
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
    for (int i = 0; i < BattleshipGame.pieceSize; i++) {
      if (vertical) {
        if (!board.isEmpty(y + i, x)) return false;
      } else {
        if (!board.isEmpty(y, x + i)) return false;
      }
    }
    return true;
  }

  public BattleshipGame() {
    this(false);
  }
  public BattleshipGame(boolean hide) {
    showPieces = !hide;
    board = new Board(this);
    createBattleships();
  }

  public char boardChar(int val) {
    switch (val) {
      case 1:
        return showPieces ? BattleshipGame.pieceChar : BattleshipGame.emptyChar;
      case 2:
        return BattleshipGame.hitChar;
      case 3:
        return BattleshipGame.missChar;
      case 4:
        return BattleshipGame.killChar;
      default:
        return BattleshipGame.emptyChar;
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

  private void handleGuess(String guess) {
    String result = "miss";
    for (Battleship piece : battleships) {
      result = piece.checkYourself(guess);
      if (result != "miss") {
        if (result == "kill") removePiece(piece);
        break;
      }
    }
    board.setValue(guess, resultValue(result));
    if(!result.equals("kill")) System.out.println(result);
  }

  private void removePiece(Battleship piece) {
    for (String cell : piece.readHits()) {
      board.setValue(cell, resultValue("kill"));
    }
    System.out.printf(
      "%n%n%c  %c%nYou sank %s!!!%n%c  %c%n%n%n",
      BattleshipGame.killChar,
      BattleshipGame.killChar,
      piece.name,
      BattleshipGame.killChar,
      BattleshipGame.killChar
    );
    battleships.remove(piece);
  }
  
  private void printBoard() {
    board.print();
  }

  public static void main(String[] args) {
    // handle invalid user input gracefully
    // hits cannot become misses
    // get terminal width and center board accordingly
    // vary the size of ships
    // add color to the board
    boolean hidden = false;
    int printEvery = 1;
    try {
      hidden = args[0].toLowerCase().equals("hide");
      printEvery = Integer.parseInt(args[1]);
    } catch (ArrayIndexOutOfBoundsException e) {}
    BattleshipGame activeGame = new BattleshipGame(hidden);
    activeGame.printBoard();
    int numOfGuesses = 0;
    while (activeGame.battleships.size() > 0) {
      ++numOfGuesses;
      activeGame.handleGuess(GameInterface.query());
      if (numOfGuesses % printEvery == 0) activeGame.printBoard();
    }
    System.out.printf("You win! It took %d guesses.", numOfGuesses);
  }
}