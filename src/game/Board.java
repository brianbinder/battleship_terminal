package game;

class Board {

  public static char yToC(int y) {
    switch (y) {
      case 0:
        return 'A';
      case 1:
        return 'B';
      case 2:
        return 'C';
      case 3:
        return 'D';
      case 4:
        return 'E';
      case 5:
        return 'F';
      case 6:
        return 'G';
      default:
        throw new IllegalArgumentException("Y value must be between 0 and 6");
    }
  }

  public static int cToY(String s) {
    return cToY(s.charAt(0));
  }
  public static int cToY(char c) {
    switch (c) {
      case 'A':
        return 0;
      case 'B':
        return 1;
      case 'C':
        return 2;
      case 'D':
        return 3;
      case 'E':
        return 4;
      case 'F':
        return 5;
      case 'G':
        return 6;
      default:
        throw new IllegalArgumentException("Valid c values are A through G");
    }
  }

}