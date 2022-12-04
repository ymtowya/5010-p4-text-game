package model.creature;

import model.graph.Coordinate;

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
  
  LifeCondition getLifeCondition();
  
  void setLifeCondition(LifeCondition newlc);
  
  void beHurt();
}
