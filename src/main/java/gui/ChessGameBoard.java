package gui;

import pieces.Queen;
import pieces.King;
import pieces.Bishop;
import pieces.Knight;
import pieces.Rook;
import pieces.ChessGamePiece;
import pieces.Pawn;
import services.moves.pieces.PieceMoveServiceImpl;
import utils.ColorOfPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The panel that represents the Chess game board. Contains a few methods that
 * allow other classes to access the physical board.
 */
public final class ChessGameBoard extends JPanel {
    private BoardSquare[][] chessCells;
    private final BoardListener listener;

    public BoardSquare[][] getCells() {
        return chessCells;
    }

    /**
     * Checks to make sure row and column are valid indices.
     *
     * @param row the row to check
     * @param col the column to check
     * @return boolean true if they are valid, false otherwise
     */
    private boolean validateCoordinates(int row, int col) {
        return chessCells.length > 0 && chessCells[0].length > 0 &&
                row < chessCells.length && col < chessCells[0].length
                && row >= 0 && col >= 0;
    }
    
    /**
     * Gets the BoardSquare at row 'row' and column 'col'.
     *
     * @param row the row to look at
     * @param col the column to look at
     * @return BoardSquare the square found, or null if it does not exist
     */
    public BoardSquare getCell(int row, int col) {
        if (validateCoordinates(row, col)) {
            return chessCells[row][col];
        }
        return null;
    }

     /**
     * Clears the cell at 'row', 'col'.
     *
     * @param row the row to look at
     * @param col the column to look at
     */
    public void clearCell(int row, int col) {
        if (validateCoordinates(row, col)) {
            chessCells[row][col].clearSquare();
        } else {
            throw new IllegalStateException("Row " + row + " and column" +
                    " " + col + " are invalid, or the board has not been" +
                    "initialized. This square cannot be cleared.");
        }
    }

    /**
     * Gets all the white game pieces on the board.
     *
     * @return ArrayList<ChessGamePiece> the pieces
     */
    public List<ChessGamePiece> getAllWhitePieces() {
        ArrayList<ChessGamePiece> whitePieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessCells[i][j].getPieceOnSquare() != null
                        && chessCells[i][j].getPieceOnSquare().getColorOfPiece().getColor() ==
                        ColorOfPiece.WHITE) {
                    whitePieces.add(chessCells[i][j].getPieceOnSquare());
                }
            }
        }
        return whitePieces;
    }

    /**
     * Gets all the black pieces on the board
     *
     * @return ArrayList<ChessGamePiece> the pieces
     */
    public List<ChessGamePiece> getAllBlackPieces() {
        ArrayList<ChessGamePiece> blackPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessCells[i][j].getPieceOnSquare() != null
                        && chessCells[i][j].getPieceOnSquare().getColorOfPiece().getColor() ==
                        ColorOfPiece.BLACK) {
                    blackPieces.add(chessCells[i][j].getPieceOnSquare());
                }
            }
        }
        return blackPieces;
    }

    /**
     * Create a new ChessGameBoard object.
     */
    public ChessGameBoard() {
        this.setLayout(new GridLayout(8, 8, 1, 1));
        listener = new BoardListener();
        chessCells = new BoardSquare[8][8];
        initializeBoard();
        new PieceMoveServiceImpl();
    }

    /**
     * Clears the board of all items, including any pieces left over in the
     * graveyard, and all old game logs.
     *
     * @param addAfterReset if true, the board will add the BoardSquares
     *                      back to the board, if false it will simply reset everything and leave
     *                      the board blank.
     */
    public void resetBoard(boolean addAfterReset) {
        chessCells = new BoardSquare[8][8];
        this.removeAll();
        if (getParent() instanceof ChessPanel chessPanel) {
            chessPanel.getGraveyard(1).clearGraveyard();
            chessPanel.getGraveyard(2).clearGraveyard();
            chessPanel.getGameLog().clearLog();
        }
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                chessCells[i][j] = new BoardSquare(i, j, null);
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
                if (addAfterReset) {
                    chessCells[i][j].addMouseListener(listener);
                    this.add(chessCells[i][j]);
                }
            }
        }
        repaint();
    }


    /**
     * (Re)initializes this ChessGameBoard to its default layout with all 32
     * pieces added.
     */
    public void initializeBoard() {
        resetBoard(false);
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                ChessGamePiece pieceToAdd;
                switch (i) {
                    case 1 -> pieceToAdd = new Pawn(this, i, j, ColorOfPiece.BLACK);
                    case 6 -> pieceToAdd = new Pawn(this, i, j, ColorOfPiece.WHITE);
                    case 0, 7 -> {
                        int colNum =
                                i == 0 ? ColorOfPiece.BLACK : ColorOfPiece.WHITE;
                        pieceToAdd = switch (j) {
                            case 0, 7 -> new Rook(this, i, j, colNum);
                            case 1, 6 -> new Knight(this, i, j, colNum);
                            case 2, 5 -> new Bishop(this, i, j, colNum);
                            case 3 -> new King(this, i, j, colNum);
                            default -> new Queen(this, i, j, colNum);
                        };
                    }
                    default -> pieceToAdd = null;
                }
                chessCells[i][j] = new BoardSquare(i, j, pieceToAdd);
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
                chessCells[i][j].addMouseListener(listener);
                this.add(chessCells[i][j]);
            }
        }
    }

    /**
     * Clears the colors on the board.
     */
    public void clearColorsOnBoard() {
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    /**
     * Listens for clicks on BoardSquares.
     */
    private class BoardListener
            implements MouseListener, Serializable {
        
        /**
         * Do an action when the left mouse button is clicked.
         *
         * @param e the event from the listener
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 &&
                    getParent() instanceof ChessPanel chessPanel) {
                chessPanel.getGameEngine()
                        .determineActionFromSquareClick(e);
            }
        }

        /**
         * Unused methods
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            // Do nothing on mouse entered
        }
        @Override
        public void mouseExited(MouseEvent e) { 
            // Do nothing on mouse exited
        }
        @Override
        public void mousePressed(MouseEvent e) {
            // Do nothing on mouse pressed
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            // Do nothing on mouse released
        }
    }
}