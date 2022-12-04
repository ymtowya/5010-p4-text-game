package model.randomhelper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Direction;
import model.accessory.Treasure;
import model.graph.Coordinate;

/**
 * Random Helper interface serves as the center of generating random
 * results for all purposes in the game.
 *
 */
public interface RandomHelper {
  
  /**
   * Get a randomly generated value with the left (inclusive) and
   * right (inclusive) range.
   *
   * @param left left bound (inclusive)
   * @param right right bound (inclusive)
   * @return generated value
   */
  int randomInt(int left, int right);
  
  /**
   * Generate a random coordinate with the given bound.
   *
   * @param maxRow max row
   * @param maxCol max col
   * @return  the coordinate
   */
  Coordinate randomCoord(int maxRow, int maxCol);
  
  /**
   * Split one value into several parts and return them.
   *
   * @param total the to-be-split value
   * @param parts number of parts
   * @return List containing the parts of the value
   */
  List<Integer> randDivideVal(Integer total, int parts);
  
  /**
   * Pick a coordinate from the given collection.
   *
   * @param coors collection of coordinates
   * @return the picked coord
   */
  Coordinate coordChoice(Collection<Coordinate> coors);
  
  /**
   * Pick a random cave.
   *
   * @param adj the array denoting the dungeon grid map
   * @return the coordinate of the cave
   */
  Coordinate randomCave(boolean[][][] adj);
  
  /**
   * Return a combo of the treasures.
   *
   * @param maxNum max number of one treasure
   * @return the combo map of treasures
   */
  Map<Treasure, Integer> treasureChoices(int maxNum);
  
  /**
   * Pick a random direction.
   *
   * @param ds set of directions
   * @return the picked direction
   */
  Direction directionChoice(Set<Direction> ds);
  
  boolean escape();
}
