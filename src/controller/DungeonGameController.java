package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import model.Direction;
import model.game.DungeonGame;
import model.game.DungeonGameImpl;

/**
 * This represents the controller for the Dungeon Game.
 *
 *
 */
public class DungeonGameController {
  
  private DungeonGame game;
  private int row;
  private int col; 
  private int conn;
  private boolean isWrap;
  private String playerName;
  private double percent;
  private int otyughNum;
  private BufferedReader reader;
  private BufferedWriter writer;
 
  /**
   * Initialize the controller with reader and writer.
   *
   * @param r reader
   * @param w writer
   */
  public DungeonGameController(BufferedReader r, BufferedWriter w) {
    this.reader = r;
    this.writer = w;
  }
  
  private void printOut(String s) throws IOException {
    writer.append(s);
    writer.flush();
  }
  
  private boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
  
  private int getInt(int mi, int ma, String hint) {
    int tmp = mi;
    while (true) {
      try {
        printOut(hint);
        String in = reader.readLine();
        if (!isNumeric(in)) {
          continue;
        }
        tmp = Integer.parseInt(in);
        if (tmp <= ma && tmp >= mi) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (NumberFormatException e) {
        continue;
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    return tmp;
  }
  
  private String getStr(Set<String> ables, String hint) {
    String tmp = "";
    while (true) {
      try {
        printOut(hint);
        tmp = reader.readLine().toLowerCase();
        if (ables == null || ables.contains(tmp)) {
          break;
        } else {
          printOut("Invalid input. Please try again.\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return tmp;
  }
  
  private Direction getDir() {
    String tmp = getStr(Set.of(new String[] {"n", "e", "s", "w"}),
        "Please input the Direction (n/e/s/w) : ");
    return Direction.ofString(tmp);
  }

  private void gameSet() {
    try {
      printOut("Welcome to the dungeon game!\n"
          + "Please input key parameters for the game:\n\n");
      this.row = getInt(5, 20, "The number of rows for the dungeon (5 - 20) :");
      this.col = getInt(5, 20, "The number of columns for the dungeon (5 - 20) :");
      this.conn = getInt(0, 8, "The connectivity of the dungeon (0 - 8) :");
      this.isWrap = "y".equals(getStr(Set.of(new String[] {"y", "n"}),
          "Is the dungeon wrapped or not (y/n) :"));
      this.percent = (double) getInt(0, 100,
          "Percentage(%) of caves with treasures (0 - 100) :") * 0.01;
      this.playerName = getStr(null, "The name of the player :");
      this.otyughNum = getInt(1, 10, "The number of otyughs (1 - 10) :");
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }
    this.init();
  }
  
  /**
   * This function lets the controller start running,
   * while receiving input and giving output.
   *
   */
  public void run() {
    Boolean goon = true;
    try {
      gameSet();
      while (goon) {
        try {
          String descPos = game.getPositionDes();
          printOut(descPos);
          String cmd = getStr(Set.of(new String[] {"m", "p", "s", "q"}),
              "Move, Pickup, Shoot, or Quit (m-p-s-q)? :");
          if ("m".equals(cmd)) {
            Direction d = getDir();
            if (game.playerCanWalk(d)) {
              descPos = game.playerMove(d);
            } else {
              descPos = "Sorry, you cannot walk for this direction.\n";
            }
          } else if ("p".equals(cmd)) {
            descPos = game.playerPick();
          } else if ("q".equals(cmd)) {
            descPos = game.quit();
          } else if ("s".equals(cmd)) {
            Direction d = getDir();
            int caveCounts = getInt(1, 5, "The number of caves (1 - 5) :");
            descPos = game.shootArrow(d, caveCounts);
          }
          printOut(descPos);
          goon = !game.isOver();
        } catch (IllegalArgumentException e) {
          printOut("Your action was invalid, we'll ignore & continue anyway.\n");
          continue;
        }
      }
      printOut(game.result());
      printOut(game.playerString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void init() {
    game = new DungeonGameImpl(row, col, conn, isWrap, playerName, percent, otyughNum);
  }

}
