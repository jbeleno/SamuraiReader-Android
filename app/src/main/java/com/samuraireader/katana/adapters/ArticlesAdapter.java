package com.samuraireader.katana.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samuraireader.katana.R;
import com.samuraireader.katana.models.ArticlesEntry;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    private List<ArticlesEntry> articles;

    // This is just a reference class to the item layout used to inflate in the
    // ListView, this is done for performace and memory reasons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mDescription;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDescription = (TextView) v.findViewById(R.id.description);
        }
    }

    // A nice constructor to get the articles
    public ArticlesAdapter(List<ArticlesEntry> articles) {
        this.articles = articles;
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
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTitle.setText(articles.get(position).getTitle());
        holder.mDescription.setText(Html.fromHtml(articles.get(position).getDescription()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }
}
