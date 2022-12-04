package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Direction;
import model.accessory.Treasure;
import model.creature.Smell;
import model.game.DungeonGame;
import model.game.DungeonGameImpl;

public class DungeonGameController {
  
  static private Set<String> dirStrs;
  static private Set<String> pickStrs;
  static {
    dirStrs = new HashSet<>(Arrays.asList("e", "s", "w", "n"));
    pickStrs = new HashSet<>(Arrays.asList("pt", "pa"));
  }
  
  private DungeonGame game;
  private int r;
  private int c; 
  private int conn;
  private boolean isWrap;
  private String playerName;
  private double percent;
  private int otyughNum;
  private BufferedReader reader;
  private BufferedWriter bw;
  
  private int getInt(String s) {
    return Integer.parseInt(s);
  }
  
  private void printOut(String s) throws IOException {
    bw.append(s);
    bw.flush();
  }
  
  private void gameSet() {
    try {
      while (true) {
        printOut("The number of rows for the dungeon (5 - 20) :");
        String input = reader.readLine();
        r = getInt(input);
        if (r >= 5 && r <= 20) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
      while (true) {
        printOut("The number of columns for the dungeon (5 - 20) :");
        String input = reader.readLine();
        c = getInt(input);
        if (c >= 5 && c <= 20) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
      while (true) {
        printOut("The connectivity of the dungeon (0 - 10) :");
        String input = reader.readLine();
        conn = getInt(input);
        if (conn >= 0 && conn <= 10) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
      while (true) {
        printOut("Is the dungeon wrapped or not (y/f) :");
        String input = reader.readLine();
        if ("y".equals(input) || "f".equals(input)) {
          isWrap = "y".equals(input);
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
      while (true) {
        printOut("Percentage(%) of caves with treasures (0 - 100) :");
        String input = reader.readLine();
        int inNum = getInt(input);
        if (inNum >= 0 && inNum <= 100) {
          percent = (double) inNum / 100;
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
      while (true) {
        printOut("The name of the player :");
        playerName = reader.readLine();
        if (!"".equals(playerName)) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
      while (true) {
        printOut("The number of otyughs (1 - 10) :");
        String input = reader.readLine();
        otyughNum = getInt(input);
        if (otyughNum >= 0 && otyughNum <= 10) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }
  
  public Boolean run(BufferedReader reader, BufferedWriter bw) {
    Boolean goon = true;
    String input;
    try {
      printOut("Welcome to the dungeon game!\n" +
          "Please input key parameters for the game:\n\n");
      while(goon) {
        input = reader.readLine();
        if (input == null) {
          return false;
        }
        bw.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }
  
  private void init(int r, int c, int conn, boolean isWrap,
      String playerName, double percent, int otyughNum) {
    game = new DungeonGameImpl(r, c, conn, isWrap, playerName, percent, otyughNum);
  }
  
  private boolean isDirStr(String s) {
    return dirStrs.contains(s);
  }
  
  private boolean isPickStr(String s) {
    return pickStrs.contains(s);
  }
  
  private boolean isShootStr(String s) {
    return "a".equals(s);
  }
  
  private boolean isQuitStr(String s) {
    return "q".equals(s);
  }
  
  void dealInput(String s) {
    if (isDirStr(s)) {
      // move
      Direction d;
      if (game.playerCanWalk(d)) {
        game.playerMove(d);
      } else {
        
      }
    } else if (isPickStr(s)) {
      // pick
      if ("pa".equals(s)) {
        game.playerPickArrow();
        int arrCount = game.getArrowCount();
      } else {
        game.playerPickTreasure();
        Map<Treasure, Integer> trs = game.getTreasureMap();
      }
    } else if (isShootStr(s)) {
      // shoot
      Direction d;
      int caveCounts;
      game.shootArrow(d, caveCounts);
    } else if (isQuitStr(s)) {
      // quit
      game.quit();
    } else {
      // exception
    }
    String state = game.getPositionDes();
    Smell smell = game.getSmell();
  }
}
