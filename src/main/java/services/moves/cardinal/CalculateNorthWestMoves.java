package services.moves.cardinal;

import java.util.ArrayList;

import gui.ChessGameBoard;
import utils.ColorOfPiece;
import utils.IsEnemy;
import utils.IsOnScreen;

public class CalculateNorthWestMoves extends CalculateCardinalMoves {
  public CalculateNorthWestMoves(int pieceRow, int pieceColumn, ColorOfPiece colorOfPiece) {
    super(pieceRow, pieceColumn, colorOfPiece);
  }

  @Override
  public ArrayList<String> invoke(ChessGameBoard board, int numMoves) {
      ArrayList<String> moves = new ArrayList<>();
       int count = 0;
       if (IsOnScreen.invoke(getPieceRow(),getPieceColumn())) {
           for (int i = 1; i < 8 && count < numMoves; i++) {
               if (IsOnScreen.invoke(getPieceRow() - i, getPieceColumn() - i)
                       && (board.getCell(getPieceRow() - i,
                       getPieceColumn() - i).getPieceOnSquare() == null)) {
                   moves.add((getPieceRow() - i) + "," + (getPieceColumn() - i));
                   count++;
               } else if (IsEnemy.invoke(board, getPieceRow() - i, getPieceColumn() - i,0)) {
                   moves.add((getPieceRow() - i) + "," + (getPieceColumn() - i));
                   break;
               } else {
                   count=numMoves;
               }
           }
       }
      return moves;
  }
}
