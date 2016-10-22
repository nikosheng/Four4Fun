package hku.cs.four4fun.model;

import java.util.ArrayList;

import hku.cs.four4fun.util.ChessTuple;

/**
 * Created by Niko Feng on 10/17/2016.
 */

public class ChessBoard {
    private static final int BOARD_ROW = 6;
    private static final int BOARD_COLUMN = 7;
    private Chess[][] chessBoard;
    private ArrayList<ChessTuple<Integer, Integer, String>> winChessPos =
            new ArrayList<ChessTuple<Integer, Integer, String>>();

    public static int getBoardRow() {
        return BOARD_ROW;
    }

    public static int getBoardColumn() {
        return BOARD_COLUMN;
    }

    public ChessBoard() {
        chessBoard = new Chess[BOARD_ROW][BOARD_COLUMN];
    }

    public void initBoard() {
        for (int i = 0; i < BOARD_ROW; i++) {
            for (int j = 0; j < BOARD_COLUMN; j++) {
                chessBoard[i][j] = new Chess(0);
            }
        }
    }

    public Chess[][] getChessBoard() {
        initBoard();
        return chessBoard;
    }

    public void printChessBoardArr(Chess[][] chessBoardArr) {
        for (int i = 0; i < BOARD_ROW; i++) {
            for (int j = 0; j < BOARD_COLUMN; j++) {
                System.out.print(chessBoardArr[i][j].getType() + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<ChessTuple<Integer, Integer, String>> getWinChessPos() {
        return winChessPos;
    }


    public boolean isFourinRow(int chess_row, int chess_col,
                               String turnRole, Chess[][] chessBoardArray) {
        int turnType = 0;
        int count = 0;
        boolean result = false;
        int i, j;
        winChessPos.clear();

        switch (turnRole) {
            case "Player1":
                turnType = 1;
                break;
            case "Player2":
                turnType = 2;
                break;
        }

        // Row Checking
        for (i = 0; i < BOARD_COLUMN; i++) {
            if (chessBoardArray[chess_row][i].getType() == turnType) {
                count += 1;
                winChessPos.add(new ChessTuple<Integer, Integer, String>(chess_row, i, turnRole));

                if (count == 4) {
                    result = true;
                    break;
                }
            } else {
                winChessPos.clear();
                count = 0;
            }
        }

        // Column Checking
        if (!result) {
            count = 0;

            for (i = 0; i < BOARD_ROW; i++) {
                if (chessBoardArray[i][chess_col].getType() == turnType) {
                    count += 1;
                    winChessPos.add(new ChessTuple<Integer, Integer, String>(i, chess_col, turnRole));

                    if (count == 4) {
                        result = true;
                        break;
                    }
                } else {
                    winChessPos.clear();
                    count = 0;
                }
            }
        }

        /* Diagonal Checking Start */
        if (!result) {
            count = 0;
            // Row Inc, Col Inc
            if (chess_row <= BOARD_ROW - 4 && chess_col <= BOARD_COLUMN - 4) {
                for (
                        i = chess_row, j = chess_col;
                        i <= chess_row + 3 && j <= chess_col + 3;
                        i++, j++) {
                    if (chessBoardArray[i][j].getType() == turnType) {
                        count += 1;
                        winChessPos.add(new ChessTuple<Integer, Integer, String>(i, j, turnRole));

                        if (count == 4) {
                            result = true;
                            break;
                        }
                    } else {
                        winChessPos.clear();
                        count = 0;
                    }
                }
            }
        }

        // Row Inc, Col Dec
        if (!result) {
            count = 0;
            if (chess_row <= BOARD_ROW - 4 && chess_col >= BOARD_COLUMN - 4) {
                for (
                        i = chess_row, j = chess_col;
                        i <= chess_row + 3 && j >= chess_col - 3;
                        i++, j--)

                {
                    if (chessBoardArray[i][j].getType() == turnType) {
                        count += 1;
                        winChessPos.add(new ChessTuple<Integer, Integer, String>(i, j, turnRole));

                        if (count == 4) {
                            result = true;
                            break;
                        }
                    } else {
                        winChessPos.clear();
                        count = 0;
                    }
                }
            }
        }

        // Row Dec, Col Inc
        if (!result) {
            count = 0;
            if (chess_row >= BOARD_ROW - 3 && chess_col <= BOARD_COLUMN - 4) {
                for (
                        i = chess_row, j = chess_col;
                        i >= chess_row - 3 && j <= chess_col + 3;
                        i--, j++)

                {
                    if (chessBoardArray[i][j].getType() == turnType) {
                        count += 1;
                        winChessPos.add(new ChessTuple<Integer, Integer, String>(i, j, turnRole));

                        if (count == 4) {
                            result = true;
                            break;
                        }
                    } else {
                        winChessPos.clear();
                        count = 0;
                    }
                }
            }
        }

        // Row Dec, Col Dec
        if (!result) {
            count = 0;
            if (chess_row >= BOARD_ROW - 3 && chess_col >= BOARD_COLUMN - 4) {
                for (
                        i = chess_row, j = chess_col;
                        i >= chess_row - 3 && j >= chess_col - 3;
                        i--, j--)

                {
                    if (chessBoardArray[i][j].getType() == turnType) {
                        count += 1;
                        winChessPos.add(new ChessTuple<Integer, Integer, String>(i, j, turnRole));

                        if (count == 4) {
                            result = true;
                            break;
                        }
                    } else {
                        winChessPos.clear();
                        count = 0;
                    }
                }
            }
        }
        /* Diagonal Checking End */
        return result;
    }

}
