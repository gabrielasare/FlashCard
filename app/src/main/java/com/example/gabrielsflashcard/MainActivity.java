package com.example.gabrielsflashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView correctAnswer = (TextView) findViewById(R.id.flashcard_answer_textview);
        TextView answerViewOne = (TextView) findViewById(R.id.option_one_textview);
        TextView answerViewTwo = (TextView) findViewById(R.id.option_two_textview);


        correctAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer.setBackgroundColor(getResources().getColor(R.color.light_green));
                correctAnswer.setTextColor(getResources().getColor(R.color.brown));
            }
        });

        answerViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerViewOne.setBackgroundColor(getResources().getColor(R.color.light_red));
            }
        });

        answerViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerViewTwo.setBackgroundColor(getResources().getColor(R.color.light_red));
            }
        });


    }
}