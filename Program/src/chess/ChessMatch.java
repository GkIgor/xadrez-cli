package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Pawn;
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
  private boolean checkMate;

  public ChessMatch() {
    this.board = new Board(8, 8);
    this.turn = 1;
    this.currentPlayer = Color.WHITE;
    this.initialSetup();
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
    placeNewPiece('a', 1, new Rook(board, Color.WHITE)); // Rook
    placeNewPiece('e', 1, new King(board, Color.WHITE)); // King
    placeNewPiece('h', 1, new Rook(board, Color.WHITE)); // Rook
    placeNewPiece('a', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('b', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('c', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('d', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('e', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('f', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('g', 2, new Pawn(board, Color.WHITE)); // Pawn
    placeNewPiece('h', 2, new Pawn(board, Color.WHITE)); // Pawn

    // BLACK pieces
    placeNewPiece('a', 8, new Rook(board, Color.BLACK)); // Rook
    placeNewPiece('e', 8, new King(board, Color.BLACK)); // King
    placeNewPiece('h', 8, new Rook(board, Color.BLACK)); // Rook
    placeNewPiece('a', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('b', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('c', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('d', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('e', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('f', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('g', 7, new Pawn(board, Color.BLACK)); // Pawn
    placeNewPiece('h', 7, new Pawn(board, Color.BLACK)); // Pawn
  }

  private void validateSourcePosition(Position position) {
    if (!board.thereIsAPiece(position)) throw new ChessException("There is no piece on source position");

    if (this.currentPlayer != ((ChessPiece) board.piece(position)).getColor())
      throw new ChessException("The chosen piece is not yours");

    if (!board.piece(position).isThereAnyPossibleMove())
      throw new ChessException("There is no possible moves for the chosen piece");
  }

  private Piece makeMove(Position source, Position target) {
    ChessPiece p = (ChessPiece) board.removePiece(source);
    p.increaseMoveCount();
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
    ChessPiece p = (ChessPiece) board.removePiece(target);
    p.decreaseMoveCount();
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
    List<Piece> list = piecesOnTheBoard
          .stream()
          .filter(piece -> ((ChessPiece) piece).getColor() == color)
          .toList();

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

  private boolean testCheckMate(Color color) {
    if (!testCheck(color)) return false;
    List<Piece> list = piecesOnTheBoard
          .stream()
          .filter(piece -> ((ChessPiece) piece).getColor() == color)
          .toList();

    for (Piece p : list) {
      boolean[][] mat = p.possibleMoves();

      for (int i = 0; i < board.getRows(); i++) {
        for (int j = 0; j < board.getColumns(); j++) {
          if (mat[i][j]) {
            Position source = ((ChessPiece) p).getChessPosition().toPosition();
            Position target = new Position(i, j);
            Piece capturedPiece = makeMove(source, target);
            boolean testCheck = this.testCheck(color);
            undoMove(source, target, capturedPiece);

            if (!testCheck) return false;
          }
        }
      }
    }
    return true;
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

    if (testCheckMate(opponent(currentPlayer))) this.checkMate = true;

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

  public boolean getCheckMate() {
    return checkMate;
  }

  public Color getCurrentPlayer() {
    return this.currentPlayer;
  }

  public int getTurn() {
    return this.turn;
  }
}
