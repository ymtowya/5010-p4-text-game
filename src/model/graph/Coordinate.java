package model.graph;

/**
 * This class represents the position in the 2-D map,
 * denoted by two dimension numbers : row & column.
 *
 */
public class Coordinate {
  int row;
  int col;
  
  /**
   * Initialize the coordinate with row and column.
   *
   * @param r row
   * @param c column
   */
  public Coordinate(int r, int c) {
    this.row = r;
    this.col = c;
  }
  
  /**
   * Initialize the coordinate with another coord.
   *
   * @param oldCoor old coordinate
   */
  public Coordinate(Coordinate oldCoor) {
    this.row = oldCoor.getRow();
    this.col = oldCoor.getCol();
  }

  /**
   * Get Row.
   *
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Set Row.
   *
   * @param r the row to set
   */
  public void setRow(int r) {
    this.row = r;
  }

  /**
   * Get Column.
   *
   * @return the col
   */
  public int getCol() {
    return col;
  }

  /**
   * Set the Col.
   *
   * @param c the col to set
   */
  public void setCol(int c) {
    this.col = c;
  }
  
  @Override
  public int hashCode() {
    return this.row * 251 + this.col;
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Coordinate)) {
      return false;
    }
    Coordinate toCompare = (Coordinate) o;
    if (row == toCompare.getRow()
        && col == toCompare.getCol()) {
      return true;
    }
    return false;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('(');
    sb.append(this.row);
    sb.append(", ");
    sb.append(this.col);
    sb.append(')');
    return sb.toString();
  }
  
}
