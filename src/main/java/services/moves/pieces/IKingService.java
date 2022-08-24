package services.moves.pieces;

import pieces.ChessGamePiece;
import java.io.Serializable;

public interface IKingService extends Serializable {

    boolean isChecked(ChessGamePiece piece);
}
