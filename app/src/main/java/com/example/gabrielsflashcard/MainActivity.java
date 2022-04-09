package com.example.gabrielsflashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView correctAnswer;
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

                int cx = correctAnswer.getWidth() / 2;
                int cy = correctAnswer.getHeight() / 2;

                float finalRadius = (float) Math.hypot(cx, cy);

                Animator anim = ViewAnimationUtils.createCircularReveal(correctAnswer, cx, cy, 0f, finalRadius);


                flashcardQuestion.setVisibility(View.INVISIBLE);
                correctAnswer.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
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

        if (allFlashcards != null && allFlashcards.size() > 0) {
            Flashcard firstCard = allFlashcards.get(0);
            flashcardQuestion.setText(firstCard.getQuestion());
            correctAnswer.setText(firstCard.getAnswer());
        }

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


                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing

                        flashcardQuestion.startAnimation(rightInAnim);


                        Flashcard currentcard = allFlashcards.get(cardIndex);
                        flashcardQuestion.setText(currentcard.getQuestion());
                        correctAnswer.setText(currentcard.getAnswer());

                        flashcardQuestion.setVisibility(View.VISIBLE);
                        correctAnswer.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                flashcardQuestion.startAnimation(leftOutAnim);

            }
        });



    }

    public void openAddCardActivity(){
        Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
        startActivityForResult(intent, 100);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

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