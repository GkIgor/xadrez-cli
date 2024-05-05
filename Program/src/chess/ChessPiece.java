package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
  private final Color color;

  public ChessPiece(Board board, Color color) {
    super(board);
    this.color = color;
  }

  protected boolean isThereOpponentPiece(Position position) {
    ChessPiece piece = (ChessPiece) getBoard().piece(position);
    return piece != null && piece.getColor() != color;
  }

  public Color getColor() {
    return this.color;
  }

  public ChessPosition getChessPosition() {
    return ChessPosition.fromPosition(position);
  }


}
