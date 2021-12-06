package com.example.mediaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private LinkedList<String> mQuotesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","Test");
        mQuotesText = new LinkedList<String>();
    }

    public void processInput(View view){

        Intent intent = new Intent(this, OutputActivity.class);
        FetchQuotes fetch = new FetchQuotes(mQuotesText);
        fetch.execute();
        Log.d("MainActivity", "bruh");
        intent.putExtra("key", mQuotesText);
        startActivity(intent);

    }

}