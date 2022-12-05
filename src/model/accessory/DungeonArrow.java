package model.accessory;

/**
 * This is one implementation of the arrow interface
 * in the dungeon game.
 *
 */
public class DungeonArrow implements Arrow {
  
  private boolean valid;
  
  /**
   * Initialize the arrow.
   *
   */
  public DungeonArrow() {
    this.valid = true;
  }

  @Override
  public boolean isValid() {
    return this.valid;
  }

  @Override
  public void setValid(boolean v) {
    this.valid = v;
  }

}
