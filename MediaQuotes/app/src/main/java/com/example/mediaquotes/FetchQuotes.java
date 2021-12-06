package com.example.mediaquotes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchQuotes extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mQuotesText;

    FetchQuotes(TextView quotesText) {
        this.mQuotesText = new WeakReference<>(quotesText);
    }

    protected String getQuoteInfo() throws IOException {
        //Google Books API URL
        String apiURL = "https://quotable.io/quotes?page=1";

        //Make connection to API
        URL requestURL = new URL(apiURL);
        HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        //Receive the response
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //Create a String with the reponse
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        String jsonString = builder.toString();
        Log.d("FetchQuoteTagJsonString", jsonString);
        return jsonString;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("FetQuoteTag","Inside Fetchquote thread");
        String jsonString = null;
        //method that connects to API throws an exception
        //must use try catch block to call it
        try {
            jsonString = getQuoteInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        //this method updates the UI
        //updates the TextViews
        super.onPostExecute(s);
        String quotes = "";
        JSONObject jsonObject = null;
        JSONArray itemsArray = null;
        try {
            //convert jsonString to jsonObject
            jsonObject = new JSONObject(s);
            //get json array
            itemsArray = jsonObject.getJSONArray("results");
            //loop through array until you find an author and title
            for (int i=0;i < itemsArray.length(); i++) {
                // Get a json object from array
                JSONObject quoteObj = itemsArray.getJSONObject(i);
                //get volumeInfo key
                String quote = quoteObj.getString("content");

                //volumeInfo object has title and author string
                quotes += quote+"\n";
                Log.d("FetchQuote","quote is "+quote);
                Log.d("FetchQuote", "quotes is "+quotes);

            }
            mQuotesText.get().setText(quotes);
        } catch (Exception e) {
            mQuotesText.get().setText("No results");
            e.printStackTrace();
        }
    }
}
