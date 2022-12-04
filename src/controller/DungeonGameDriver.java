package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.Set;
import model.Direction;
import model.creature.DungeonPlayer;
import model.creature.Player;
import model.dungeon.DungeonMap;
import model.game.GameCalculator;
import model.game.GameCalculatorImpl;
import model.randomhelper.DungeonRandomHelper;
import model.randomhelper.RandomHelper;

/**
 * Driver for the Dungeon Model.
 *
 *
 */
public class DungeonGameDriver {

  static FileWriter writer;
  static Scanner in;
  static GameCalculator game;

  private static void fileSetUp() throws IOException {
    File myObjFile = new File("./res/runningResult.txt");
    writer = new FileWriter(myObjFile);
    in = new Scanner(System.in);
  }
  
  private static void log(String string) throws IOException {
    writer.write(string);
  }
  
  private static void runGame(DungeonMap m1, Player p1, RandomHelper helper) throws IOException {
    game.enterGame(m1, p1);
    log(game.getMapString(m1, p1));
    log("\n-------------------\n");
    log("The player state now is:\n");
    log(game.getPlayerString(p1));
    log("\n-------------------\n");
    log("The Location description is:\n");
    log(game.getLocationString(m1, p1.getCoord()));
    log("\n-------------------\n");
    log("Now we start the game:\nDirections : [");
    int steps = 0;
    while (!game.isAtEnd(m1, p1) && steps < 600) {
      game.pickTreasure(m1, p1);
      Set<Direction> ds = game.walkableDirs(m1, p1);
      Direction d = helper.directionChoice(ds);
      if (game.walkPlayer(m1, p1, d)) {
        log("+");
      } else {
        log("^");
      }
      log(d.name());
      log(", ");
      ++steps;
    }
    log("\n\nThe player reached the end ? : ");
    log(game.isAtEnd(m1, p1) ? "Yes" : "No");
    log("\nTakes in total steps : ");
    log(String.valueOf(steps));
    log("\n-------------------\n");
    
    log("Now the game result is:\n");
    log(game.getMapString(m1, p1));
    log("\n-------------------\n");
    log("Now the player description is:\n");
    log(game.getPlayerString(p1));
  }
  
  private static void setUp3() throws IOException {
    try {
      fileSetUp();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log("\n---------START----------\n");
    log("Initializing the Unwrapped Game:\n");
    RandomHelper helper = new DungeonRandomHelper(14);
    game = new GameCalculatorImpl(helper);
    Player p1 = new DungeonPlayer(0, 0, "Tonnie");
    DungeonMap m1 = game.initGame(7, 8, 4, false, 0.4);
    runGame(m1, p1, helper);
    log("\n\n---------NEW GAME----------\n");
    log("Initializing the Wrapped Game:\n");
    DungeonMap m2 = game.initGame(8, 8, 4, true, 0.75);
    runGame(m2, p1, helper);
    log("\n---------END----------\n");
  }

  /**
   * Main Function of Driver.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    try {
      
      DungeonGameController controller = new DungeonGameController();
      controller.run(br, bw);
      
      
      // setUp4()
      setUp3();
      // setUp2()
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
