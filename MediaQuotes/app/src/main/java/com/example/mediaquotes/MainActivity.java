package com.example.mediaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinkedList<String> mQuotesText;
    private boolean[] mBooksSelected;
    private String mWordInput;
    EditText inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuotesText = new LinkedList<String>();
        mBooksSelected = new boolean[3];
        inputField = (EditText) findViewById(R.id.inputField);

        Button dune = findViewById(R.id.dune);
        Button bb = findViewById(R.id.breakingbad);
        Button got = findViewById(R.id.got);

        dune.setOnClickListener(this);
        bb.setOnClickListener(this);
        got.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        CheckBox check = (CheckBox) v;
        switch(v.getId()) {
            case R.id.dune:
                if(check.isChecked())
                    Toast.makeText(this, "Dune is Selected", Toast.LENGTH_SHORT).show();
                mBooksSelected[0] = check.isChecked();
                break;
            case R.id.breakingbad:
                if(check.isChecked())
                    Toast.makeText(this, "Breaking Bad is Selected", Toast.LENGTH_SHORT).show();
                mBooksSelected[1] = check.isChecked();
                break;
            case R.id.got:
                if(check.isChecked())
                    Toast.makeText(this, "Game of Thrones is Selected", Toast.LENGTH_SHORT).show();
                mBooksSelected[2] = check.isChecked();
                break;
        }
    }

    public void processInput(View view) throws InterruptedException, ExecutionException {
        String mWordInput = inputField.getEditableText().toString();
        Log.d("The input is:", mWordInput);
        if(!mBooksSelected[0] && !mBooksSelected[1] && !mBooksSelected[2]){
            Toast.makeText(this, "Must select at least 1 source of quotes", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, OutputActivity.class);
        FetchQuotes fetch = new FetchQuotes(mQuotesText, mBooksSelected, mWordInput);
        fetch.execute().get();
        intent.putExtra("key", mQuotesText);
        startActivity(intent);
    }
}