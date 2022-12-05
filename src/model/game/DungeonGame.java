package model.game;

import java.util.Map;
import model.Direction;
import model.accessory.Treasure;
import model.creature.Smell;
import model.graph.Coordinate;

/**
 * This interface represents the dungeon game,
 * including the map and the player.
 *
 */
public interface DungeonGame {
  
  /**
   * Tell if the player can move on this direction.
   *
   * @param d potential direction
   * @return true if can move so, false otherwise
   */
  boolean playerCanWalk(Direction d);
  
  /**
   * Let the player move.
   * @param d direction of movement
   * @return the result of the move
   */
  String playerMove(Direction d);
  
  /**
   * Let player pick up arrows.
   * @return result of pickup
   */
  String playerPickArrow();
  
  /**
   * Get the count of arrows of the player.
   * @return the count of arrows
   */
  Integer getArrowCount();
  
  /**
   * Let player pick up the treasures.
   * @return the pickup result
   */
  String playerPickTreasure();
  
  /**
   * Player picks up both treasures and arrows.
   * @return the result of pick up.
   */
  String playerPick();
  
  /**
   * Get the map of treasures.
   * @return map of treasures
   */
  Map<Treasure, Integer> getTreasureMap();
  
  /**
   * Let the player shoot an arrow in the game.
   * @param d arrow direction
   * @param caveCounts number of caves
   * @return description of this shoot
   */
  String shootArrow(Direction d, int caveCounts);
  
  /**
   * Quit the game.
   * @return the game state
   */
  String quit();
  
  /**
   * Get the description of the current position.
   * @return the description string
   */
  String getPositionDes();
  
  /**
   * Get the smell the player now has.
   * @return the smell
   */
  Smell getSmell();
  
  /**
   * Tell if the game is done.
   * @return true if game over
   */
  boolean isOver();
  
  /**
   * Tell if the player is killed.
   *
   * @return true if it's killed
   */
  boolean isKilled();
  
  /**
   * Get player's current position.
   *
   * @return player position
   */
  Coordinate getCoord();
  
  /**
   * Get the string of the game result.
   *
   * @return game result
   */
  String result();
  
  /**
   * Get the description of the player.
   *
   * @return the string of player description
   */
  String playerString();
}
