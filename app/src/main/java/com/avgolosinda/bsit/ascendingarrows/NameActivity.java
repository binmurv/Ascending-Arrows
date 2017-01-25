package com.avgolosinda.bsit.ascendingarrows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class NameActivity extends AppCompatActivity {

    EditText txtName;
    ImageButton btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        this.init();
    }

    public void init(){
        btnStart = (ImageButton) findViewById(R.id.btnStart);
        txtName = (EditText) findViewById(R.id.txtName);
    }

    public void btnStart_click (View view){
        String strName = txtName.getText().toString().trim();
        if (!isEmpty(strName)){
            Intent game_screen = new Intent(this, GameActivity.class);
            game_screen.putExtra("strName", strName);
            startActivity(game_screen);
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
        }
    }
}
