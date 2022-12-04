package model.accessory;

import java.util.Deque;
import java.util.Map;

/**
 * This interface represents those who can hold treasures.
 *
 */
public interface ItemHolder {
  
  /**
   * Tell if can hold treasures.
   *
   * @return true if can hold, false otherwise
   */
  boolean canHoldTreasureNow();
  
  /**
   * Set the treasure map with new map.
   *
   * @param newTreasures new treasure map
   */
  void setTreasureMap(Map<Treasure, Integer> newTreasures);
  
  /**
   * Add new treasures to the map.
   *
   * @param newTreasures new treasures to be added
   */
  void addTreasures(Map<Treasure, Integer> newTreasures);
  
  /**
   * Get the treasure map copy.
   *
   * @return Copy of the treasure map
   */
  Map<Treasure, Integer> getTreasures();
  
  /**
   * Get total amount of treasures.
   *
   * @return amount of treasures
   */
  int getTotalTreasure();
  
  boolean hasArrow();
  
  void clearArrow();
  
  void addArrow(Deque<Arrow> a);
  
  int arrowCount();
  
  Deque<Arrow> getArrows();
}
