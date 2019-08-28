package game;

class Board {
  int[][] grid;
  BoardGame game;

  static enum YVal {
    A (0, "A"),
    B (1, "B"),
    C (2, "C"),
    D (3, "D"),
    E (4, "E"),
    F (5, "F"),
    G (6, "G");

    int val;
    String letter;
    YVal(int y, String c) {
      val = y;
      letter = c;
    }
  }

  public Board(BoardGame gameModel) {
    game = gameModel;
    grid = new int[7][7];
    for (int i = 0; i < 7; i++) grid[i] = new int[7];
  }

  private static IllegalArgumentException argumentComplaint() {
    throw new IllegalArgumentException("Input must be between A0 and G6!!");
  }

  public static String yToString(int y) {
    for (YVal pair : YVal.values()) {
      if (pair.val == y) return pair.letter;
    }
    throw argumentComplaint();
  }

  public static int cToY(String s) {
    for (YVal pair : YVal.values()) {
      if (pair.letter.equals(s)) return pair.val;
    }
    throw argumentComplaint();
  }

  public static String coordsToString(int y, int x) {
    return Board.yToString(y) + Integer.toString(x);
  }

  public void setValue(String guess, int val) {
    String yString = "";
    String xString = "";
    boolean hitDigit = false;
    for (char c : guess.toCharArray()) {
      if (Character.isAlphabetic(c)) {
        if (!hitDigit) {
          yString += Character.toString(c);
        } else {
          throw Board.argumentComplaint();
        }
      } else {
        hitDigit = true;
        if (Character.isDigit(c)) {
          xString += Character.toString(c);
        } else {
          throw Board.argumentComplaint();
        }
      }
    }
    int y = Board.cToY(yString);
    int x = Integer.valueOf(xString);
    setValue(y, x, val);
  }
  public void setValue(int y, int x, int val) {
    grid[y][x] = val;
  }

  public boolean isEmpty(int y, int x) {
    return grid[y][x] == 0;
  }

  public void print() {
    for (int i = 0; i < grid.length; i++) {
      String rowString = String.valueOf(Board.yToString(i));
      for (int j = 0; j < grid[i].length; j++) rowString += " " + String.valueOf(game.boardChar(grid[i][j])) + " ";
      System.out.println(rowString);
    }
    System.out.println("  0  1  2  3  4  5  6 ");
  }

}