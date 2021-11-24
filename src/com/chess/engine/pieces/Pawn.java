package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16,7,9};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.PAWN, piecePosition, pieceAlliance,true);
    }
    public Pawn(final int piecePosition, final Alliance pieceAlliance,final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: CANDIDATE_MOVE_COORDINATE){

            final int candidateDestinationCoordinate = this.piecePosition+currentCoordinateOffset*this.pieceAlliance.getDirection();

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCoordinateOffset==8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                //TODO more work to do here to deal with promotions!!
                legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));//Stub
            } else if(currentCoordinateOffset==16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceAlliance.isWhite()))){

                final int behindCandidateDestinationCoordinate = this.piecePosition+(this.pieceAlliance.getDirection()*8);

                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new PawnJump(board,this,candidateDestinationCoordinate));//Stub
                }
            } else if(currentCoordinateOffset==7 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                    (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()))){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()){
                        //TODO MORE TO DO HERE
                        legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
                    }
                }
            } else if(currentCoordinateOffset==9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()){
                        //TODO MORE TO DO HERE
                        legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }

}
