package hku.cs.four4fun.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import hku.cs.four4fun.R;

/**
 * Created by Niko Feng on 10/19/2016.
 */

public class GameMenuActivity extends AppCompatActivity {

    private ImageView startGame = null;
    private AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startGame = (ImageView) findViewById(R.id.startgame);
        startGame.setOnClickListener(new StartGameListener());

    }

    class StartGameListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //Get the Layout Inflater
            final View playerinfo = LayoutInflater.from(GameMenuActivity.this).inflate(R.layout.player_signin, null);
            final EditText player1 = (EditText) playerinfo.findViewById(R.id.player_1);
            final EditText player2 = (EditText) playerinfo.findViewById(R.id.player_2);
            builder = new AlertDialog.Builder(GameMenuActivity.this);
            builder.setView(playerinfo);

            builder.setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent gameIntent = new Intent();
                    gameIntent.putExtra("player1", player1.getText().toString());
                    gameIntent.putExtra("player2", player2.getText().toString());
//                    gameIntent.putExtra("player1", "Player1");
//                    gameIntent.putExtra("player2", "Player2");
                    gameIntent.setClass(GameMenuActivity.this, GameActivity.class);
                    startActivity(gameIntent);
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onVisibleBehindCanceled();
                        }
                    }
            );

            builder.setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
