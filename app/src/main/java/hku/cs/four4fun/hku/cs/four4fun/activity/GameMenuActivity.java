package hku.cs.four4fun.hku.cs.four4fun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import hku.cs.four4fun.R;

/**
 * Created by Niko Feng on 10/19/2016.
 */

public class GameMenuActivity extends AppCompatActivity {

    private ImageView startGame = null;

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
            Intent gameIntent = new Intent();
            gameIntent.setClass(GameMenuActivity.this, GameActivity.class);
            startActivity(gameIntent);
        }
    }
}
