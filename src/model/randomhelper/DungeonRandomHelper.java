package model.randomhelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import model.Direction;
import model.accessory.Treasure;
import model.graph.Coordinate;


/**
 * DungeonRandomHelper is one helper to give random values to support the game.
 *
 *
 */
public class DungeonRandomHelper implements RandomHelper {
  
  Random rand;
  
  /**
   * Initialize the helper with seed.
   *
   * @param seed the seed
   */
  public DungeonRandomHelper(int seed) {
    rand = new Random(seed);
  }

  @Override
  public int randomInt(int left, int right) {
    return rand.nextInt(right - left + 1) + left;
  }

  @Override
  public List<Integer> randDivideVal(Integer total, int parts) {
    final int unit = Math.floorDiv(total, parts);
    final int left = total - unit * (parts - 1);
    List<Integer> resultList = new ArrayList<>(parts);
    for (int i = 0; i < parts; ++i) {
      resultList.add(unit);
    }
    resultList.set(randomInt(0, parts - 1), left);
    return resultList;
  }

  @Override
  public Coordinate randomCoord(int maxRow, int maxCol) {
    int r = randomInt(0, maxRow - 1);
    int c = randomInt(0, maxCol - 1);
    return new Coordinate(r, c);
  }

  @Override
  public Coordinate coordChoice(Collection<Coordinate> coors) {
    int index = this.randomInt(0, coors.size() - 1);
    Iterator<Coordinate> iter = coors.iterator();
    for (int i = 0; i < index; i++) {
      iter.next();
    }
    return iter.next();
  }

  @Override
  public Coordinate randomCave(boolean[][][] adj) {
    int r = adj.length;
    if (r < 1) {
      return null;
    }
    int c = adj[0].length;
    List<Integer> caveXs = new ArrayList<>();
    List<Integer> caveYs = new ArrayList<>();
    for (int i = 0; i < r; ++i) {
      for (int j = 0; j < c; ++j) {
        int tmp = 0;
        for (int d = 0; d < 4; ++d) {
          if (adj[i][j][d]) {
            ++tmp;
          }
        }
        if (tmp != 2) {
          caveXs.add(i);
          caveYs.add(j);
        }
      }
    }
    
    if (caveXs.isEmpty()) {
      return null;
    }
    
    final int index = randomInt(0, caveXs.size() - 1);
    
    return new Coordinate(caveXs.get(index), caveYs.get(index));
  }

  @Override
  public Map<Treasure, Integer> treasureChoices(int maxNum) {
    Map<Treasure, Integer> resMap = new HashMap<>();
    for (Treasure t : Treasure.values()) {
      resMap.put(t, randomInt(1, maxNum));
    }
    return resMap;
  }

  @Override
  public Direction directionChoice(Set<Direction> ds) {
    int index = this.randomInt(0, ds.size() - 1);
    Iterator<Direction> iter = ds.iterator();
    for (int i = 0; i < index; i++) {
      iter.next();
    }
    return iter.next();
  }

  @Override
  public boolean escape() {
    return this.randomInt(0, 1) == 0;
  }

}
