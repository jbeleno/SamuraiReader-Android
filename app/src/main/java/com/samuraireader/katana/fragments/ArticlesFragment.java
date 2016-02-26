package com.samuraireader.katana.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.samuraireader.katana.R;
import com.samuraireader.katana.adapters.ArticlesAdapter;
import com.samuraireader.katana.models.ArticlesEntry;
import com.samuraireader.katana.util.AppSamuraiReader;
import com.samuraireader.katana.util.Message;
import com.samuraireader.katana.util.MyVolley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticlesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticlesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticlesFragment extends Fragment{
    private static final String ARG_SECTION = "section";
    private static final String ARG_LINK = "link";

    private static final String PARAM_OFFSET = "offset";
    private static final String PARAM_TAG = "tag";

    private static final String STR_STATUS = "status";
    private static final String STR_OK = "OK";
    private static final String STR_ARTICLES = "articles";
    private static final String STR_TITLE = "title";
    private static final String STR_DESCRIPTION = "description";
    private static final String STR_LINK = "link";
    private static final String STR_JOURNAL = "journal";
    private static final String STR_CATEGORIES = "categories";

    private String section;
    private String link;
    private int offset = 0;
    boolean loadingLocked = false;

    private ProgressDialog progress;
    private SwipeRefreshLayout swipeLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ArticlesEntry> mDataset = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param section This is the section that the user selected to get the
     *                articles that the user wants.
     * @param link This is the URL for loading the data of each section
     * @return A new instance of fragment ArticlesFragment.
     */
    public static ArticlesFragment newInstance(String section, String link) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, section);
        args.putString(ARG_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            section = getArguments().getString(ARG_SECTION);
            link = getArguments().getString(ARG_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.articles_data);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Load articles depending on the section choosen
        loadArticles();

        // Change the title
        mListener.onTitleChange(section);

        // Setting the action when the user reload the data
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_articles);
        swipeLayout.setOnRefreshListener(new RefreshListener());

        return view;
    }


    // Using Volley library I get the articles from the URL passed in the arguments
    public void loadArticles(){
        if(offset == 0) {
            progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                    getString(R.string.app_name), true);
        }

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                link,
                successInLoadArticles(),
                errorInLoadArticles()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(PARAM_OFFSET, String.valueOf(offset));
                params.put(PARAM_TAG, section);
                return params;
            }
        };
        queue.add(myReq);
    }

    // This is the listener when everything seems to be OK in loading articles from
    // the link provided in the parameters
    private Response.Listener<String> successInLoadArticles() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try{
                    JSONObject jsonObj = new JSONObject(response);
                    if(jsonObj.get(STR_STATUS).equals(STR_OK)){

                        JSONArray articles = jsonObj.getJSONArray(STR_ARTICLES);
                        if(articles.length() != 0) {
                            for (int i = 0; i < articles.length(); i++) {

                                JSONObject article = articles.getJSONObject(i);

                                String link = article.getString(STR_LINK);
                                String title = article.getString(STR_TITLE);
                                String description = article.getString(STR_DESCRIPTION);
                                String journal = article.getString(STR_JOURNAL);
                                JSONArray categories = article.getJSONArray(STR_CATEGORIES);
                                String tag = "";
                                if(categories != null){
                                    tag = categories.get(0).toString();
                                }

                                mDataset.add(new ArticlesEntry(link, title, description, journal, tag));
                            }
                            if (offset == 0) {
                                mAdapter = new ArticlesAdapter(mDataset, new ArticlesAdapter.MyFragmentRedirecter() {
                                    @Override
                                    public void loadFragment(String link) {
                                        mListener.loadFragment(link);
                                    }
                                });
                                mRecyclerView.setAdapter(mAdapter);

                                mRecyclerView.addOnScrollListener(new ArticlesScrollListener());
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        loadingLocked = false;
                    }else{
                        Message.show(getString(R.string.error_server), getActivity());
                        loadingLocked = false;
                    }
                }catch (JSONException e){
                    Message.show(getString(R.string.error_server), getActivity());
                    loadingLocked = false;
                }
            }
        };
    }

    // When there's an error with the web services this shit notify to users
    // Mostly there are error 404 or 500
    private Response.ErrorListener errorInLoadArticles() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progress.dismiss();
                loadingLocked = false;
                Message.show(getString(R.string.error_conection), getActivity());
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.onTitleChange("");
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {

        // This method is for changing the title in the activity
        void onTitleChange(String title);

        // Load another Fragment
        void loadFragment(String link);
    }

    /**
     * This class handles the reload action when the user swipe down,
     * basically stop the reload animation after a second and clean the
     * articles dataset and the offset is set in 0, loading again the articles
     * under this criteria
     */
    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {

                    swipeLayout.setRefreshing(false);
                    mDataset = new ArrayList<>();
                    offset = 0;

                    loadArticles();
                }
            }, 1000);
        }
    }


    /**
     * This class is used to listen the scroll and when is on the final of the content
     * then try to load more information of news articles.
     */
    private class ArticlesScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(dy > 0) //check for scroll down
            {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!loadingLocked)
                {
                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                        offset++;
                        loadingLocked = true;
                        Log.d(AppSamuraiReader.getTag(), String.valueOf(offset));
                        loadArticles();
                    }
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }
    }
}
