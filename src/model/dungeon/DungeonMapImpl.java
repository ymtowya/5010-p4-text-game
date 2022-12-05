package model.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Direction;
import model.accessory.ItemHolder;
import model.accessory.Treasure;
import model.creature.LifeCondition;
import model.creature.Otyugh;
import model.graph.Coordinate;

/**
 * An implementation of DungeonMap.
 *
 */
public class DungeonMapImpl implements DungeonMap {
  
  private List<List<Location>> myDungeon;
  private boolean isWrapped;
  private int innerConnectivity;
  private Coordinate start;
  private Coordinate end;
  private Set<Otyugh> otyughs;
  
  /**
   * Dungeon Map Initializer.
   *
   * @param row row numbers
   * @param col column numbers
   * @param connectivity the connectivity of the graph
   * @param wrapped if is wrapped or not
   */
  public DungeonMapImpl(int row, int col,
      int connectivity, boolean wrapped, Set<Otyugh> newOtyughs) {
    this.myDungeon = new ArrayList<>(row);
    for (int i = 0; i < row; ++i) {
      List<Location> tmpList = new ArrayList<>(col);
      for (int j = 0; j < col; ++j) {
        tmpList.add(new LocationImpl());
      }
      myDungeon.add(tmpList);
    }
    this.innerConnectivity = connectivity;
    this.isWrapped = wrapped;
    this.otyughs = new HashSet<>(newOtyughs);
  }
  
  private void setMap(boolean[][][] adjs) {
    int r = adjs.length;
    if (r < 1) {
      return;
    }
    int c = adjs[0].length;
    // myDungeon.clear();
    for (int i = 0; i < r; ++i) {
      List<Location> locaList = this.myDungeon.get(i);
      for (int j = 0; j < c; ++j) {
        Location location = locaList.get(j);
        location.clearAllDirections();
        if (adjs[i][j][0]) {
          location.addNewDirection(Direction.NORTH);
        }
        if (adjs[i][j][1]) {
          location.addNewDirection(Direction.EAST);
        }
        if (adjs[i][j][2]) {
          location.addNewDirection(Direction.SOUTH);
        }
        if (adjs[i][j][3]) {
          location.addNewDirection(Direction.WEST);
        }
      }
    }
  }
  
  private Location locationAt(Coordinate c) {
    return this.myDungeon.get(c.getRow()).get(c.getCol());
  }

  @Override
  public Map<Treasure, Integer> getTreasuresAt(Coordinate c) {
    return locationAt(c).getTreasures();
  }

  @Override
  public boolean canWalk(Coordinate c, Direction d) {
    return locationAt(c).canWalk(d);
  }

  @Override
  public Set<Direction> getDirectionsAt(Coordinate c) {
    return locationAt(c).getAllDirections();
  }


  @Override
  public int getConnectivity() {
    return this.innerConnectivity;
  }

  @Override
  public boolean isWrapped() {
    return this.isWrapped;
  }

  @Override
  public Coordinate getStart() {
    return new Coordinate(start);
  }

  @Override
  public Coordinate getEnd() {
    return new Coordinate(end);
  }

  @Override
  public int getCaveCount() {
    int count = 0;
    for (List<Location> row : myDungeon) {
      for (Location l : row) {
        if (l.isCave()) {
          ++count;
        }
      }
    }
    return count;
  }

  @Override
  public int getTunnelCount() {
    int count = 0;
    for (List<Location> row : myDungeon) {
      for (Location l : row) {
        if (!l.isCave()) {
          ++count;
        }
      }
    }
    return count;
  }

  @Override
  public void setByAdjMap(boolean[][][] adjs) {
    this.setMap(adjs);
  }

  @Override
  public boolean setTreasures(Map<Coordinate, Map<Treasure, Integer>> trs) {
    if (trs == null) {
      return false;
    }
    for (Coordinate c : trs.keySet()) {
      if (locationAt(c).canHoldTreasureNow()) {
        locationAt(c).setTreasureMap(trs.get(c));
      } else {
        return false;
      }
    }
    return true;
  }

  @Override
  public void setStart(Coordinate s) {
    this.start = new Coordinate(s);
  }

  @Override
  public void setEnd(Coordinate e) {
    this.end = new Coordinate(e);
  }

  @Override
  public int getRows() {
    if (this.myDungeon == null) {
      return 0;
    }
    return this.myDungeon.size();
  }

  @Override
  public int getCols() {
    if (getRows() > 0) {
      return this.myDungeon.get(0).size();
    }
    return 0;
  }

  @Override
  public boolean isCave(Coordinate c) {
    if (c == null || this.myDungeon == null) {
      return false;
    }
    Location l = myDungeon.get(c.getRow()).get(c.getCol());
    return l.isCave();
  }

  @Override
  public void removeTreauseAt(Coordinate c) {
    locationAt(c).setTreasureMap(new HashMap<>());
  }

  @Override
  public Coordinate getNextCoord(Coordinate c, Direction d) {
    return null;
  }

  @Override
  public Direction getTunnelAnotherDirection(Coordinate c, Direction d) {
    Location l = locationAt(c);
    if (!l.isCave()) {
      Set<Direction> ds = l.getAllDirections();
      for (Direction dn : ds) {
        if (!dn.equals(d)) {
          return dn;
        }
      }
    }
    return null;
  }

  @Override
  public Set<Otyugh> getOtyughs() {
    return new HashSet<>(this.otyughs);
  }

  @Override
  public boolean hasOtyughAt(Coordinate c) {
    for (Otyugh o : otyughs) {
      if (c.equals(o.getCoord()) &&
          o.getLifeCondition() != LifeCondition.DEAD) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Otyugh getOtyughAt(Coordinate c) {
    for (Otyugh o : otyughs) {
      if (c.equals(o.getCoord())) {
        return o;
      }
    }
    return null;
  }

  @Override
  public ItemHolder getHolderAt(Coordinate c) {
    return this.locationAt(c);
  }

}
