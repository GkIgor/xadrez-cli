package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
  private final Board board;

  public ChessMatch() {
    this.board = new Board(8, 8);
    this.initialSetup();
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    board.placePiece(piece, new ChessPosition(column, row).toPosition());
  }

  private void initialSetup() {
    // WHITE Pieces
    placeNewPiece('b', 6, new Rook(board, Color.WHITE));
    placeNewPiece('e', 1, new King(board, Color.WHITE));

    // BLACK pieces
    placeNewPiece('e', 8, new King(board, Color.BLACK));
  }

  public ChessPiece[][] getPieces() {
    ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

    for (int i = 0; i < board.getRows(); i++) {

      for (int j = 0; j < board.getColumns(); j++) {

        mat[i][j] = (ChessPiece) board.piece(i, j);

      }

    }
    return mat;
  }

}
