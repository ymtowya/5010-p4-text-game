package model.dungeon;

import java.util.Set;
import model.Direction;
import model.accessory.ItemHolder;

/**
 * Interface Location represents one unit in the cave.
 *
 */
public interface Location extends ItemHolder {
  
  /**
   * Tell if can walk towards this direction.
   *
   * @param d direction
   * @return true if can walk, false otherwise
   */
  boolean canWalk(Direction d);
  
  /**
   * Set the Direction Set.
   *
   * @param newDirections new directions set
   */
  void setDirectsSet(Set<Direction> newDirections);
  
  /**
   * Add direction to the current set.
   *
   * @param d new direction
   */
  void addNewDirection(Direction d);
  
  /**
   * Remove direction from the set.
   *
   * @param d direction to remove
   */
  void removeDirection(Direction d);
  
  /**
   * Clear All holding directions.
   *
   */
  void clearAllDirections();
  
  /**
   * Get all walkable directions.
   *
   * @return set of directions
   */
  Set<Direction> getAllDirections();
  
  /**
   * Tell if it's a cave or not.
   *
   * @return True if it is cave, false otherwise
   */
  boolean isCave();
}
