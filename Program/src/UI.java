import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class UI {
  // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
  public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  private static void printPiece(ChessPiece piece, boolean background) {
    if (background)
      out.print(ANSI_BLUE_BACKGROUND);

    if (piece == null) {
      out.print('-' + ANSI_RESET);
      out.print(" ");
      return;
    }

    if (piece.getColor() == Color.WHITE) {
      out.print(ANSI_WHITE + piece + ANSI_RESET);
      out.print(" ");
      return;
    }

    out.print(ANSI_YELLOW + piece + ANSI_RESET);
    out.print(" ");

  }

  // https://stackoverflow.com/questions/2979383/java-clear-the-console
  public static void clearScreen() {
    out.print("\033[H\033[2J");
    out.flush();
  }


  public static void printBoard(ChessPiece[][] pieces) {
    for (int i = 0; i < pieces.length; i++) {
      out.print((8 - i) + " ");

      for (int j = 0; j < pieces.length; j++) {
        printPiece(pieces[i][j], false);
      }
      out.println();
    }
    out.println("  a b c d e f g h");

  }

  public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
    for (int i = 0; i < pieces.length; i++) {
      out.print((8 - i) + " ");

      for (int j = 0; j < pieces.length; j++) {
        printPiece(pieces[i][j], possibleMoves[i][j]);
      }
      out.println();
    }
    out.println("  a b c d e f g h");

  }

  public static ChessPosition readChessPosition(Scanner sc) {
    try {
      String str = sc.nextLine();
      char colum = str.charAt(0);
      int row = Integer.parseInt(str.substring(1));
      return new ChessPosition(colum, row);

    } catch (RuntimeException err) {
      throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
    }

  }

}
