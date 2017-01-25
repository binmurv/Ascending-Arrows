package com.avgolosinda.bsit.ascendingarrows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    String strName;
    TextView txtName, txtScore, txtLives, txtResult;
    ImageButton btnNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        this.init();

    }
    public void init(){
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtName = (TextView) findViewById(R.id.txtName);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtLives = (TextView) findViewById(R.id.txtLives);
        btnNewGame = (ImageButton) findViewById(R.id.btnNewGame);

        Intent game_screen = getIntent();
        System.out.println(game_screen.getStringExtra("strName"));
        System.out.println(game_screen.getStringExtra("strScore"));
        System.out.println(game_screen.getStringExtra("strLives"));

        strName = game_screen.getStringExtra("strName");

        txtName.setText(strName);
        txtScore.setText(game_screen.getStringExtra("strScore"));
        txtResult.setText(game_screen.getStringExtra("strResult"));
        txtLives.setText(game_screen.getStringExtra("strLives"));


    }

    public void btnNewGame_click(View view){
        Intent name_screen = new Intent(this, NameActivity.class);
        startActivity(name_screen);
        finish();
    }

    public void btnRestart_click(View view){
        Intent game_screen = new Intent(this, GameActivity.class);

        game_screen.putExtra("strName", strName );
        startActivity(game_screen);
        finish();
    }
}
