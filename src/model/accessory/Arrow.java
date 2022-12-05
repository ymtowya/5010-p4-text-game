package model.accessory;

/**
 * This interface represents the arrow in the game.
 *
 *
 */
public interface Arrow {

  /**
   * Tell if the arrow is still valid.
   *
   * @return true if it's valid, false otherwise
   */
  boolean isValid();
  
  /**
   * This set the validity of the arrow.
   *
   * @param v new validity
   */
  void setValid(boolean v);
  
}
