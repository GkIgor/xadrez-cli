package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

  public Bishop(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "B";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] mat = new boolean[getBoard().getColumns()][getBoard().getRows()];
    Position p = new Position(0, 0);

    // nw
    p.setValues(this.position.getRow() - 1, this.position.getColum() - 1);

    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColum()] = true;
      p.setValues(p.getRow() - 1, p.getColum() - 1);

    }
    if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColum()] = true;

    // ne
    p.setValues(this.position.getRow() - 1, this.position.getColum() + 1);

    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColum()] = true;
      p.setValues(p.getRow() - 1, p.getColum() + 1);

    }
    if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColum()] = true;

    // se
    p.setValues(this.position.getRow() + 1, this.position.getColum() + 1);

    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColum()] = true;
      p.setValues(p.getRow() + 1, p.getColum() + 1);

    }
    if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColum()] = true;

    // sw
    p.setValues(this.position.getRow() + 1, this.position.getColum() - 1);

    while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
      mat[p.getRow()][p.getColum()] = true;
      p.setValues(p.getRow() + 1,p.getColum() + 1);

    }
    if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColum()] = true;

    return mat;
  }


}
