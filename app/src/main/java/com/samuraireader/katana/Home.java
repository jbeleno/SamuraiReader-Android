package com.samuraireader.katana;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.samuraireader.katana.fragments.ArticlesFragment;
import com.samuraireader.katana.util.AppSamuraiReader;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   ArticlesFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTitleChange(String title) {
        final ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
            actionBar.setTitle(title);
    }

    @Override
    public void loadFragment(String link) {
        Log.d(AppSamuraiReader.getTag(), link);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String link = "http://nodejs-jbeleno.rhcloud.com/articles/list";
        String section = "Esportes";

        if (id == R.id.nav_sports) {

        } else if (id == R.id.nav_politics) {

        } else if (id == R.id.nav_tech) {

        } else if (id == R.id.nav_international) {

        } else if (id == R.id.nav_economy) {

        } else if (id == R.id.nav_daily) {

        }

        // The fragment is inflated depending on section selected by the user
        Fragment fragment = ArticlesFragment.newInstance(section, link);

        FragmentManager fragmentoManager = getSupportFragmentManager();
        fragmentoManager.beginTransaction()
                .add(R.id.content_frame, fragment, null)
                .addToBackStack(null)
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
