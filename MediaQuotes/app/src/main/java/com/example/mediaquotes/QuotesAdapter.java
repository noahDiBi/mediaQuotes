package com.example.mediaquotes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.LinkedList;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesHolder>{

    private LayoutInflater mInflater;
    private LinkedList<String> mQuotes;
    private Context context;

    public QuotesAdapter(Context context,
                           LinkedList<String> quoteList) {
        //use this to create the layout
        mInflater = LayoutInflater.from(context);
        mQuotes = quoteList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.quote_item, parent, false);
        return new QuotesHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesHolder holder, int position) {
        String mCurrent = mQuotes.get(position);
        holder.mQuoteView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    class QuotesHolder extends RecyclerView.ViewHolder{

        private TextView mQuoteView;
        private QuotesAdapter mAdapter;

        public QuotesHolder(@NonNull View itemView, QuotesAdapter adapter) {
            super(itemView);
            mQuoteView = itemView.findViewById(R.id.quoteView);
            mAdapter = adapter;
        }
    }

}
