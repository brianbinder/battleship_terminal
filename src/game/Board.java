package game;

class Board {
  int[][] grid;
  BoardGame game;

  static enum YVal {
    A (0, 'A'),
    B (1, 'B'),
    C (2, 'C'),
    D (3, 'D'),
    E (4, 'E'),
    F (5, 'F'),
    G (6, 'G');

    int val;
    char letter;
    YVal(int y, char c) {
      val = y;
      letter = c;
    }
  }

  public Board(BoardGame gameModel) {
    game = gameModel;
    grid = new int[7][7];
    for (int i = 0; i < 7; i++) grid[i] = new int[7];
  }

  public static char yToC(int y) {
    for (YVal pair : YVal.values()) {
      if (pair.val == y) return pair.letter;
    }
    throw new IllegalArgumentException("Y value must be between 0 and 6");
  }

  public static int cToY(String s) {
    return cToY(s.charAt(0));
  }
  public static int cToY(char c) {
    for (YVal pair : YVal.values()) {
      if (pair.letter == c) return pair.val;
    }
    throw new IllegalArgumentException("Valid c values are A through G");
  }

  public static String coordsToString(int y, int x) {
    return Board.yToC(y) + Integer.toString(x);
  }

  public void setValue(String guess, int val) {
    int y = Board.cToY(guess.charAt(0));
    int x = Integer.valueOf(guess.substring(1));
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
      String rowString = String.valueOf(Board.yToC(i));
      for (int j = 0; j < grid[i].length; j++) rowString += " " + String.valueOf(game.boardChar(grid[i][j])) + " ";
      System.out.println(rowString);
    }
    System.out.println("  0  1  2  3  4  5  6 ");
  }

}