package model.creature;

import model.graph.Coordinate;

public class DungeonOtyugh implements Otyugh {

  private int row;
  private int col;
  private LifeCondition lc;
  
  public DungeonOtyugh(Coordinate c) {
    this.setByCoord(c);
    this.lc = LifeCondition.HEALTHY;
  }
  
  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }

  @Override
  public Coordinate getCoord() {
    return new Coordinate(row, col);
  }

  @Override
  public void setRow(int newRow) {
    row = newRow;
  }

  @Override
  public void setCol(int newCol) {
    col = newCol;
  }

  @Override
  public void setByCoord(Coordinate coor) {
    row = coor.getRow();
    col = coor.getCol();
  }

  @Override
  public LifeCondition getLifeCondition() {
    return lc;
  }

  @Override
  public void setLifeCondition(LifeCondition newlc) {
    this.lc = newlc;
  }

  @Override
  public void beHurt() {
    switch (this.lc) {
      case HEALTHY:
        this.lc = LifeCondition.HURT;
        break;
      case HURT:
        this.lc = LifeCondition.DEAD;
      default:
        break;
    }
  }
  
  @Override
  public int hashCode() {
    return this.getCoord().hashCode();
  }
  
  @Override
  public boolean equals(Object o2) {
    if (!(o2 instanceof Otyugh)) {
      return false;
    }
    Otyugh oo = (Otyugh) o2;
    return this.getCoord().equals(oo.getCoord());
  }

}
