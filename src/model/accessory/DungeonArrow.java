package model.accessory;

public class DungeonArrow implements Arrow {
  
  private boolean valid;
  
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
