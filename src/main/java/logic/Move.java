package logic;

import logic.enums.Piece;
import logic.enums.Side;
import logic.enums.Square;
import logic.enums.Validity;

public class Move {

    Piece piece;
    Square origin;
    Square destination;
    int diceRoll;

    Piece promotionPiece;
    boolean promotionMove = false;

    Side side;
    Validity status;
    public Square enPassant = Square.INVALID;
    boolean enPassantMove = false;
    boolean enPassantCapture = false;
    public Square castling = Square.INVALID;

    public Move(Piece piece, Square origin, Square destination, int diceRoll, Side side) {
        this.piece = piece;
        this.origin = origin;
        this.destination = destination;
        this.diceRoll = diceRoll;
        this.side = side;
        promotionPiece = piece;

        this.status = Validity.PROCESSING;
    }

    public Validity getStatus() {
        return status;
    }

    public boolean isEnPassantCapture() {
        return enPassantCapture;
    }

    public boolean isPromotionMove() {
        return promotionMove;
    }

    public Piece getPromotionPiece() {
        return promotionPiece;
    }

    public void setStatus(Validity status) {
        this.status = status;
    }

    public void setValid() {
        this.status = Validity.VALID;
    }

    public void setInvalid() {
        this.status = Validity.INVALID;
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getOrigin() {
        return origin;
    }

    public Square getDestination() {
        return destination;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "Moved: " + piece +
                " from (origin): " + origin +
                ", to (destination)=" + destination;
    }
}
