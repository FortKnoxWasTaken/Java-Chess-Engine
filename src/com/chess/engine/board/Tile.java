package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import java.util.*;

public abstract class Tile {
    // Can be accessed only by subclasses and set only at the time of instantiating
    protected final int tileCoordinate; // 0-63

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = creatAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> creatAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap =new HashMap<>();

        for(int i = 0; i<BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece!=null ? new OccupiedTile(tileCoordinate,piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {
        // Only accessible by the class OccupiedTile class and its members
        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, final Piece piece) {
            super(tileCoordinate);
            this.pieceOnTile=piece;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }

}
