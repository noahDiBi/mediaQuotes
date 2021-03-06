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
    //Lists and arrays for the quotes, speakers, and series used
    private LinkedList<String> mQuotesText;
    private LinkedList<String> mCharacters;
    private LinkedList<String> mMediaList;
    private boolean[] mBooksSelected;
    //Get the text input
    EditText mInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialization
        mQuotesText = new LinkedList<String>();
        mCharacters = new LinkedList<String>();
        mMediaList = new LinkedList<String>();
        mBooksSelected = new boolean[3];
        mInputField = (EditText) findViewById(R.id.inputField);
        //Obtain buttons
        Button dune = findViewById(R.id.dune);
        Button bb = findViewById(R.id.breakingbad);
        Button got = findViewById(R.id.got);
        //Setup listeners
        dune.setOnClickListener(this);
        bb.setOnClickListener(this);
        got.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        CheckBox check = (CheckBox) v;
        //Switch on/off the use of each piece of media when their button is clicked
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

    //When "Search" is clicked
    public void processInput(View view) throws InterruptedException, ExecutionException {
        String mWordInput = mInputField.getEditableText().toString();
        //Reject if no media is selected
        if(!mBooksSelected[0] && !mBooksSelected[1] && !mBooksSelected[2]){
            Toast.makeText(this, "Must select at least 1 source of quotes", Toast.LENGTH_LONG).show();
            return;
        }
        //Setup and begin
        Intent intent = new Intent(this, OutputActivity.class);
        FetchQuotes fetch = new FetchQuotes(mQuotesText, mCharacters, mMediaList, mBooksSelected, mWordInput);
        fetch.execute().get();
        intent.putExtra("quotes", mQuotesText);
        intent.putExtra("names", mCharacters);
        intent.putExtra("media", mMediaList);
        startActivity(intent);
    }
}