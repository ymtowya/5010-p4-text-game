package model;

/**
 * Enumeration of the directions.
 *
 */
public enum Direction {
  NORTH,
  WEST,
  EAST,
  SOUTH;
  
  static public Direction ofString(String s) {
    if ("n".equals(s)) {
      return NORTH;
    } else if ("w".equals(s)) {
      return WEST;
    } else if ("e".equals(s)) {
      return EAST;
    } else if ("s".equals(s)) {
      return SOUTH;
    }
    throw new IllegalArgumentException();
  }
}
