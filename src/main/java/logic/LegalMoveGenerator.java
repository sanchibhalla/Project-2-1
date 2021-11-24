package logic;

import logic.board.Board;
import logic.enums.Piece;
import logic.enums.Side;
import logic.enums.Square;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static logic.enums.Piece.EMPTY;
import static logic.enums.Piece.getDiceFromPiece;
import static logic.enums.Square.INVALID;
import static logic.enums.Square.getSquare;

public class LegalMoveGenerator {

    //for GUI
    public List<Square> getLegalMoves(logic.State state, Square squareOrigin, Piece piece, Side side) {
        LegalMoveEvaluator evaluator = new LegalMoveEvaluator();
        ArrayList<Square> legalMoves = new ArrayList<>();
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                // TODO Test this: Passing 99999 or arbitrary number as move diceroll get's neglected instead of 1 for diceroll causes index out of bounds in evaluator
                if(evaluator.isLegalMove(new Move(piece,squareOrigin,Square.getSquare(rank,file),getDiceFromPiece(piece),side),state,false,true)) {
                    legalMoves.add(Square.getSquare(rank,file));
                }
            }
        }
        return legalMoves;
    }

    public List<Square> getMoves(State state, Square origin, Piece piece) {
        List<Square> validMoves = new LinkedList<>();
        Board board = state.getBoard();

        switch (piece.getType()) {
            case PAWN -> {
                //this one is more complex and weird since it depends on logic.board state with the en passant and capturing
                Square naturalMove = Square.getSquare(origin.getSquareNumber() + piece.getOffsets()[0]);
                if (board.isEmpty(naturalMove)) {
                    validMoves.add(naturalMove);

                    //double jumping
                    Square doubleJump = Square.getSquare(naturalMove.getSquareNumber() + piece.getOffsets()[0]);
                    if (doubleJump != Square.INVALID && board.isEmpty(doubleJump) && piece.canDoubleJump(origin))
                        validMoves.add(doubleJump);
                }

                for (int k = 1; k < 3; k++) {
                    if (!board.isOffBoard(origin.getSquareNumber() + piece.getOffsets()[k])) {
                        Square validTarget = Square.getSquare(origin.getSquareNumber() + piece.getOffsets()[k]);

                        if (board.getPieceAt(validTarget) != EMPTY && !board.getPieceAt(validTarget).isFriendly(piece.getColor()))
                            validMoves.add(validTarget);
                    }
                }
            }

            case KING -> {
                for (int offset : piece.getOffsets()) {
                    if (!board.isOffBoard(origin.getSquareNumber() + offset)) {
                        Square target = Square.getSquare(origin.getSquareNumber() + offset);

                        if (board.isEmpty(target) || !board.getPieceAt(target).isFriendly(piece.getColor())) {
                            validMoves.add(target);
                        }
                    }
                }
                //CHECK FOR CASTLING
                if (piece.getColor() == Side.WHITE) {
                    if (origin.getSquareNumber() == 4) {
                        //SHORT WHITE
                        if (board.isEmpty(origin.getSquareRight()) && board.isEmpty(getSquare(6)) && state.isShortCastlingWhite()) {
                            validMoves.add(getSquare(6));
                        }
                        //LONG WHITE
                        if (board.isEmpty(origin.getSquareLeft()) && board.isEmpty(getSquare(2)) && board.isEmpty(getSquare(1)) && state.isLongCastlingWhite()) {
                            validMoves.add(getSquare(2));
                        }
                    }
                }
                else {
                    if (origin.getSquareNumber() == 116) {
                        //SHORT BLACK
                        if (board.isEmpty(origin.getSquareRight()) && board.isEmpty(getSquare(118)) && state.isShortCastlingBlack()) {
                            validMoves.add(getSquare(118));
                        }
                        //LONG BLACK
                        if (board.isEmpty(origin.getSquareLeft()) && board.isEmpty(getSquare(114)) && board.isEmpty(getSquare(113)) && state.isLongCastlingBlack()) {
                            validMoves.add(getSquare(114));
                        }
                    }
                }
            }

            case KNIGHT -> {
                for (int offset : piece.getOffsets()) {
                    if (!board.isOffBoard(origin.getSquareNumber() + offset)) {
                        Square target = Square.getSquare(origin.getSquareNumber() + offset);

                        if (board.isEmpty(target) || !board.getPieceAt(target).isFriendly(piece.getColor())) {
                            validMoves.add(target);
                        }
                    }
                }
            }

            case BISHOP, ROOK, QUEEN -> {
                for (int offset : piece.getOffsets()) {
                    if (!board.isOffBoard(origin.getSquareNumber() + offset)) {
                        Square target = Square.getSquare(origin.getSquareNumber() + offset);

                        while (target != INVALID && board.isEmpty(target) ) {
                            validMoves.add(target);
                            target = Square.getSquare(target.getSquareNumber() + offset);
                        }

                        if (target != INVALID && !board.getPieceAt(target).isFriendly(piece.getColor())) {
                            validMoves.add(target);
                        }
                    }
                }
            }
        }


        return validMoves;
    }

}
