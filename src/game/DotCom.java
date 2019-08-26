package game;

import java.util.Arrays;
import java.util.ArrayList;

class DotCom {
  String name;
  private ArrayList<String> locationCells;
  private ArrayList<String> hitCells = new ArrayList<>();

  DotCom(String inputName) {
    name = inputName;
  }

  protected String checkYourself(String guess) {
    if (locationCells.remove(guess)) {
      hitCells.add(guess);
      return locationCells.size() == 0 ? "kill" : "hit";
    }
    return "miss";
  }
  
  String[] readHits() {
    return hitCells.toArray(new String[0]);
  }

  void setLocationCells(String[] locations) {
    setLocationCells(locations, false);
  }
  void setLocationCells(String[] locations, boolean printSelf) {
    locationCells = new ArrayList<String>(Arrays.asList(locations));
    if (printSelf) printLocations();
  }

  private void printLocations() {
    System.out.format("%s's locations are %s%n", name, Arrays.toString(locationCells.toArray()));
  }
}