package com.example.gabrielsflashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView flashcardQuestion;
    private TextView correctAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView add_icon = (ImageView) findViewById(R.id.add_icon_imageView);
        flashcardQuestion = (TextView) findViewById(R.id.flashcard_question_textview);
        correctAnswer = (TextView) findViewById(R.id.flashcard_answer_textview);


        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                correctAnswer.setVisibility(View.VISIBLE);
            }
        });

        correctAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                correctAnswer.setBackgroundColor(getResources().getColor(R.color.light_green));
//                correctAnswer.setTextColor(getResources().getColor(R.color.brown));

                flashcardQuestion.setVisibility(View.VISIBLE);
                correctAnswer.setVisibility(View.INVISIBLE);
            }
        });

        add_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCardActivity();
            }
        });

    }

    public void openAddCardActivity(){
        Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == 100) {

            String string1 = data.getExtras().getString("QUESTION_KEY");
            String string2 = data.getExtras().getString("ANSWER_KEY");
            flashcardQuestion.setText(string1);
            correctAnswer.setText(string2);
        }

    }
}