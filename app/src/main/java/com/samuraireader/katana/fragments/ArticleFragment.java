package com.samuraireader.katana.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.samuraireader.katana.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    private static final String ARG_LINK = "link";

    private String mLink;

    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mLink is the link to access to the article page.
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleFragment newInstance(String mLink) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LINK, mLink);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLink = getArguments().getString(ARG_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_article, container, false);

        WebView articlePage = (WebView) rootView.findViewById(R.id.webview_article);

        articlePage.getSettings().setJavaScriptEnabled(true);
        articlePage .loadUrl(mLink);
        articlePage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });

        return rootView;
    }
}
