package model.dungeon;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Direction;
import model.accessory.Arrow;
import model.accessory.Treasure;

/**
 * An implementation of the Location interface.
 *
 */
public class LocationImpl implements Location {
  
  private Set<Direction> directions;
  private Map<Treasure, Integer> treasures;
  private Deque<Arrow> arrows;
  
  /**
   * Initialize with empty parameters.
   *
   */
  public LocationImpl() {
    this.directions = new HashSet<>();
    this.treasures = new HashMap<>();
  }
  
  /**
   * Initialize with parameters.
   *
   * @param newDirections directions walkable
   * @param newTreasures treasures in the location
   */
  public LocationImpl(Set<Direction> newDirections,
      Map<Treasure, Integer> newTreasures) {
    this.directions = new HashSet<>();
    this.treasures = new HashMap<>();
    this.directions.addAll(newDirections);
    this.treasures.putAll(newTreasures);
  }

  @Override
  public boolean canWalk(Direction d) {
    return this.directions.contains(d);
  }

  @Override
  public void setDirectsSet(Set<Direction> newDirections) {
    this.directions = new HashSet<>(newDirections);
  }

  @Override
  public void addNewDirection(Direction d) {
    this.directions.add(d);
  }

  @Override
  public void removeDirection(Direction d) {
    this.directions.remove(d);
  }

  @Override
  public void clearAllDirections() {
    this.directions.clear();
  }

  @Override
  public void setTreasureMap(Map<Treasure, Integer> newTreasures) {
    if (isCave()) {
      this.treasures = new HashMap<>(newTreasures);
    }
  }

  @Override
  public Map<Treasure, Integer> getTreasures() {
    return new HashMap<>(this.treasures);
  }

  @Override
  public int getTotalTreasure() {
    int count = 0;
    for (int tmp : this.treasures.values()) {
      count += tmp;
    }
    return count;
  }

  @Override
  public boolean canHoldTreasureNow() {
    return this.isCave();
  }

  @Override
  public Set<Direction> getAllDirections() {
    return new HashSet<Direction>(this.directions);
  }

  @Override
  public boolean isCave() {
    return this.directions.size() != 2;
  }

  @Override
  public void addTreasures(Map<Treasure, Integer> newTreasures) {
    for (Treasure t : newTreasures.keySet()) {
      if (this.treasures.containsKey(t)) {
        final int tmp = treasures.get(t);
        treasures.put(t, tmp + newTreasures.get(t));
      } else {
        treasures.put(t, newTreasures.get(t));
      }
    }
  }

  @Override
  public boolean hasArrow() {
    return !this.arrows.isEmpty();
  }

  @Override
  public void clearArrow() {
    this.arrows.clear();
  }

  @Override
  public void addArrow(Deque<Arrow> a) {
    this.arrows.addAll(a);
  }

  @Override
  public int arrowCount() {
    return this.arrows.size();
  }

  @Override
  public Deque<Arrow> getArrows() {
    return this.arrows;
  }

}
