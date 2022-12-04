package model.game;

import java.util.Set;
import model.Direction;
import model.creature.Otyugh;
import model.creature.Player;
import model.creature.Smell;
import model.dungeon.DungeonMap;
import model.graph.Coordinate;

/**
 * This interface represents a calculator for the dungeon.
 * It usually takes in a dungeon map (sometimes with a Player)
 * to conduct some actions and change the model.
 * This is supposed to be the only interface that can change
 * the model. You are not expected to change the model with methods
 * in other interfaces.
 *
 */
public interface GameCalculator {
  
  /**
   * Initialize a DungeonMap Model with parameters.
   *
   * @param row number of rows
   * @param col number of columns
   * @param conn connectivity
   * @param isWrap is wrapped or not
   * @param treasureProb percentage of caves with treasure
   * @return a new set Dungeon Map
   */
  DungeonMap initGame(int row, int col, int conn,
      boolean isWrap, double treasureProb, int otyughs);
  
  /**
   * Make the player enter the game.
   *
   * @param m the dungeon map
   * @param p the player
   */
  void enterGame(DungeonMap m, Player p);
  
  /**
   * Walk the player in the map.
   *
   * @param m map
   * @param p player
   * @param d direction
   * @return true if walkable, false if not
   */
  boolean walkPlayer(DungeonMap m, Player p, Direction d);
  
  /**
   * Make the player pick up the treasures.
   *
   * @param m dungeon map
   * @param p player
   * @return true if success, false otherwise
   */
  boolean pickTreasure(DungeonMap m, Player p);
  
  /**
   * Make the player pick up the arrows.
   *
   * @param m dungeon map
   * @param p player
   * @return true if success, false otherwise
   */
  boolean pickArrow(DungeonMap m, Player p);
  
  /**
   * Get the description string of the dungeon map.
   *
   * @param m map
   * @param p player
   * @return string of description
   */
  String getMapString(DungeonMap m, Player p);
  
  /**
   * Get the player's description.
   *
   * @param p player
   * @return description string
   */
  String getPlayerString(Player p);
  
  /**
   * Get the description string for the location.
   *
   * @param m map
   * @param c coordinate
   * @return description string
   */
  String getLocationString(DungeonMap m, Coordinate c);
  
  /**
   * Tell if the player is at end of the map.
   *
   * @param m map
   * @param p player
   * @return true if it's at end, false otherwise
   */
  boolean isAtEnd(DungeonMap m, Player p);
  
  /**
   * Get set of walkable directions.
   *
   * @param m map
   * @param p player
   * @return set of directions
   */
  Set<Direction> walkableDirs(DungeonMap m, Player p);
  
  void shootArrow(DungeonMap m, Player p, Direction d, int caveNums);
  
  Smell getSmellAt(DungeonMap m, Player p);
  
  Set<Otyugh> getOtyughs(int num);
}
