package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
  private final Board board;
  private final List<Piece> piecesOnTheBoard = new ArrayList<>();
  private final List<Piece> capturedPieces = new ArrayList<>();
  private int turn;
  private Color currentPlayer;
  private boolean check;

  public ChessMatch() {
    this.board = new Board(8, 8);
    this.turn = 1;
    this.currentPlayer = Color.WHITE;
    this.initialSetup();
  }

  public Color getCurrentPlayer() {
    return this.currentPlayer;
  }

  public int getTurn() {
    return this.turn;
  }

  private void nextTurn() {
    turn++;
    this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    board.placePiece(piece, new ChessPosition(column, row).toPosition());
    piecesOnTheBoard.add(piece);
  }

  private void initialSetup() {
    // WHITE Pieces
    placeNewPiece('c', 1, new Rook(board, Color.WHITE));
    placeNewPiece('c', 2, new Rook(board, Color.WHITE));
    placeNewPiece('d', 2, new Rook(board, Color.WHITE));
    placeNewPiece('e', 2, new Rook(board, Color.WHITE));
    placeNewPiece('e', 1, new Rook(board, Color.WHITE));
    placeNewPiece('d', 1, new King(board, Color.WHITE));

    // BLACK pieces
    placeNewPiece('c', 7, new Rook(board, Color.BLACK));
    placeNewPiece('c', 8, new Rook(board, Color.BLACK));
    placeNewPiece('d', 7, new Rook(board, Color.BLACK));
    placeNewPiece('e', 7, new Rook(board, Color.BLACK));
    placeNewPiece('e', 8, new Rook(board, Color.BLACK));
    placeNewPiece('d', 8, new King(board, Color.BLACK));
  }

  private void validateSourcePosition(Position position) {
    if (!board.thereIsAPiece(position)) throw new ChessException("There is no piece on source position");

    if (this.currentPlayer != ((ChessPiece) board.piece(position)).getColor())
      throw new ChessException("The chosen piece is not yours");

    if (!board.piece(position).isThereAnyPossibleMove())
      throw new ChessException("There is no possible moves for the chosen piece");
  }

  private Piece makeMove(Position source, Position target) {
    Piece p = board.removePiece(source);
    Piece capturedPiece = board.removePiece(target);
    board.placePiece(p, target);

    if (capturedPiece != null) {
      piecesOnTheBoard.remove(capturedPiece);
      capturedPieces.add(capturedPiece);
    }

    return capturedPiece;
  }

  private void validateTargetPosition(Position source, Position target) {
    if (!board.piece(source).possibleMove(target)) {
      throw new ChessException("The chosen piece can't move to target position");
    }
  }

  private void undoMove(Position source, Position target, Piece capturedPiece) {
    Piece p = board.removePiece(target);
    board.placePiece(p, source);

    if (capturedPiece != null) {
      board.placePiece(capturedPiece, target);
      capturedPieces.remove(capturedPiece);
      piecesOnTheBoard.add(capturedPiece);
    }
  }

  private Color opponent(Color color) {
    return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
  }

  private ChessPiece king(Color color) {
    List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece) piece).getColor() == color).toList();
    for (Piece p : list) {
      if (p instanceof King) {
        return (ChessPiece) p;

      }
    }
    throw new IllegalStateException("There is no " + color + " king on the board");
  }

  private boolean testCheck(Color color) {
    Position kingPosition = king(color).getChessPosition().toPosition();
    List<Piece> opponentPieces = piecesOnTheBoard
          .stream()
          .filter(piece -> ((ChessPiece) piece).getColor() == opponent(color))
          .toList();
    for (Piece p : opponentPieces) {
      boolean[][] mat = p.possibleMoves();
      if (mat[kingPosition.getRow()][kingPosition.getColum()]) return true;
    }
    return false;
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

  public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    Position source = sourcePosition.toPosition();
    Position target = targetPosition.toPosition();

    this.validateSourcePosition(source);
    this.validateTargetPosition(source, target);

    Piece capturedPiece = makeMove(source, target);

    if (testCheck(currentPlayer)) {
      undoMove(source, target, capturedPiece);
      throw new ChessException("You can't put yourself in check");
    }

    this.check = testCheck(opponent(currentPlayer));

    this.nextTurn();
    return (ChessPiece) capturedPiece;
  }

  public boolean[][] possibleMoves(ChessPosition sourcePosition) {
    Position position = sourcePosition.toPosition();
    this.validateSourcePosition(position);
    return board.piece(position).possibleMoves();
  }

  public boolean getCheck() {
    return this.check;
  }

}
