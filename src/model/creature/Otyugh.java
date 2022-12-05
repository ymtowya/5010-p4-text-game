package model.creature;

import model.graph.Coordinate;

/**
 * This interface represents the otyugh in the dungeon.
 *
 *
 */
public interface Otyugh {
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
   * Set the position by the given coordinate.
   *
   * @param coor the new position in coordinate
   */
  void setByCoord(Coordinate coor);
  
  /**
   * Get the current life condition of the otyugh.
   *
   * @return the current life condition
   */
  LifeCondition getLifeCondition();
  
  /**
   * Set the otyugh's life condition to the new value.
   *
   * @param newlc new life condition
   */
  void setLifeCondition(LifeCondition newlc);
  
  /**
   * Deal with the Otyugh been hurt.
   *
   */
  void beHurt();
}
