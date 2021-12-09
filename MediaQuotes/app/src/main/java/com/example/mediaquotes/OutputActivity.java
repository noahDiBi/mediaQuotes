package com.example.mediaquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class OutputActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private QuotesAdapter mAdapter;
    private LinkedList<String> mQuoteList = new LinkedList<>();
    private LinkedList<String> mNamesList = new LinkedList<>();
    private LinkedList<String> mMediaList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        //Gets Linked List of quotes for recycler view
        //Thanks stackOverflow
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            ArrayList<String> list = (ArrayList<String>) extras.get("quotes");
            for(int i = 0; i < list.size(); i++)
                mQuoteList.add(list.get(i));
            list = (ArrayList<String>) extras.get("names");
            for(int i = 0; i < list.size(); i++)
                mNamesList.add(list.get(i));
            list = (ArrayList<String>) extras.get("media");
            for(int i = 0; i < list.size(); i++)
                mMediaList.add(list.get(i));
        }
        else
            Log.d("OutputActivity", "null");

        //Create the data for the recyclerView
        //instantiate recyclerView
        mRecyclerView = findViewById(R.id.quoteList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //create the adapter with the context of the MainActiviy and
        //the data source
        mAdapter = new QuotesAdapter(this, mQuoteList, mNamesList, mMediaList);
        mRecyclerView.setAdapter(mAdapter);
    }
}