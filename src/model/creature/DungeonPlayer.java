package model.creature;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.accessory.Arrow;
import model.accessory.DungeonArrow;
import model.accessory.Treasure;
import model.graph.Coordinate;

/**
 * An implementation of the player in the dungeon.
 *
 *
 */
public class DungeonPlayer implements Player {
  
  private int row;
  private int col;
  private boolean isLive;
  private String name;
  private Map<Treasure, Integer> treasures;
  private Deque<Arrow> arrows;
  
  /**
   * Initialize the player.
   *
   * @param r row
   * @param c column
   * @param n name
   */
  public DungeonPlayer(int r, int c, String n) {
    this.row = r;
    this.col = c;
    this.isLive = true;
    this.name = n;
    this.treasures = new HashMap<>();
    for (Treasure t : Treasure.values()) {
      this.treasures.put(t, 0);
    }
    this.arrows = new LinkedList<>();
    for (int i = 0; i < 3; ++i) {
      arrows.add(new DungeonArrow());
    }
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public void setRow(int newRow) {
    this.row = newRow;
  }

  @Override
  public void setCol(int newCol) {
    this.col = newCol;
  }

  @Override
  public void setTreasureMap(Map<Treasure, Integer> newTreasures) {
    this.treasures = new HashMap<>();
    this.treasures.putAll(newTreasures);
  }

  @Override
  public Map<Treasure, Integer> getTreasures() {
    Map<Treasure, Integer> resMap = new HashMap<>();
    resMap.putAll(this.treasures);
    return resMap;
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
    return true;
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
  public Coordinate getCoord() {
    return new Coordinate(row, col);
  }
  
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean hasArrow() {
    return !arrows.isEmpty();
  }

  @Override
  public Arrow useOneArrow() {
    Arrow a = arrows.poll();
    return a;
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
  public boolean isAlive() {
    return this.isLive;
  }

  @Override
  public void setLife(boolean l) {
    this.isLive = l;
  }

  @Override
  public void clearArrow() {
    this.arrows.clear();
  }

  @Override
  public Deque<Arrow> getArrows() {
    return this.arrows;
  }

}
