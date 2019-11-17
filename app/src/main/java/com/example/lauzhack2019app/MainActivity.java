package com.example.lauzhack2019app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button singlePlayer = findViewById(R.id.singleplayer);
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlePlayerOnClick(v);
            }
        });
        Button multiplayer = findViewById(R.id.multiplayer);
        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiPlayerOnClick(v);
            }
        });
    }

    private void singlePlayerOnClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.PLAYER_TYPE_TAG, GameActivity.PLAYER_TYPE.SINGLE_PLAYER);
        startActivity(intent);
    }

    private void multiPlayerOnClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.PLAYER_TYPE_TAG, GameActivity.PLAYER_TYPE.MULTIPLAYER);
        startActivity(intent);
    }
}
