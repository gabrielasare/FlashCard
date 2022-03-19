package com.example.gabrielsflashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddCardActivity extends AppCompatActivity {

    private ImageView close_icon;
    private ImageView download_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        ImageView close_icon = (ImageView) findViewById(R.id.cancel_icon_imageView);
        ImageView download_icon = (ImageView) findViewById(R.id.download_icon_imageView);

        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        download_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                String inputQuestion = ((EditText) findViewById(R.id.questionText_editText)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.answerText_editText)).getText().toString();
                data.putExtra("QUESTION_KEY", inputQuestion);
                data.putExtra("ANSWER_KEY", inputAnswer);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 100);

    }
}