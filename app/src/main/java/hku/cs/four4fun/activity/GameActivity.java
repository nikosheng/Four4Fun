package hku.cs.four4fun.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Stack;

import hku.cs.four4fun.R;
import hku.cs.four4fun.model.Chess;
import hku.cs.four4fun.model.ChessBoard;
import hku.cs.four4fun.util.ChessTuple;

import static hku.cs.four4fun.R.id.restart;

public class GameActivity extends AppCompatActivity {

    public ChessBoard board = null;

    private ImageView gameViews[][];
    private Chess[][] chessBoardArray;
    private Button restartButton;
    private Button retractButton;
    private TextView player1Name;
    private TextView player2Name;
    private ImageView player1Chess;
    private ImageView player2Chess;
    private int chessCountTracer;
    private Stack<ChessTuple<Integer, Integer, String>> retractStack;
    private HashSet<ChessTuple<Integer, Integer, String>> winChessPosSet;

    private boolean gameover = false;
    private String turnRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initChessBoard();
    }

    public void initChessBoard() {
        board = new ChessBoard();
        chessBoardArray = board.getChessBoard();

        ChessButtonListener buttonClickListener = new ChessButtonListener();

        gameViews = new ImageView[board.getBoardRow()][board.getBoardColumn()];
        gameViews[0][0] = (ImageView) findViewById(R.id.chess00);
        gameViews[1][0] = (ImageView) findViewById(R.id.chess10);
        gameViews[2][0] = (ImageView) findViewById(R.id.chess20);
        gameViews[3][0] = (ImageView) findViewById(R.id.chess30);
        gameViews[4][0] = (ImageView) findViewById(R.id.chess40);
        gameViews[5][0] = (ImageView) findViewById(R.id.chess50);

        gameViews[0][1] = (ImageView) findViewById(R.id.chess01);
        gameViews[1][1] = (ImageView) findViewById(R.id.chess11);
        gameViews[2][1] = (ImageView) findViewById(R.id.chess21);
        gameViews[3][1] = (ImageView) findViewById(R.id.chess31);
        gameViews[4][1] = (ImageView) findViewById(R.id.chess41);
        gameViews[5][1] = (ImageView) findViewById(R.id.chess51);

        gameViews[0][2] = (ImageView) findViewById(R.id.chess02);
        gameViews[1][2] = (ImageView) findViewById(R.id.chess12);
        gameViews[2][2] = (ImageView) findViewById(R.id.chess22);
        gameViews[3][2] = (ImageView) findViewById(R.id.chess32);
        gameViews[4][2] = (ImageView) findViewById(R.id.chess42);
        gameViews[5][2] = (ImageView) findViewById(R.id.chess52);

        gameViews[0][3] = (ImageView) findViewById(R.id.chess03);
        gameViews[1][3] = (ImageView) findViewById(R.id.chess13);
        gameViews[2][3] = (ImageView) findViewById(R.id.chess23);
        gameViews[3][3] = (ImageView) findViewById(R.id.chess33);
        gameViews[4][3] = (ImageView) findViewById(R.id.chess43);
        gameViews[5][3] = (ImageView) findViewById(R.id.chess53);

        gameViews[0][4] = (ImageView) findViewById(R.id.chess04);
        gameViews[1][4] = (ImageView) findViewById(R.id.chess14);
        gameViews[2][4] = (ImageView) findViewById(R.id.chess24);
        gameViews[3][4] = (ImageView) findViewById(R.id.chess34);
        gameViews[4][4] = (ImageView) findViewById(R.id.chess44);
        gameViews[5][4] = (ImageView) findViewById(R.id.chess54);

        gameViews[0][5] = (ImageView) findViewById(R.id.chess05);
        gameViews[1][5] = (ImageView) findViewById(R.id.chess15);
        gameViews[2][5] = (ImageView) findViewById(R.id.chess25);
        gameViews[3][5] = (ImageView) findViewById(R.id.chess35);
        gameViews[4][5] = (ImageView) findViewById(R.id.chess45);
        gameViews[5][5] = (ImageView) findViewById(R.id.chess55);

        gameViews[0][6] = (ImageView) findViewById(R.id.chess06);
        gameViews[1][6] = (ImageView) findViewById(R.id.chess16);
        gameViews[2][6] = (ImageView) findViewById(R.id.chess26);
        gameViews[3][6] = (ImageView) findViewById(R.id.chess36);
        gameViews[4][6] = (ImageView) findViewById(R.id.chess46);
        gameViews[5][6] = (ImageView) findViewById(R.id.chess56);

        for (int i = 0; i < board.getBoardRow(); i++) {
            for (int j = 0; j < board.getBoardColumn(); j++) {
                gameViews[i][j].setOnClickListener(buttonClickListener);
            }
        }

        for (int i = 0; i < board.getBoardRow(); i++) {
            for (int j = 0; j < board.getBoardColumn(); j++) {
                String tag = "chess" + i + j;
                gameViews[i][j].setTag(tag);
            }
        }

        restartButton = (Button) findViewById(restart);
        restartButton.setOnClickListener(new RestartButtionListener());

        retractButton = (Button) findViewById(R.id.retract);
        retractButton.setOnClickListener(new RetractButtonListener());

        turnRole = "Player1";  // Player1 Turn As Default
        chessCountTracer = 0;  // Check for the number of chess have been placed in the board
        retractStack = new Stack<ChessTuple<Integer, Integer, String>>();

        Intent menuIntent = getIntent();
        player1Name = (TextView) findViewById(R.id.player1_name);
        player2Name = (TextView) findViewById(R.id.player2_name);
        player1Chess = (ImageView) findViewById(R.id.player1_chess);
        player2Chess = (ImageView) findViewById(R.id.player2_chess);
        player1Chess.setVisibility(View.VISIBLE);
        player2Chess.setVisibility(View.INVISIBLE);
        player1Name.setText(menuIntent.getStringExtra("player1"));
        player2Name.setText(menuIntent.getStringExtra("player2"));
    }

    public void restartGame() {
        chessBoardArray = board.getChessBoard();
        turnRole = "Player1";
        player1Chess.setVisibility(View.VISIBLE);
        player2Chess.setVisibility(View.INVISIBLE);
        chessCountTracer = 0;  // Check for the number of chess have been placed in the board

        for (int i = 0; i < board.getBoardRow(); i++) {
            for (int j = 0; j < board.getBoardColumn(); j++) {
                gameViews[i][j].setImageResource(R.drawable.red);
                gameViews[i][j].setOnClickListener(new ChessButtonListener());
            }
        }

        retractStack.clear();
    }

    public void restartOrExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("Four4Fun")
                .setMessage("Do you want to restart?")
                .setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        restartGame();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Intent backIntent = new Intent();
                        backIntent.setClass(GameActivity.this, GameMenuActivity.class);
                        startActivity(backIntent);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    class RetractButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!retractStack.isEmpty()) {
                ChessTuple<Integer, Integer, String> tuple = retractStack.pop();
                int row = tuple.getRow();
                int col = tuple.getColumn();
                String role = tuple.getRole();

                gameViews[row][col].setImageResource(R.drawable.red);
                chessBoardArray[row][col].setType(0);
                turnRole = role;

                switch (turnRole) {
                    case "Player1":
                        player1Chess.setVisibility(View.VISIBLE);
                        player2Chess.setVisibility(View.INVISIBLE);
                        break;
                    case "Player2":
                        player1Chess.setVisibility(View.INVISIBLE);
                        player2Chess.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    public void setWinChessImg(HashSet<ChessTuple<Integer, Integer, String>> winChessPosSet) {
        for (ChessTuple<Integer, Integer, String> tuple : winChessPosSet) {
            int row = tuple.getRow();
            int col = tuple.getColumn();
            String role = tuple.getRole();

            gameViews[row][col].setImageResource(R.drawable.pokeball);
        }

    }

    public void forbidClickable() {
        for (int i = 0; i < board.getBoardRow(); i++) {
            for (int j = 0; j < board.getBoardColumn(); j++) {
                gameViews[i][j].setClickable(false);
            }
        }
    }

    public void gameResultDialog(String turnRole) {
        AlertDialog.Builder builder;
        View resultinfo = LayoutInflater.from(GameActivity.this).inflate(R.layout.result_info, null);
        ImageView winner_img = (ImageView) resultinfo.findViewById(R.id.winner_img);
        TextView result = (TextView) resultinfo.findViewById(R.id.result);

        switch (turnRole) {
            case "Player1":
                winner_img.setImageResource(R.drawable.player_1);
                result.setText("Winner is " + player1Name.getText());
                break;
            case "Player2":
                winner_img.setImageResource(R.drawable.player_2);
                result.setText("Winner is " + player2Name.getText());
                break;
            case "Draw":
                winner_img.setImageResource(R.drawable.psyduck);
                result.setText("Game Draw");
        }

        builder = new AlertDialog.Builder(GameActivity.this);
        builder.setView(resultinfo);

        builder.setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onVisibleBehindCanceled();
            }
        });

        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    class RestartButtionListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            restartOrExit();
        }
    }

    class ChessButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (chessCountTracer == 41) {
                gameResultDialog("Draw");
            }

            ImageView chess = (ImageView) view;

            String tag = (String) chess.getTag();

            int chess_row;
            int chess_col = Integer.parseInt(tag.substring(6, 7));

            for (int i = board.getBoardRow() - 1; i >= 0; i--) {
                if (chessBoardArray[i][chess_col].getType() == 0) {
                    chess_row = i;

                    if (turnRole.equals("Player1")) {
                        chessBoardArray[i][chess_col].setType(1);
                        gameViews[i][chess_col].setImageResource(R.drawable.pikachu);
                        if (board.isFourinRow(chess_row, chess_col, turnRole, chessBoardArray)) {
                            winChessPosSet = board.getWinChessPos();
                            gameResultDialog(turnRole);
                            setWinChessImg(winChessPosSet);
                            forbidClickable();
                            retractStack.clear();
                            break;
                        }
                        retractStack.push(new ChessTuple<Integer, Integer, String>(i, chess_col, turnRole));
                        player1Chess.setVisibility(View.INVISIBLE);
                        player2Chess.setVisibility(View.VISIBLE);
                        turnRole = "Player2";
                    } else {
                        chessBoardArray[i][chess_col].setType(2);
                        gameViews[i][chess_col].setImageResource(R.drawable.dratini);
                        if (board.isFourinRow(chess_row, chess_col, turnRole, chessBoardArray)) {
                            winChessPosSet = board.getWinChessPos();
                            gameResultDialog(turnRole);
                            setWinChessImg(winChessPosSet);
                            forbidClickable();
                            retractStack.clear();
                            break;
                        }
                        retractStack.push(new ChessTuple<Integer, Integer, String>(i, chess_col, turnRole));
                        player1Chess.setVisibility(View.VISIBLE);
                        player2Chess.setVisibility(View.INVISIBLE);
                        turnRole = "Player1";
                    }
                    chessCountTracer += 1;
                    break;
                }
            }
        }
    }


}
