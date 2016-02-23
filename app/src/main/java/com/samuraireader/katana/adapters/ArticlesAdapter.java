package com.samuraireader.katana.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.samuraireader.katana.models.ArticlesEntry;

import java.util.List;

public class ArticlesAdapter extends BaseAdapter {
    private Activity activity;
    private List<ArticlesEntry> articles;


    /**
     * This is a constructor where we took some data from the frament
     * and we use it to setup the needed to start a ListView of articles
     * items.
     *
     * @param activity: The activity where the adapter it's being called
     * @param articles: An array of Articles
     */
    public ArticlesAdapter(Activity activity,
                         List<ArticlesEntry> articles){
        this.activity = activity;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
