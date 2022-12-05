package model.creature;

import model.accessory.Arrow;
import model.accessory.ItemHolder;
import model.graph.Coordinate;

/**
 * Player interface represents the player in the dungeon.
 *
 */
public interface Player extends ItemHolder {
  
  /**
   * Get the player's name.
   *
   * @return name
   */
  String getName();
  
  /**
   * Get the Row player is in.
   *
   * @return row
   */
  int getRow();
  
  /**
   * Get column the player is in.
   *
   * @return column
   */
  int getCol();
  
  /**
   * Get coordinate of the player.
   *
   * @return coordinate
   */
  Coordinate getCoord();
  
  /**
   * Set row.
   *
   * @param newRow new row
   */
  void setRow(int newRow);
  
  /**
   * Set column.
   *
   * @param newCol new column
   */
  void setCol(int newCol);
  
  /**
   * Use up one arrow.
   *
   * @return the used arrow
   */
  Arrow useOneArrow();
  
  /**
   * Tell if it's still alive.
   *
   * @return true if it's alive, false otherwise
   */
  boolean isAlive();
  
  /**
   * Set the life condition of the player.
   *
   * @param l new life condition
   */
  void setLife(boolean l);
}
