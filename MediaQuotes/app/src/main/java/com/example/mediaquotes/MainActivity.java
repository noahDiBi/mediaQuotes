package com.example.mediaquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mQuotesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuotesText = (TextView) findViewById(R.id.quotesText);
        FetchQuotes fq = new FetchQuotes(mQuotesText);
        fq.execute((String) null);
    }
}