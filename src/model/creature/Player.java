package model.creature;

import java.util.List;

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
  
  Arrow useOneArrow();
  
  boolean isAlive();
  
  void setLife(boolean l);
}
