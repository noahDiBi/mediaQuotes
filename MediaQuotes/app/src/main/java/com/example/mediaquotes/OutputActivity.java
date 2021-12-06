package com.example.mediaquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

public class OutputActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private QuotesAdapter mAdapter;
    private LinkedList<String> mQuoteList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        //Gets Linked List of quotes for recycler view
        //Thanks stackOverflow
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mQuoteList = (LinkedList<String>) extras.get("key");
        }

        //Create the data for the recyclerView
        //instantiate recyclerView
        mRecyclerView = findViewById(R.id.quoteList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //create the adapter with the context of the MainActiviy and
        //the data source
        mAdapter = new QuotesAdapter(this, mQuoteList);
        mRecyclerView.setAdapter(mAdapter);
    }
}