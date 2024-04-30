package boardgame;

public class Position {
  private int row, colum;


  public Position(int row, int colum) {
    this.colum = colum;
    this.row = row;
  }

  public int getColum() {
    return this.colum;
  }

  public void setColum(int colum) {
    this.colum = colum;
  }

  public int getRow() {
    return this.row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public void setValues(int row, int colum) {
    this.row = row;
    this.colum = colum;
  }

  @Override
  public String toString() {
    return String.format("%d, %d", this.row, this.colum);
  }
}
