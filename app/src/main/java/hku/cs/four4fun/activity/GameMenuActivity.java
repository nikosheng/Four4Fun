package hku.cs.four4fun.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import hku.cs.four4fun.R;
/**
 * Created by Niko Feng on 10/19/2016.
 */

public class GameMenuActivity extends AppCompatActivity {

    private ImageView startGame = null;
    private AlertDialog.Builder builder;
    private MediaPlayer mediaplayer;
    private boolean isReady;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initMediaPlayer();

        startGame = (ImageView) findViewById(R.id.startgame);
        startGame.setOnClickListener(new StartGameListener());

        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnim.setInterpolator(lin);

        if (rotateAnim != null) {
            startGame.setAnimation(rotateAnim);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaplayer != null) {
            if (mediaplayer.isPlaying()) {
                mediaplayer.stop();
            }
            mediaplayer.release();
        }
    }

    public void initMediaPlayer() {
        mediaplayer = MediaPlayer.create(this, R.raw.pokemon);

        if (mediaplayer == null) {
            return;
        }

        mediaplayer.stop();

        mediaplayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.release();
                return false;
            }
        });

        try{
            mediaplayer.prepare();
            isReady = true;

            if (!mediaplayer.isPlaying()) {
                mediaplayer.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            isReady = false;
        }

        if(isReady){
            mediaplayer.setLooping(true);
        }
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
                    String player1Name = player1.getText().toString();
                    String player2Name = player2.getText().toString();
                    if ("".equals(player1Name)) {
                        player1Name = "Player1";
                    }

                    if ("".equals(player2Name)) {
                        player2Name = "Player2";
                    }
                    gameIntent.putExtra("player1", player1Name);
                    gameIntent.putExtra("player2", player2Name);
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
