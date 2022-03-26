package com.example.gabrielsflashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    private TextView flashcardQuestion;
    private TextView correctAnswer;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int cardIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView add_icon = (ImageView) findViewById(R.id.add_icon_imageView);
//        ImageView next_button = (ImageView) findViewById(R.id.flashcard_next_button);
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

//        next_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openAddCardActivity();
//            }
//        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        Flashcard firstCard = allFlashcards.get(0);
        flashcardQuestion.setText(firstCard.getQuestion());
        correctAnswer.setText(firstCard.getAnswer());

        findViewById(R.id.flashcard_next_button).setOnClickListener(new View.OnClickListener() { // add button to main xml
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0){
                    return;
                }

                cardIndex += 1;

                if (cardIndex >= allFlashcards.size()){
                    Snackbar.make(view,
                            "You've reached the end of the cards! Going back to the start",
                            Snackbar.LENGTH_SHORT
                            ).show();

                    cardIndex = 0;

                }


                Flashcard currentcard = allFlashcards.get(cardIndex);
                flashcardQuestion.setText(currentcard.getQuestion());
                correctAnswer.setText(currentcard.getAnswer());

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

            Flashcard flashcard = new Flashcard(string1, string2);
            flashcardDatabase.insertCard(flashcard);


            allFlashcards = flashcardDatabase.getAllCards();


        }

    }
}