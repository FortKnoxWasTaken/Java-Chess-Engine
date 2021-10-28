package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16};

    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: CANDIDATE_MOVE_COORDINATE){

            final int candidateDestinationCoordinate = this.piecePosition+(currentCoordinateOffset)*this.pieceAlliance.getDirection();

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCoordinateOffset==8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                //TODO more work to do here to deal with promotions!!
                legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));//Stub
            } else if(currentCoordinateOffset==16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())){
                final int behindCandidateDestinationCoordinate = this.piecePosition+(this.pieceAlliance.getDirection()*8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));//Stub
                }
            }
        }

        return legalMoves;
    }
}
