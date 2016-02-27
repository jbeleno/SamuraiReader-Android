/**
 * Title: SamuraiReader Android
 * Version: 1.0
 * Author: Juan Sebastián Beleño Díaz
 * Email: jsbeleno@gmail.com
 * Date: 27/02/2016
 *
 * This class handles with the adapter of the articles items, getting an array of items
 * and setting the data in UI elements, always optimizing the performance and memory and
 * using good pratices in code.
 */
package com.samuraireader.katana.adapters;

import android.graphics.Color;
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
        public TextView mTitle, mDescription, mJournal;
        public MyViewHolderClicks mListener;
        public View mDecoration;

        public ViewHolder(View v, MyViewHolderClicks mListener) {
            super(v);
            this.mListener = mListener;
            mTitle = (TextView) v.findViewById(R.id.title);
            mDescription = (TextView) v.findViewById(R.id.description);
            mJournal = (TextView) v.findViewById(R.id.journal);
            mDecoration = v.findViewById(R.id.decoration);
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
        holder.mJournal.setText(articles.get(position).getJournal());

        String tag = articles.get(position).getTag();
        String color = getColor(tag);

        holder.mDecoration.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * This method select a color depending on the section choosen by the user
     * @param tag is the section choosen by the user
     * @return a hexadecimal color in string format
     */
    public String getColor(String tag){
        final String STR_SPORTS = "Esportes";
        final String STR_POLITICS = "Política";
        final String STR_TECH = "Tecnologia";
        final String STR_WORLD = "Internacional";
        final String STR_ECONOMY = "Economia";
        final String STR_DAILY = "Cotidiano";

        final String STR_RED = "#F44336";
        final String STR_PURPLE = "#9C27B0";
        final String STR_BLUE = "#2196F3";
        final String STR_TEAL = "#009688";
        final String STR_ORANGE = "#FF9800";
        final String STR_PINK = "#E91E63";
        final String STR_GREEN = "#4CAF50";

        switch (tag){
            case STR_SPORTS:
                return STR_RED;

            case STR_POLITICS:
                return STR_PURPLE;

            case STR_TECH:
                return STR_BLUE;

            case STR_WORLD:
                return STR_TEAL;

            case STR_ECONOMY:
                return STR_ORANGE;

            case STR_DAILY:
                return STR_PINK;

        }

        return STR_GREEN;
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
