package model.graph;

import java.util.Set;
import model.creature.Otyugh;
import model.creature.Smell;

/**
 * This interface generates a fundamental representation
 * of the map. Mostly of multiple dimension arrays.
 *
 */
public interface GridGenerator {
  
  /**
   * Set grid size.
   *
   * @param r row
   * @param c column
   */
  void setSize(int r, int c);
  
  /**
   * Set connectivity.
   *
   * @param c connectivity
   */
  void setConnectivity(int c);
  
  /**
   * Set isWrapped.
   *
   * @param i isWrapped or not
   */
  void setIsWrapped(boolean i);
  
  /**
   * Set Start.
   *
   * @param newStart new start
   */
  void setStart(Coordinate newStart);
  
  /**
   * Set End.
   *
   * @param newEnd new end
   */
  void setEnd(Coordinate newEnd);
  
  /**
   * Set a random start.
   *
   * @return true if success, false otherwise
   */
  boolean setRandomStart();
  
  /**
   * Get start.
   *
   * @return start
   */
  Coordinate getStart();
  
  /**
   * Get end.
   *
   * @return end
   */
  Coordinate getEnd();
  
  /**
   * Get Plain grid of the map, where [i][j][k] denotes
   * if the i-th row, j-th column unit has walkable direction
   * denoted by k (0-Up, 1-Right, 2-DOWN, 3-LEFT).
   *
   * @return the grid array.
   */
  boolean[][][] getPlainGrid();
  
  /**
   * Generate the grid array.
   *
   */
  void geneGrid();
  
  /**
   * Generate the step records array where [i][j]
   * denotes how many steps needed to go from
   * start to the i-th row, j-th column position.
   *
   * @param s coordinate of start
   * @return the step array
   */
  int[][] getStepRecords(Coordinate s);
  
  /**
   * Get the String of the Step Record Array.
   *
   * @param s start coord
   * @return string of steps
   */
  String getStepRecordString(Coordinate s);
  
  /**
   * Get the String of the adj arrays.
   *
   * @return string of adj arrays
   */
  String getAdjStr();
  
  /**
   * Set the random ending point.
   *
   * @param steps minimum steps from start to end
   * @return true if we have such an end available
   */
  boolean setRandomEnd(int steps);
  
  /**
   * Generate a set of random caves with prob percentage.
   *
   * @param prob percentage
   * @return the set of caves coordinates
   */
  Set<Coordinate> geneRandomCaves(double prob);
  
  /**
   * Generate a set of random caves by given number.
   *
   * @param nums number of caves
   * @return set of caves
   */
  Set<Coordinate> geneRandomCavesByNum(int nums);
  
  /**
   * Get the smell at the position.
   *
   * @param c position
   * @return the smell
   */
  Smell getSmellAt(Coordinate c);
  
  /**
   * Update the smell grid with given set of otyughs.
   *
   * @param otyughs set of otyughs in the map
   */
  void updateSmellGrid(Set<Otyugh> otyughs);
  
  /**
   * Generate random locations in the map.
   *
   * @param nums number of locations
   * @return set of the locations
   */
  Set<Coordinate> geneRandomLocs(int nums);
    
}
