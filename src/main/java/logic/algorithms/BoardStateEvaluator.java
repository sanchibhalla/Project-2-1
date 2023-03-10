package logic.algorithms;

import logic.LegalMoveGenerator;
import logic.ML.DQL;
import logic.ML.OriginAndDestSquare;
import logic.PieceAndSquareTuple;
import logic.State;
import logic.enums.Piece;
import logic.enums.Side;
import logic.enums.Square;

import java.util.ArrayList;
import java.util.List;

public class BoardStateEvaluator {

    public static int getBoardEvaluationNumber(State state, Side color) { // for ML
        int evalNo = 0;
        int turn = 1;

        ArrayList<OriginAndDestSquare> originAndDestSquares = LegalMoveGenerator.getAllLegalMovesML(state, color);

        for (OriginAndDestSquare tempMove : originAndDestSquares) {
            Square s = tempMove.getOrigin();
            Piece p = state.getBoard().getPieceAt(s);

            if (p.getColor()==color) {
                evalNo += p.getWeight();
                evalNo += getCorrectWeights(p,turn)[s.getRank()-1][s.getFile()];
            } else {
                evalNo -= p.getWeight();
                evalNo -= getCorrectWeights(p,turn)[s.getRank()-1][s.getFile()];
            }
            }
        return evalNo;
    }

    public static int getEvalOfQL(State state, int depth) {
        DQL ql = new DQL();
        ql.algo(state, state.getColor(), depth);
        return ql.getAvgValuesOfTable();
    }




