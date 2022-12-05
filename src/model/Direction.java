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
  
  /**
   * Get the direction by string.
   * @param s string of the direction
   * @return the direction
   */
  public static Direction ofString(String s) {
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
  
  /**
   * Get the opposite direction.
   * @param d given direction
   * @return the opposite direction
   */
  public static Direction oppositeDir(Direction d) {
    switch (d) {
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      case NORTH:
        return SOUTH;
      default:
        return NORTH;
    }
  }
}
