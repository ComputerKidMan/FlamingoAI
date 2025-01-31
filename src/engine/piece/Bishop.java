package engine.piece;

import engine.board.Board;
import engine.board.BoardUtil;
import engine.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(final int index, final Tile tile, final boolean alliance) {
        super(index, Type.Bishop, tile, alliance, List.of(-9, -7, 7, 9));
    }

    @Override
    public List<Move> getLegals(Board board) {
        final List<Move> moves = new ArrayList<>();
        if (dead) return moves;

        for (int dir : directions){
            final int start = tile.index;
            int end = start;

            if (dir == 1 || dir == 9 || dir == -7){
                if (BoardUtil.isLastFile(start)) continue;
            }
            else if (dir == -1 || dir == 7 || dir == -9){
                if (BoardUtil.isFirstFile(start)) continue;
            }

            while (end >= 0 && end < 63){
                end += dir;
                if (end < 0 || end > 63) break;
                Tile tile = board.tiles[end];
                if (tile.occupied && alliance == tile.piece.alliance) break;
                moves.add(new Move(start, end, this, tile.piece));

                if (dir == 1 || dir == 9 || dir == -7){
                    if (BoardUtil.isLastFile(end)) break;
                }
                else if (dir == -1 || dir == 7 || dir == -9){
                    if (BoardUtil.isFirstFile(end)) break;
                }
            }
        }
        for (Move move : moves){
            board.makeMove(move, true);
            if (BoardUtil.isCheck(alliance, board)) move.illegal = true;
            board.unMakeMove(move);
        }

        moves.removeIf(move -> move.illegal);

        return moves;
    }
}