    private final static int[][] knightBoardWeightsB = {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20, 0, 5, 5, 0, -20, -40},
            {-30, 5, 10, 15, 15, 10, 5, -30},
            {-30, 0, 15, 20, 20, 15, 0, -30},
            {-30, 5, 15, 20, 20, 15, 5, -30},
            {-30, 0, 10, 15, 15, 10, 0, -30},
            {-40, -20, 0, 0, 0, 0, -20, -40},
            {-50, -40, -30, -30, -30, -30, -40, -50}};
    private final static int[][] knightBoardWeightsW = {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20, 0, 0, 0, 0, -20, -40},
            {-30, 0, 10, 15, 15, 10, 0, -30},
            {-30, 5, 15, 20, 20, 15, 5, -30},
            {-30, 0, 15, 20, 20, 15, 0, -30},
            {-30, 5, 10, 15, 15, 10, 5, -30},
            {-40, -20, 0, 5, 5, 0, -20, -40},
            {-50, -40, -30, -30, -30, -30, -40, -50}};
    private final static int[][] queenBoardWeightsB = {
            {-20, -10, -10, -5, -5, -10, -10, -20},
            {-10, 0, 5, 0, 0, 0, 0, -10},
            {-10, 5, 5, 5, 5, 5, 0, -10},
            {0, 0, 5, 5, 5, 5, 0, -5},
            {-5, 0, 5, 5, 5, 5, 0, -5},
            {-10, 0, 5, 5, 5, 5, 0, -10},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-20, -10, -10, -5, -5, -10, -10, -20}};
    private final static int[][] queenBoardWeightsW = {
            {-20, -10, -10, -5, -5, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 5, 5, 5, 0, -10},
            {-5, 0, 5, 5, 5, 5, 0, -5},
            {0, 0, 5, 5, 5, 5, 0, -5},
            {-10, 5, 5, 5, 5, 5, 0, -10},
            {-10, 0, 5, 0, 0, 0, 0, -10},
            {-20, -10, -10, -5, -5, -10, -10, -20}};
    private final static int[][] bishopBoardWeightsB = {
            {-20, -10, -10, -10, -10, -10, -10, -20},
            {-10, 5, 0, 0, 0, 0, 5, -10},
            {-10, 10, 10, 10, 10, 10, 10, -10},
            {-10, 0, 10, 10, 10, 10, 0, -10},
            {-10, 5, 5, 10, 10, 5, 5, -10},
            {-10, 0, 5, 10, 10, 5, 0, -10},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-20, -10, -10, -10, -10, -10, -10, -20}};
    private final static int[][] bishopBoardWeightsW = {
            {-20, -10, -10, -10, -10, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 10, 10, 5, 0, -10},
            {-10, 5, 5, 10, 10, 5, 5, -10},
            {-10, 0, 10, 10, 10, 10, 0, -10},
            {-10, 10, 10, 10, 10, 10, 10, -10},
            {-10, 5, 0, 0, 0, 0, 5, -10},
            {-20, -10, -10, -10, -10, -10, -10, -20}};
    private final static int[][] pawnBoardWeightsB = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 10, -20, -20, 10, 10, 5},
            {5, -5, -10, 0, 0, -10, -5, 5},
            {0, 0, 0, 20, 20, 0, 0, 0},
            {5, 5, 10, 25, 25, 10, 5, 5},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {0, 0, 0, 0, 0, 0, 0, 0}};
    private final static int[][] pawnBoardWeightsW = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5, 5, 10, 25, 25, 10, 5, 5},
            {0, 0, 0, 20, 20, 0, 0, 0},
            {5, -5, -10, 0, 0, -10, -5, 5},
            {5, 10, 10, -20, -20, 10, 10, 5},
            {0, 0, 0, 0, 0, 0, 0, 0}};
    private final static int[][] rookBoardWeightsW = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {5, 10, 10, 10, 10, 10, 10, 5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {0, 0, 0, 5, 5, 0, 0, 0}};
    private final static int[][] rookBoardWeightsB = {
            {0, 0, 0, 5, 5, 0, 0, 0},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {-5, 0, 0, 0, 0, 0, 0, -5},
            {5, 10, 10, 10, 10, 10, 10, 5},
            {0, 0, 0, 0, 0, 0, 0, 0}};
    private final static int[][] kingBoardWeightsMiddleGameW = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {20, 20, 0, 0, 0, 0, 20, 20},
            {20, 30, 10, 0, 0, 10, 30, 20}};
    private final static int[][] kingBoardWeightsMiddleGameB = {
            {20, 30, 10, 0, 0, 10, 30, 20},
            {20, 20, 0, 0, 0, 0, 20, 20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},};
    private final static int[][] kingBoardWeightsEndGameW = {
            {-50, -40, -30, -20, -20, -30, -40, -50},
            {-30, -20, -10, 0, 0, -10, -20, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -30, 0, 0, 0, 0, -30, -30},
            {-50, -30, -30, -30, -30, -30, -30, -50}};
    private final static int[][] kingBoardWeightsEndGameB = {
            {-50, -30, -30, -30, -30, -30, -30, -50},
            {-30, -30, 0, 0, 0, 0, -30, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 30, 40, 40, 30, -10, -30},
            {-30, -10, 20, 30, 30, 20, -10, -30},
            {-30, -20, -10, 0, 0, -10, -20, -30},
            {-50, -40, -30, -20, -20, -30, -40, -50}};

    private final BoardStateGenerator generator = new BoardStateGenerator();


    // return a value of a board for a given side taking into account the piece values and their corresponding board square position values
    public static int getBoardEvaluationNumber(List<PieceAndSquareTuple> nodePieceAndSquare, Side color, int turn) {
        int evalNo = 0;
        for (PieceAndSquareTuple<Piece, Square> ps : nodePieceAndSquare) {
            // if piece friendly add weight, else subtract opposite weight
            if (ps.getPiece().getColor() == color) {
                evalNo += ps.getPiece().getWeight();
                evalNo += getCorrectWeights(ps.getPiece(), turn)[ps.getSquare().getRank() - 1][ps.getSquare().getFile()];
                //evalNo -= 50 * getDoubledOrBlockedPawnCount(nodePieceAndSquare, color); // double blocked isolated pawns are bad so subtract
                //evalNo += 10 * getNumberOfMovesForSide(nodePieceAndSquare, color);
            } else {
                evalNo -= ps.getPiece().getWeight();
                evalNo -= getCorrectWeights(ps.getPiece(), turn)[ps.getSquare().getRank() - 1][ps.getSquare().getFile()];
                //evalNo += 50 * getDoubledOrBlockedPawnCount(nodePieceAndSquare, Side.getOpposite(color));
                //evalNo += 10 * getNumberOfMovesForSide(nodePieceAndSquare, color);
            }
        }
        return evalNo;
    }

    /**
     * @param nodePieceAndSquare
     * @param color
     * @return the total count of possible legal moves for a given state for a given side
     * @see <a href="https://www.chessprogramming.org/Evaluation#Linear_vs._Nonlinear">Inspired by: Chess Programming Wiki</a>
     */
    private static int getNumberOfMovesForSide(List<PieceAndSquareTuple> nodePieceAndSquare, Side color) {
        return 0;
    }

    /**
     * @param nodePieceAndSquare list of pieces and their corresponding squares
     * @param color              side of double and blocked pawns being counted
     * @return the total count of blocked or doubled pawns for given side
     * @see <a href="https://www.chessprogramming.org/Evaluation">Inspired by: Chess Programming Wiki</a>
     */
    private static int getDoubledOrBlockedPawnCount(List<PieceAndSquareTuple> nodePieceAndSquare, Side color) {
        // above for black
        int count = 0;
        for (PieceAndSquareTuple<Piece, Square> ps : nodePieceAndSquare) {
            if (ps.getPiece().getType() == Piece.PAWN && ps.getPiece().getColor() == color) {
                for (PieceAndSquareTuple<Piece, Square> ps2 : nodePieceAndSquare) {
                    // doubled pawns
                    // check above for black because the way of the boardIndexMap setup
                    if (ps.getSquare().getSquareAbove() == ps2.getSquare() && ps2.getPiece().getType() == Piece.PAWN
                            && ps2.getPiece().getColor() == ps.getPiece().getColor() && ps.getPiece().getColor() == Side.BLACK) {
                        count++;
                        // check below for white
                    } else if (ps.getSquare().getSquareBelow() == ps2.getSquare() && ps2.getPiece().getType() == Piece.PAWN
                            && ps2.getPiece().getColor() == ps.getPiece().getColor() && ps.getPiece().getColor() == Side.WHITE) {
                        count++;
                    }
                    // blocked pawns
                    if (ps.getSquare().getSquareAbove() == ps2.getSquare() && ps2.getPiece().getType() == Piece.PAWN
                            && ps2.getPiece().getColor() == Side.getOpposite(ps.getPiece().getColor()) && ps.getPiece().getColor() == Side.BLACK) {
                        count++;
                    } else if (ps.getSquare().getSquareBelow() == ps2.getSquare() && ps2.getPiece().getType() == Piece.PAWN
                            && ps2.getPiece().getColor() == Side.getOpposite(ps.getPiece().getColor()) && ps.getPiece().getColor() == Side.WHITE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param piece piece who's point value matrix you want to receive
     * @param turn  cumulative game turn found in State object
     * @return correct point value weights for given piece
     * @see <a href="https://www.chessprogramming.org/Point_Value">Inspired by: Chess Programming Wiki</a>
     */
    // return appropriate board configuration for correct side
    private static int[][] getCorrectWeights(Piece piece, int turn) {
        if (piece == Piece.WHITE_KING && turn < 50) return kingBoardWeightsMiddleGameB;
        if (piece == Piece.BLACK_KING && turn < 50) return kingBoardWeightsMiddleGameW;
        if (piece == Piece.WHITE_KING && turn >= 50) return kingBoardWeightsEndGameB;
        if (piece == Piece.BLACK_KING && turn >= 50) return kingBoardWeightsEndGameW;
        return switch (piece) {
            case WHITE_PAWN -> pawnBoardWeightsB;
            case BLACK_PAWN -> pawnBoardWeightsW;
            case WHITE_KNIGHT -> knightBoardWeightsB;
            case BLACK_KNIGHT -> knightBoardWeightsW;
            case WHITE_BISHOP -> bishopBoardWeightsB;
            case BLACK_BISHOP -> bishopBoardWeightsW;
            case WHITE_ROOK -> rookBoardWeightsB;
            case BLACK_ROOK -> rookBoardWeightsW;
            case WHITE_QUEEN -> queenBoardWeightsB;
            case BLACK_QUEEN -> queenBoardWeightsW;
            default -> null;
        };
    }

}
