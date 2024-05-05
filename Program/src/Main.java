import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exceptions.ChessException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    ChessMatch chessMatch = new ChessMatch();
    while (true) {
      try {
        UI.clearScreen();
        UI.printMatch(chessMatch);
        out.println();
        out.print("Source: ");
        ChessPosition source = UI.readChessPosition(sc);

        boolean[][] possibleMoves = chessMatch.possibleMoves(source);
        UI.clearScreen();
        UI.printBoard(chessMatch.getPieces(), possibleMoves);

        out.println();
        out.print("Target: ");
        ChessPosition target = UI.readChessPosition(sc);

        ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
      } catch (ChessException | InputMismatchException err) {
        out.println(err.getMessage());
        sc.nextLine();

      }
    }

  }

}