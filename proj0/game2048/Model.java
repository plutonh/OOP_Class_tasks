package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        int i, j, k, sum, cnt;

        if(side == Side.EAST)
        {
            changed = false;
            for(j=0;j<board.size();j++) {
                for (i=board.size()-1;i>0;i--) {
                    if((board.tile(i, j) == null) && (board.tile(i-1, j) != null)) changed = true;
                    if((board.tile(i, j) != null) && (board.tile(i-1, j) != null) && (board.tile(i, j).value() == board.tile(i-1, j).value())) changed = true;
                }
            }

            for(j=0;j<board.size();j++) {
                cnt = 0;
                for(i=board.size()-1;i>=0;i--) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(board.size()-1-cnt, j, t);
                        changed = true;
                        cnt++;
                    }
                }
            }

            for(j=0;j<board.size();j++) {
                for (i=board.size()-1;i>0;i--) {
                    if ((board.tile(i, j) != null) && (board.tile(i-1, j) != null)) {
                        if (board.tile(i, j).value() == board.tile(i-1, j).value()) {
                            Tile t = board.tile(i-1, j);
                            board.move(i, j, t);
                            changed = true;
                            sum = board.tile(i, j).value();
                            score += sum;
                            for(k=board.size()-1;k>0;k--)
                            {
                                if ((board.tile(k, j) != null) && (board.tile(k-1, j) != null)) {
                                    if (board.tile(k, j).value() == board.tile(k-1, j).value()) continue;
                                    else break;
                                }
                                else continue;
                            }
                        } else continue;
                    } else continue;
                }
            }

            for(j=0;j<board.size();j++) {
                cnt = 0;
                for(i=board.size()-1;i>=0;i--) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(board.size()-1-cnt, j, t);
                        changed = true;
                        cnt++;
                    }
                }
            }
        }
        else if(side == Side.WEST)
        {
            changed = false;
            for(j=0;j<board.size();j++) {
                for (i=0;i<board.size()-1;i++) {
                    if((board.tile(i, j) == null) && (board.tile(i+1, j) != null)) changed = true;
                    if((board.tile(i, j) != null) && (board.tile(i+1, j) != null) && (board.tile(i, j).value() == board.tile(i+1, j).value())) changed = true;
                }
            }

            for(j=0;j<board.size();j++) {
                cnt = 0;
                for(i=0;i<board.size();i++) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(cnt, j, t);
                        cnt++;
                    }
                }
            }

            for(j=0;j<board.size();j++) {
                for (i=0;i<board.size()-1;i++) {
                    if ((board.tile(i, j) != null) && (board.tile(i+1, j) != null)) {
                        if (board.tile(i, j).value() == board.tile(i+1, j).value()) {
                            Tile t = board.tile(i+1, j);
                            board.move(i, j, t);
                            sum = board.tile(i, j).value();
                            score += sum;
                            for(k=0;k<board.size();k++)
                            {
                                if ((board.tile(k, j) != null) && (board.tile(k+1, j) != null)) {
                                    if (board.tile(k, j).value() == board.tile(k+1, j).value()) continue;
                                    else break;
                                }
                                else continue;
                            }
                        } else continue;
                    } else continue;
                }
            }

            for(j=0;j<board.size();j++) {
                cnt = 0;
                for(i=0;i<board.size();i++) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(cnt, j, t);
                        cnt++;
                    }
                }
            }
        }
        else if(side == Side.SOUTH)
        {
            changed = false;
            for(i=0;i<board.size();i++) {
                for(j=0;j<board.size()-1;j++) {
                    if((board.tile(i, j) == null) && (board.tile(i, j+1) != null)) changed = true;
                    if((board.tile(i, j) != null) && (board.tile(i, j+1) != null) && (board.tile(i, j).value() == board.tile(i, j+1).value())) changed = true;
                }
            }

            for(i=0;i<board.size();i++) {
                cnt = 0;
                for(j=0;j<board.size();j++) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(i, cnt, t);
                        cnt++;
                    }
                }
            }

            for(i=0;i<board.size();i++) {
                for(j=0;j<board.size()-1;j++) {
                    if((board.tile(i, j) != null) && (board.tile(i, j+1) != null)) {
                        if(board.tile(i, j).value() == board.tile(i, j+1).value()) {
                            Tile t = board.tile(i, j+1);
                            board.move(i, j, t);
                            sum = board.tile(i, j).value();
                            score+=sum;
                            for(k=0;k<board.size()-1;k++)
                            {
                                if ((board.tile(i, k) != null) && (board.tile(i, k+1) != null)) {
                                    if (board.tile(i, k).value() == board.tile(i, k+1).value()) continue;
                                    else break;
                                }
                                else continue;
                            }
                        }
                        else continue;
                    }
                    else continue;
                }
            }

            for(i=0;i<board.size();i++) {
                cnt = 0;
                for(j=0;j<board.size();j++) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(i, cnt, t);
                        cnt++;
                    }
                }
            }
        }
        else // North
        {
            changed = false;
            for(i=0;i<board.size();i++) {
                for(j=board.size()-1;j>0;j--) {
                    if((board.tile(i, j) == null) && (board.tile(i, j-1) != null)) changed = true;
                    if((board.tile(i, j) != null) && (board.tile(i, j-1) != null) && (board.tile(i, j).value() == board.tile(i, j-1).value())) changed = true;
                }
            }

            for(i=0;i<board.size();i++) {
                cnt = 0;
                for(j=board.size()-1;j>=0;j--) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(i, board.size()-1-cnt, t);
                        cnt++;
                    }
                }
            }

            for(i=0;i<board.size();i++) {
                for(j=board.size()-1;j>0;j--) {
                    if((board.tile(i, j) != null) && (board.tile(i, j-1) != null)) {
                        if(board.tile(i, j).value() == board.tile(i, j-1).value()) {
                            Tile t = board.tile(i, j-1);
                            board.move(i, j, t);
                            sum = board.tile(i, j).value();
                            score+=sum;
                            for(k=board.size()-1;k>0;k--)
                            {
                                if ((board.tile(i, k) != null) && (board.tile(i, k-1) != null)) {
                                    if (board.tile(i, k).value() == board.tile(i, k-1).value()) continue;
                                    else break;
                                }
                                else continue;
                            }
                        }
                        else continue;
                    }
                    else continue;
                }
            }

            for(i=0;i<board.size();i++) {
                cnt = 0;
                for(j=board.size()-1;j>=0;j--) {
                    if(board.tile(i, j) != null) {
                        Tile t = board.tile(i, j);
                        board.move(i, board.size()-1-cnt, t);
                        cnt++;
                    }
                }
            }
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int i, j;
        for(i=0 ; i<b.size() ; i++)
            for(j=0 ; j<b.size() ; j++)
                if(b.tile(i, j) == null) return true;
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int i, j;
        for(i=0 ; i<b.size() ; i++)
            for(j=0 ; j<b.size() ; j++)
            {
                if(b.tile(i, j) == null) continue;
                if(b.tile(i, j).value() == 2048) return true;
            }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        int i, j, u, d, r, l, ori, uv, dv, rv, lv;
        for(i=0 ; i<b.size() ; i++)
            for(j=0 ; j<b.size() ; j++)
            {
                u = i-1;
                d = i+1;
                r = j+1;
                l = j-1;
                if(b.tile(i, j) == null) return true;
                else
                {
                    ori = b.tile(i, j).value();
                    if((u>=0) && (b.tile(u, j)!=null)) uv = b.tile(u, j).value(); else uv = 0;
                    if((l>=0) && (b.tile(i, l)!=null)) lv = b.tile(i, l).value(); else lv = 0;
                    if((d<b.size()) && (b.tile(d, j)!=null)) dv = b.tile(d, j).value(); else dv = 0;
                    if((r<b.size()) && (b.tile(i, r)!=null)) rv = b.tile(i, r).value(); else rv = 0;
                    if(ori==uv||ori==dv||ori==rv||ori==lv) return true;
                }
            }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
