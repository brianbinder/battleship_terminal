package game;

class Board {

  enum YVal {
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

}