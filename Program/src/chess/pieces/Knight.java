package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
  public Knight(Board board, Color color) {
    super(board, color);
  }

  private boolean canMove(Position position) {
    ChessPiece p = (ChessPiece) getBoard().piece(position);
    return p == null || p.getColor() != this.getColor();
  }

  @Override
  public String toString() {
    return "N";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getColumns()][getBoard().getRows()];
    Position position = new Position(0, 0);

    position.setValues(this.position.getRow() - 1, this.position.getColum() - 2);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() - 2, this.position.getColum() - 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() - 2, this.position.getColum() + 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() - 1, this.position.getColum() + 2);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() + 1, this.position.getColum() + 2);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() + 2, this.position.getColum() + 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() + 2, this.position.getColum() - 1);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;

    position.setValues(this.position.getRow() + 1, this.position.getColum() - 2);
    if (this.getBoard().positionExists(position) && canMove(position))
      mat[position.getRow()][position.getColum()] = true;



    return mat;
  }
}
