package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
  public King(Board board, Color color) {
    super(board, color);
  }

  private boolean canMove(Position position) {
    ChessPiece p = (ChessPiece) getBoard().piece(position);
    return p == null || p.getColor() != this.getColor();
  }

  @Override
  public String toString() {
    return "K";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getColumns()][getBoard().getRows()];
    Position position = new Position(0, 0);

    // above
    position.setValues(this.position.getRow() - 1, this.position.getColum());
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // below
    position.setValues(this.position.getRow() + 1, this.position.getColum());
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // left
    position.setValues(this.position.getRow(), this.position.getColum() - 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // right
    position.setValues(this.position.getRow(), this.position.getColum() + 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // nw
    position.setValues(this.position.getRow() - 1, this.position.getColum() - 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // ne
    position.setValues(this.position.getRow() - 1, this.position.getColum() + 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // sw
    position.setValues(this.position.getRow() + 1, this.position.getColum() - 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    // se
    position.setValues(this.position.getRow() + 1, this.position.getColum() + 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;



    return mat;
  }
}
