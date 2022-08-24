package services.moves.cardinal;

import java.util.ArrayList;

import gui.ChessGameBoard;

public interface ICalculateMove {

	ArrayList<String> invoke(ChessGameBoard board, int numMoves);
}
