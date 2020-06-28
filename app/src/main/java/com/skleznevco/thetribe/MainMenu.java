package com.skleznevco.thetribe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    private String gameDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        RadioGroup difficultGroup = findViewById(R.id.difficulty_group);
        Button startButton = findViewById(R.id.button_start);

        difficultGroup.clearCheck();
        difficultGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        gameDifficulty = null;
                        break;
                    case R.id.difficulty_easy:
                        gameDifficulty = "EASY";
                        break;
                    case R.id.difficulty_medium:
                        gameDifficulty = "MEDIUM";
                        break;
                    case R.id.difficulty_hard:
                        gameDifficulty = "HARD";
                        break;
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameDifficulty != null) {
                    Intent intent = new Intent(MainMenu.this, GameActivity.class);
                    intent.putExtra("Difficulty", gameDifficulty);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Необходимо выбрать сложность", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
