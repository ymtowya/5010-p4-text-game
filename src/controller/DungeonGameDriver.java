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


  /**
   * Main Function of Driver.
   *
   * @param args arguments
   */
  public static void main(String[] args) throws IOException {
    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    DungeonGameController controller = new DungeonGameController(br, bw);
    controller.run();
  }

}
