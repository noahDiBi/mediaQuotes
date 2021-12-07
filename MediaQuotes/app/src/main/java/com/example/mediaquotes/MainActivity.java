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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinkedList<String> mQuotesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuotesText = new LinkedList<String>();

        Button dune = findViewById(R.id.dune);
        Button bb = findViewById(R.id.breakingbad);
        Button got = findViewById(R.id.got);
        Button bcs = findViewById(R.id.bettercallsaul);
        Button lucifer = findViewById(R.id.lucifer);

        dune.setOnClickListener(this);
        bb.setOnClickListener(this);
        got.setOnClickListener(this);
        bcs.setOnClickListener(this);
        lucifer.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch(v.getId()) {
            case R.id.dune:
                Toast.makeText(this, "Dune is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.breakingbad:
                Toast.makeText(this, "Breaking is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.got:
                Toast.makeText(this, "Game of Thrones is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bettercallsaul:
                Toast.makeText(this, "Better Call Saul is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lucifer:
                Toast.makeText(this, "Lucifer is Selected", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void processInput(View view){
        Intent intent = new Intent(this, OutputActivity.class);
        FetchQuotes fetch = new FetchQuotes(mQuotesText);
        fetch.execute();
        intent.putExtra("key", mQuotesText);
        startActivity(intent);
    }
}