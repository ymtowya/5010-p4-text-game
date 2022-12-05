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
  
  /**
   * Tell if the holder still has arrow.
   *
   * @return true if still has arrow, false otherwise
   */
  boolean hasArrow();
  
  /**
   * Clear all of the possessed arrows.
   *
   */
  void clearArrow();
  
  /**
   * Add arrows to possession.
   *
   * @param a arrows to be added
   */
  void addArrow(Deque<Arrow> a);
  
  /**
   * Get total count of arrows.
   *
   * @return count of arrows
   */
  int arrowCount();
  
  /**
   * Get arrows in a queue.
   *
   * @return queue of arrows
   */
  Deque<Arrow> getArrows();
}
