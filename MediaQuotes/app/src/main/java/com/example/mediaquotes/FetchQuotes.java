package com.example.mediaquotes;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

public class FetchQuotes extends AsyncTask<String, Void, String> {
    private WeakReference<LinkedList<String>> mQuotesText;
    private WeakReference<LinkedList<String>> mCharacters;
    private WeakReference<LinkedList<String>> mMedia;
    private boolean[] mWorksUsed;
    private String mInput;

    FetchQuotes(LinkedList<String> quotesText, LinkedList<String> names, LinkedList<String> media, boolean[] works, String word) {
        mQuotesText = new WeakReference<LinkedList<String>>(quotesText);
        mCharacters = new WeakReference<LinkedList<String>>(names);
        mMedia = new WeakReference<LinkedList<String>>(media);
        mWorksUsed = works;
        mInput = word;
    }

    public LinkedList<String> getContents(){
        return mQuotesText.get();
    }

    protected String getQuoteInfo(int work) throws IOException {
        //Google Books API URL
        String apiURL = new String();
        switch(work) {
            case 0:
                apiURL = "https://the-dune-api.herokuapp.com/quotes/342";
                break;
            case 1:
                apiURL = "https://breaking-bad-quotes.herokuapp.com/v1/quotes/78";
                break;
            case 2:
                apiURL = "https://game-of-thrones-quotes.herokuapp.com/v1/random/144";
                break;
        }

        //Make connection to API
        URL requestURL = new URL(apiURL);
        HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        //Receive the response
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //Create a String with the response
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
        ArrayList<String> jsonStrings = new ArrayList<String>();
        //method that connects to API throws an exception
        //must use try catch block to call it
        Log.d("FetchQuotes", "Attempt Read");
        try {
            for(int i = 0; i < mWorksUsed.length; i++) {
                jsonStrings.add("");
                if(mWorksUsed[i])
                    jsonStrings.set(i, getQuoteInfo(i));
                else
                    jsonStrings.set(i, null);
                switch(i){
                    case 0:
                        dune(jsonStrings.get(i));
                        break;
                    case 1:
                        breakingBad(jsonStrings.get(i));
                        break;
                    case 2:
                        gameOfThronesQuotes(jsonStrings.get(i));
                        break;
                }
            }
            if(mQuotesText.get().isEmpty())
                mQuotesText.get().add("No results found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);

    }

    private void dune(String s){
        if(s == null) return;
        JSONArray itemsArray = null;
        try {
            //convert jsonString to jsonObject
            itemsArray = new JSONArray(s);
            //loop through array until you find an author and title
            Log.d("FetchQuotes", "Entering Loop");
            for (int i=0;i < itemsArray.length(); i++) {
                // Get a json object from array
                JSONObject quoteObj = itemsArray.getJSONObject(i);
                //get quoteInfo key
                String quote = quoteObj.getString("quote");
                if(checkWordPhrase (mInput, quote)){
                    mQuotesText.get().add(quote);
                    mCharacters.get().add("Not Given");
                    mMedia.get().add("Dune");
                }
            }

        } catch (Exception e) {
            mQuotesText.get().add("No results");
            mCharacters.get().add("N/A");
            mMedia.get().add("N/A");
            e.printStackTrace();
        }
    }

    private void breakingBad(String s){
        if(s == null) return;
        JSONArray itemsArray = null;
        try {
            //convert jsonString to jsonObject
            itemsArray = new JSONArray(s);
            //loop through array until you find an author and title
            Log.d("FetchQuotes", "Entering Loop");
            for (int i=0;i < itemsArray.length(); i++) {
                // Get a json object from array
                JSONObject quoteObj = itemsArray.getJSONObject(i);
                //get quoteInfo key
                String quote = quoteObj.getString("quote");
                String author = quoteObj.getString("author");
                if(checkWordPhrase (mInput, quote)){
                    mQuotesText.get().add(quote);
                    mCharacters.get().add(author);
                    mMedia.get().add("Breaking Bad");
                }
            }

        } catch (Exception e) {
            mQuotesText.get().add("No results");
            mCharacters.get().add("N/A");
            mMedia.get().add("N/A");
            e.printStackTrace();
        }
    }

    private void gameOfThronesQuotes(String s){
        if(s == null) return;
        JSONArray itemsArray = null;
        try {
            //convert jsonString to jsonObject
            itemsArray = new JSONArray(s);
            //loop through array until you find an author and title
            Log.d("FetchQuotes", "Entering Loop");
            for (int i=0;i < itemsArray.length(); i++) {
                // Get a json object from array
                JSONObject quoteObj = itemsArray.getJSONObject(i);
                //get quoteInfo key
                String quote = quoteObj.getString("sentence");
                if(checkWordPhrase (mInput, quote)){
                    mQuotesText.get().add(quote);
                    JSONObject speaker = quoteObj.getJSONObject("character");
                    mCharacters.get().add(speaker.getString("name"));
                    mMedia.get().add("Game of Thrones");
                }
            }

        } catch (Exception e) {
            mQuotesText.get().add("No results");
            mCharacters.get().add("N/A");
            mMedia.get().add("N/A");
            e.printStackTrace();
        }
    }

    private boolean checkWordPhrase (String input, String quote) {
        if (input.contains(" ") == false) {
            boolean hasWord = Arrays.asList(quote.toLowerCase(Locale.ROOT).replaceAll("\\p{Punct}", "").split(" ")).contains(input.toLowerCase(Locale.ROOT));
            if (hasWord)
                return true;
        } else {
            boolean hasPhrase = quote.toLowerCase(Locale.ROOT).indexOf(input.toLowerCase(Locale.ROOT)) > -1;
            if (hasPhrase)
                return true;
        }
        return false;
    }

}
