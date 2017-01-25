package com.avgolosinda.bsit.ascendingarrows;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    int MAX_NUMBER = 20;
    int SCORE_INCREMENT = 50;
    int LIVES = 3;


    int[] intAllNumbers,
            intCurrentSet;

    int intCurrentIndex,
            intCurNum,
            intTurn,
            intScore,
            intLives;

    String strName;


    TextView txtUp, txtDown, txtLeft, txtRight, txtLives, txtScore, txtName;
    ImageButton btnLeft, btnRight, btnUp, btnDown, btnEndGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.init();
    }

    public void init(){
        Intent intent = getIntent();
        this.strName = intent.getStringExtra("strName");

        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        btnUp = (ImageButton) findViewById(R.id.btnUp);
        btnDown = (ImageButton) findViewById(R.id.btnDown);

        txtLeft = (TextView) findViewById(R.id.txtLeft);
        txtRight = (TextView) findViewById(R.id.txtRight);
        txtUp = (TextView) findViewById(R.id.txtUp);
        txtDown = (TextView) findViewById(R.id.txtDown);

        btnEndGame = (ImageButton) findViewById(R.id.btnEndGame);

        this.newGame();
    }


    public void btnClick(View view){
        ImageButton btnClicked = (ImageButton) view;
        switch(view.getId()){
            case R.id.btnUp :
                this.intCurNum = Integer.parseInt(txtUp.getText().toString());
                btnUp.setEnabled(false);
                break;
            case R.id.btnDown :
                this.intCurNum = Integer.parseInt(txtDown.getText().toString());
                btnDown.setEnabled(false);
                break;
            case R.id.btnLeft :
                this.intCurNum = Integer.parseInt(txtLeft.getText().toString());
                btnLeft.setEnabled(false);
                break;
            case R.id.btnRight :
                this.intCurNum = Integer.parseInt(txtRight.getText().toString());
                btnRight.setEnabled(false);
                break;
        }
        //Compare

        if(this.intCurNum != this.intCurrentSet[this.intTurn]){

            Toast.makeText(this.getApplicationContext(), "Wrong Move!", Toast.LENGTH_SHORT).show();
            setLife(this.intLives-=1);

            newRound();

            //GAME OVER
            if(this.intLives==0){
                endGame();
            }

        }else {
            this.intTurn += 1;
        }

        if(this.intTurn==4){
            setScore(this.intScore += SCORE_INCREMENT);
            newRound();
        }
    }

    public void newGame() {

        //reset values
        setLife(LIVES);
        setScore(0);

        //display game layout

        newRound();
    }

    public void setLife(int life){
        this.intLives = life;
        this.txtLives = (TextView) findViewById(R.id.txtLives);
        this.txtLives.setText("Life: "+ this.intLives);
    }

    public void setScore(int score){
        this.intScore = score;
        this.txtScore = (TextView) findViewById(R.id.txtScore);
        this.txtScore.setText("Score: "+ this.intScore);
    }

    public void newRound() {


        //enable all buttons (arrow)

        btnLeft.setEnabled(true);
        btnRight.setEnabled(true);
        btnDown.setEnabled(true);
        btnUp.setEnabled(true);

        //reset int turn
        this.intTurn = 0;

        if (this.intCurrentIndex == 0){
            this.intAllNumbers = getNewSet();
        }

        txtUp.setText(String.format("%d", intAllNumbers[this.intCurrentIndex++]));
        txtRight.setText(String.format("%d", intAllNumbers[this.intCurrentIndex++]));
        txtDown.setText(String.format("%d", intAllNumbers[this.intCurrentIndex++]));
        txtLeft.setText(String.format("%d", intAllNumbers[this.intCurrentIndex++]));

        intCurrentSet = new int[4];
        intCurrentSet[0] = Integer.parseInt(txtUp.getText().toString());
        intCurrentSet[1] = Integer.parseInt(txtLeft.getText().toString());
        intCurrentSet[2] = Integer.parseInt(txtDown.getText().toString());
        intCurrentSet[3] = Integer.parseInt(txtRight.getText().toString());
        Arrays.sort(intCurrentSet);


        if(this.intCurrentIndex == MAX_NUMBER){
            this.intCurrentIndex = 0;
        }

    }


    public void btnEndGame_click(View view){
        endGame();
    }


    public void endGame(){

        String strResult;
        if(intLives>0) {
            strResult = "GAME OVER!";
        }else {
            strResult = "GAME OVER!";
        }

        //Notif

        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(strResult)
                .setContentText(
                    "Name: " + strName +
                    "\nLives: " + String.valueOf(intLives) +
                    "\nScore: " + String.valueOf(intScore)
            );

        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
        mBuilder.setContentIntent(pi);

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());


        //display game over screen
        Intent end_game_screen = new Intent(this, EndGameActivity.class);
        end_game_screen.putExtra("strName", strName );
        end_game_screen.putExtra("strResult", strResult );
        end_game_screen.putExtra("strScore", String.valueOf(intScore) );
        end_game_screen.putExtra("strLives", String.valueOf(intLives) );
        startActivity(end_game_screen);
        finish();

    }

    public int[] getNewSet (){
        int[] intNewSet = new int[MAX_NUMBER];
        List<Integer> intNewSetList = new ArrayList<>();

        for (int i=0; i< MAX_NUMBER; i++){
            intNewSetList.add(i+1);
        }
        Collections.shuffle(intNewSetList);
        for (int i=0; i< MAX_NUMBER; i++){
            System.out.print(intNewSetList.get(i) + ", ");
            intNewSet[i] = intNewSetList.get(i);
        }
        return intNewSet;
    }

}
