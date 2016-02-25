package com.samuraireader.katana.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samuraireader.katana.R;
import com.samuraireader.katana.models.ArticlesEntry;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    private List<ArticlesEntry> articles;
    MyFragmentRedirecter mListener;

    // This is just a reference class to the item layout used to inflate in the
    // ListView, this is done for performace and memory reasons, also is created
    // a listener of clicks on the elements
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle, mDescription;
        public MyViewHolderClicks mListener;

        public ViewHolder(View v, MyViewHolderClicks mListener) {
            super(v);
            this.mListener = mListener;
            mTitle = (TextView) v.findViewById(R.id.title);
            mDescription = (TextView) v.findViewById(R.id.description);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onHolderClick(getAdapterPosition());
        }

        public interface MyViewHolderClicks {
            void onHolderClick(int position);
        }
    }

    // A nice constructor to get the articles
    public ArticlesAdapter(List<ArticlesEntry> articles, MyFragmentRedirecter mListener) {
        this.articles = articles;
        this.mListener = mListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articles_item, parent, false);

        // If we won't change view's size, margins, paddings and layout parameters
        // return directly
        return new ViewHolder(v, new ViewHolder.MyViewHolderClicks() {
            @Override
            public void onHolderClick(int position) {
                String link = articles.get(position).getLink();
                mListener.loadFragment(link);
            }
        });
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTitle.setText(articles.get(position).getTitle());
        holder.mDescription.setText(articles.get(position).getDescription());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // This interface is used to comunicate a load of new fragments
    public interface MyFragmentRedirecter {
        void loadFragment(String link);
    }
}
