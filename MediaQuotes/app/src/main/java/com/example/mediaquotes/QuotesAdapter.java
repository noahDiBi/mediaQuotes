package com.example.mediaquotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.LinkedList;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesHolder>{

    private LayoutInflater mInflater;
    private LinkedList<String> mQuotes;
    private LinkedList<String> mNames;
    private LinkedList<String> mMedia;
    private Context context;

    public QuotesAdapter(Context context,
                           LinkedList<String> quoteList, LinkedList<String> names, LinkedList<String> media) {
        //use this to create the layout and link the 3 lists
        mInflater = LayoutInflater.from(context);
        mQuotes = quoteList;
        mNames = names;
        mMedia = media;
        this.context = context;
    }

    @NonNull
    @Override
    public QuotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.quote_item, parent, false);
        return new QuotesHolder(mItemView, this);
    }

    //Set all of the text in quote_item
    @Override
    public void onBindViewHolder(@NonNull QuotesHolder holder, int position) {
        String mCurrent = mQuotes.get(position);
        holder.mQuoteView.setText(mCurrent);
        mCurrent = mNames.get(position);
        holder.mNameView.setText("Spoken by: " + mCurrent);
        mCurrent = mMedia.get(position);
        holder.mMediaView.setText("Said in: " + mCurrent);
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    class QuotesHolder extends RecyclerView.ViewHolder{

        //Holder for quote_item
        private TextView mQuoteView;
        private TextView mNameView;
        private TextView mMediaView;
        private QuotesAdapter mAdapter;

        public QuotesHolder(@NonNull View itemView, QuotesAdapter adapter) {
            super(itemView);
            //Get each text view from quote_item
            mQuoteView = itemView.findViewById(R.id.quoteView);
            mNameView = itemView.findViewById(R.id.characterView);
            mMediaView = itemView.findViewById(R.id.mediaView);
            mAdapter = adapter;
        }
    }

}
