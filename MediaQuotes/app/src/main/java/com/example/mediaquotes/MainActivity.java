package com.example.mediaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinkedList<String> mQuotesText;
    private boolean[] mBooksSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuotesText = new LinkedList<String>();
        mBooksSelected = new boolean[3];

        Button dune = findViewById(R.id.dune);
        Button bb = findViewById(R.id.breakingbad);
        Button got = findViewById(R.id.got);

        dune.setOnClickListener(this);
        bb.setOnClickListener(this);
        got.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch(v.getId()) {
            case R.id.dune:
                Toast.makeText(this, "Dune is Selected", Toast.LENGTH_SHORT).show();
                mBooksSelected[0] = true;
                break;
            case R.id.breakingbad:
                Toast.makeText(this, "Breaking is Selected", Toast.LENGTH_SHORT).show();
                mBooksSelected[1] = true;
                break;
            case R.id.got:
                Toast.makeText(this, "Game of Thrones is Selected", Toast.LENGTH_SHORT).show();
                mBooksSelected[2] = true;
                break;
        }
    }

    public void processInput(View view) throws InterruptedException, ExecutionException {
        Intent intent = new Intent(this, OutputActivity.class);
        FetchQuotes fetch = new FetchQuotes(mQuotesText, mBooksSelected);
        fetch.execute().get();
        intent.putExtra("key", mQuotesText);
        startActivity(intent);
    }
}