package model.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import model.Direction;
import model.creature.LifeCondition;
import model.creature.Otyugh;
import model.creature.Smell;
import model.randomhelper.RandomHelper;

/**
 * Implement the Grid Generator with Kruskal Algorithm.
 *
 */
public class KruskalGridGenerator implements GridGenerator {

  private int row;
  private int col;
  private int connectivity;
  private boolean isWrapped;
  private boolean[][][] grid;
  private boolean[][][] adj;
  private int[][] stepRec;
  private int[][] smells;
  private Coordinate start;
  private Coordinate end;
  private RandomHelper rh;
  
  /**
   * Initialize with random helper.
   *
   * @param newRh random helper
   */
  public KruskalGridGenerator(RandomHelper newRh) {
    this.row = 5;
    this.col = 7;
    this.connectivity = 0;
    this.isWrapped = true;
    this.start = new Coordinate(2, 3);
    this.end = new Coordinate(0, 1);
    this.rh = newRh;
  }
  
  private boolean setNewConnectivity() {
    int freeEdges = 0;
    Set<Coordinate> frees = new HashSet<>();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        Coordinate tmpCoord = new Coordinate(i, j);
        int tmpAdjDegree = getAdjFreeDegree(tmpCoord);
        int tmpGridDegree = getGridFreeDegree(tmpCoord);
        freeEdges += tmpGridDegree - tmpAdjDegree;
        if (tmpAdjDegree < tmpGridDegree) {
          frees.add(tmpCoord);
        }
      }
    }
    // not enough edges
    if (freeEdges < this.connectivity * 2) {
      return false;
    }
    
    int leftConn = this.connectivity;
    while (leftConn > 0) {
      Coordinate curr = rh.coordChoice(frees);
      final int startDir = rh.randomInt(0, 3);
      for (int i = 0; i < 4; ++i) {
        if (!canWalkAdj(curr, startDir + i) && canWalkGrid(curr, startDir + i)) {
          
          setAdjWalk(curr, startDir + i);
          leftConn--;
          if (getAdjFreeDegree(curr) >= getGridFreeDegree(curr)) {
            frees.remove(curr);
          }
          Coordinate relevant = getNextCoor(curr, startDir + i);
          if (getAdjFreeDegree(relevant) >= getGridFreeDegree(relevant)) {
            frees.remove(relevant);
          }
          break;
        }
      }
    }
    return true;
  }
  
  
  private void setStepRecHelper(Coordinate c, int step) {
    this.stepRec[c.row][c.col] = step;
  }
  
  private int getAdjFreeDegree(Coordinate c) {
    int res = 0;
    for (int d = 0; d < 4; d++) {
      if (canWalkAdj(c, d)) {
        ++res;
      }
    }
    return res;
  }
  
  private int getGridFreeDegree(Coordinate c) {
    int res = 0;
    for (int d = 0; d < 4; d++) {
      if (canWalkGrid(c, d)) {
        ++res;
      }
    }
    return res;
  }
  
  
  private int getOppDir(int d) {
    return (d + 2) % 4;
  }
  
  private int modRow(Coordinate c) {
    return (c.getRow() + row) % row;
  }
  
  private int modCol(Coordinate c) {
    return (c.getCol() + col) % col;
  }
  
  private boolean canWalkGrid(Coordinate c, int d) {
    return grid[modRow(c)][modCol(c)][d % 4];
  }
  
  private boolean canWalkAdj(Coordinate c, int d) {
    return this.adj[modRow(c)][modCol(c)][d % 4];
  }
  
  private Coordinate getNextCoor(Coordinate c, int d) {
    int r0 = c.getRow();
    int c0 = c.getCol();
    switch (d % 4) {
      case 0:
        r0 = (r0 - 1 + row) % row;
        break;
      case 1:
        c0 = (c0 + 1) % col;
        break;
      case 2:
        r0 = (r0 + 1) % row;
        break;
      case 3:
        c0 = (c0 - 1 + col) % col;
        break;
      default:
        break;
    }
    return new Coordinate(r0, c0);
  }
  
  private boolean setAdjWalk(Coordinate c, int d) {
    if (!canWalkGrid(c, d)) {
      return false;
    }
    adj[modRow(c)][modCol(c)][d % 4] = true;
    Coordinate newCoor = getNextCoor(c, d);
    adj[modRow(newCoor)][modCol(newCoor)][getOppDir(d)] = true;
    return true;
  }
  
  private void initGrid() {
    this.grid = new boolean[row][col][4];
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        for (int d = 0; d < 4; ++d) {
          grid[i][j][d] = true; 
        }
      }
    }
    if (!isWrapped) {
      for (int j = 0; j < col; ++j) {
        grid[0][j][0] = false;
        grid[row - 1][j][2] = false;
      }
      for (int i = 0; i < row; ++i) {
        grid[i][0][3] = false;
        grid[i][col - 1][1] = false;
      }
    }
  }
  
  private void geneMst() {
    // init vSet & adjVSet
    Set<Coordinate> connected = new HashSet<>();
    Set<Coordinate> adjv = new HashSet<>();
    connected.add(this.start);
    // init adj
    this.adj = new boolean[row][col][4];
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        for (int d = 0; d < 4; ++d) {
          adj[i][j][d] = false; 
        }
      }
    }
    for (int d = 0; d < 4; ++d) {
      // setAdjWalk(this.start, d);
      if (canWalkGrid(this.start, d)) {
        adjv.add(getNextCoor(this.start, d));
      }
    }
    // loop
    while (connected.size() < row * col) {
      Coordinate curr = rh.coordChoice(adjv);
      int d = this.rh.randomInt(0, 3);
      for (int i = d; i < d + 4; ++i) {
        if (connected.contains(getNextCoor(curr, i))
            && canWalkGrid(curr, i)) {
          if (setAdjWalk(curr, i)) {
            connected.add(curr);
            adjv.remove(curr);
            break;
          }
        }
      }
      for (int i = 0; i < 4; ++i) {
        Coordinate tmp = getNextCoor(curr, i);
        if (!(connected.contains(tmp))
            && !(adjv.contains(tmp))
            && canWalkGrid(curr, i)) {
          adjv.add(tmp);
        }
      }
    }
  }
  
  
  @Override
  public int[][] getStepRecords(Coordinate s) {
    this.stepRec = new int[row][col];
    Queue<Coordinate> thisLayer = new ArrayDeque<>();
    Queue<Coordinate> nextLayer = new ArrayDeque<>();
    Set<Coordinate> visited = new HashSet<>();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        stepRec[i][j] = -1; 
      }
    }
    thisLayer.add(s);
    int currStep = 0;
    while (!thisLayer.isEmpty()) {
      while (!thisLayer.isEmpty()) {
        Coordinate c = thisLayer.poll();
        // set steps
        setStepRecHelper(c, currStep);
        visited.add(c);
        // add children
        for (int d = 0; d < 4; d++) {
          if (canWalkAdj(c, d)) {
            Coordinate nextChild = getNextCoor(c, d);
            if (!visited.contains(nextChild)) {
              nextLayer.add(nextChild);
            }
          }
        }
      }
      // incre
      ++currStep;
      // set Next Layer
      while (!nextLayer.isEmpty()) {
        thisLayer.add(nextLayer.poll());
      }
    }
    return this.stepRec;
  }
  
  @Override
  public String getStepRecordString(Coordinate s) {
    this.getStepRecords(s);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        sb.append(stepRec[i][j]);
        sb.append(' ');
      }
      sb.append('\n');
    }
    sb.append("\n\nSmells:\n");
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        sb.append(smells[i][j]);
        sb.append(' ');
      }
      sb.append('\n');
    }
    return sb.toString();
  }
  
  @Override
  public void setSize(int r, int c) {
    this.row = r;
    this.col = c;
  }

  @Override
  public void setConnectivity(int c) {
    this.connectivity = c;
  }

  @Override
  public void setIsWrapped(boolean i) {
    this.isWrapped = i;
  }

  @Override
  public void geneGrid() {
    // based on inner connectivity & isWrapped
    // have representation
    // 0, 1, 2, 3 - N, E, S, W
    this.initGrid();
    // get MST
    this.geneMst();
    // add to connectivity
    this.setNewConnectivity();
    this.smells = new int[row][col];
  }

  @Override
  public boolean[][][] getPlainGrid() {
    boolean[][][] gridRes = new boolean[row][col][4];
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        for (int d = 0; d < 4; ++d) {
          gridRes[i][j][d] = adj[i][j][d]; 
        }
      }
    }
    return gridRes;
  }

  @Override
  public void setStart(Coordinate newStart) {
    this.start = new Coordinate(newStart);
  }

  @Override
  public void setEnd(Coordinate newEnd) {
    this.end = new Coordinate(newEnd);
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
  public String getAdjStr() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        if (adj[i][j][0]) {
          sb.append(" | ");
        } else {
          sb.append("   ");
        }
      }
      sb.append('\n');
      for (int j = 0; j < col; ++j) {
        if (adj[i][j][3]) {
          sb.append("-O");
        } else {
          sb.append(" O");
        }
        if (adj[i][j][1]) {
          sb.append("-");
        } else {
          sb.append(" ");
        }
      }
      sb.append('\n');
      for (int j = 0; j < col; ++j) {
        if (adj[i][j][2]) {
          sb.append(" | ");
        } else {
          sb.append("   ");
        }
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  @Override
  public boolean setRandomStart() {
    this.start = new Coordinate(rh.randomInt(0, row - 1),
        rh.randomInt(0, col - 1));
    return true;
  }
  
  private boolean isCave(Coordinate c) {
    return getAdjFreeDegree(c) != 2;
  }
  
  @Override
  public boolean setRandomEnd(int steps) {
    List<Integer> coorXs = new ArrayList<>();
    List<Integer> coorYs = new ArrayList<>();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        Coordinate coor = new Coordinate(i, j);
        if (isCave(coor)
            && stepRec[i][j] >= steps) {
          coorXs.add(i);
          coorYs.add(j);
        }
      }
    }
    if (coorXs.isEmpty()) {
      return false;
    }
    
    final int tmpIndex = rh.randomInt(0, coorXs.size() - 1);
    this.end = new Coordinate(coorXs.get(tmpIndex), coorYs.get(tmpIndex));
    return true;
  }

  @Override
  public Set<Coordinate> geneRandomCaves(double prob) {
    List<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        Coordinate coor = new Coordinate(i, j);
        if (isCave(coor)) {
          list.add(coor);
        }
      }
    }
    final int nums = (int) Math.ceil(prob * list.size());
    Collections.shuffle(list);
    
    return new HashSet<>(list.subList(0, nums));
  }

  @Override
  public Smell getSmellAt(Coordinate c) {
    int v = smells[c.getRow()][c.getCol()];
    if (v == 0) {
      return Smell.NONE;
    } else if (v == 1) {
      return Smell.NORMAL;
    }
    return Smell.STRONG;
  }

  @Override
  public int[][] getSmellGrid() {
    return smells;
  }
  
  private boolean firstLayerCheck(int i, int j, Set<Coordinate> otyughLocs,
      Set<Coordinate> firstLayer) {
    Coordinate c = new Coordinate(i, j);
    smells[i][j] = 0; 
    // check first layer
    for (int d = 0; d < 4; ++d) {
      if (canWalkAdj(c, d)) {
        firstLayer.add(getNextCoor(c, d));
      }
    }
    firstLayer.add(c);
    for (Coordinate loc : firstLayer) {
      if (otyughLocs.contains(loc)) {
        smells[i][j] = 2;
        firstLayer.remove(c);
        return true;
      }
    }
    firstLayer.remove(c);
    return false;
  }
  
  private void secondLayerCheck(int i, int j, Set<Coordinate> otyughLocs,
      Set<Coordinate> firstLayer) {
    Set<Coordinate> secondLayer = new HashSet<>();
    for (Coordinate flc : firstLayer) {
      for (int d = 0; d < 4; ++d) {
        if (canWalkAdj(flc, d)) {
          Coordinate next = getNextCoor(flc, d);
          if (!firstLayer.contains(next)) {
            secondLayer.add(getNextCoor(flc, d));
          }
        }
      }
    }
    int countOtyugh = 0;
    for (Coordinate slc: secondLayer) {
      if (otyughLocs.contains(slc)) {
        countOtyugh++;
      }
    }
    smells[i][j]= countOtyugh; 
  }

  @Override
  public void updateSmellGrid(Set<Otyugh> otyughs) {
    Set<Coordinate> otyughLocs = new HashSet<>();
    for (Otyugh o : otyughs) {
      if (o.getLifeCondition() != LifeCondition.DEAD) {
        otyughLocs.add(o.getCoord());
      }
    }
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        smells[i][j] = 0;
        Set<Coordinate> firstLayer = new HashSet<>();
        if (!firstLayerCheck(i, j, otyughLocs, firstLayer)) {
          secondLayerCheck(i, j, otyughLocs, firstLayer);
        }
      }
    }
  }

  @Override
  public Set<Coordinate> geneRandomCavesByNum(int nums) {
    List<Coordinate> list = new ArrayList<>();
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        Coordinate coor = new Coordinate(i, j);
        if (isCave(coor) && !coor.equals(end)) {
          list.add(coor);
        }
      }
    }
    Collections.shuffle(list);
    Set<Coordinate> res = new HashSet<>(list.subList(0, nums - 1));
    res.add(end);
    // TODO
    assert(res.size() == nums);
    return res;
  }

  @Override
  public Set<Coordinate> geneRandomLocs(int nums) {
    Set<Coordinate> res = new HashSet<>();
    for (int i = 0; i < nums; ++i) {
      int r = rh.randomInt(0, row - 1);
      int c = rh.randomInt(0, col - 1);
      res.add(new Coordinate(r, c));
    }
    return res;
  }

}
