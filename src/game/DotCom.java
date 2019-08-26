package game;

import java.util.Arrays;
import java.util.ArrayList;

class DotCom {
  String name;
  ArrayList<String> locationCells;

  DotCom(String inputName) {
    name = inputName;
  }

  protected String checkYourself(String guess) {
    if (locationCells.remove(guess)) {
      return locationCells.size() == 0 ? "kill" : "hit";
    }
    return "miss";
  }

  void setLocationCells(String[] locations) {
    locationCells = new ArrayList<String>(Arrays.asList(locations));
    // printLocations();
  }

  private void printLocations() {
    System.out.format("%s's locations are %s%n", name, Arrays.toString(locationCells.toArray()));
  }
}