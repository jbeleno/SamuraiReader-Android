/**
 * Title: SamuraiReader Android
 * Version: 1.0
 * Author: Juan Sebastián Beleño Díaz
 * Email: jsbeleno@gmail.com
 * Date: 27/02/2016
 *
 * This class is the fragment that contains the single article information
 * that basically is loading a web page and shows the progress for UX
 */
package com.samuraireader.katana.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.samuraireader.katana.R;

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

        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        final WebView articlePage = (WebView) rootView.findViewById(R.id.webview_article);

        articlePage.getSettings().setJavaScriptEnabled(true);
        articlePage.loadUrl(mLink);
        articlePage.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
        articlePage.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
        articlePage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        return rootView;
    }
}
