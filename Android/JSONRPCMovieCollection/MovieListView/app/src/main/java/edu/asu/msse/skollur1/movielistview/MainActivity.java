package edu.asu.msse.skollur1.movielistview;
/*
 * Copyright 2016 Supraj Kolluri,
 *
 *
 * The contents of the file can only be used for the purpose of grading and reviewing.
 * The instructor and the University have the right to build and evaluate the
 * software package for the purpose of determining the grade and program assessment.
 *
 *
 * @author Supraj Kolluri mailto:supraj.kolluri@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version March 15, 2016
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ExpandableListView expandableView;
    public MovieListAdapter movieListAdapter;
    String url;

    public LinkedHashMap<String, List<String>> genreMovieMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = this.getString(R.string.defaulturl);
        setContentView(R.layout.activity_main);
        expandableView = (ExpandableListView)findViewById(R.id.expandableView);
        genreMovieMap = new LinkedHashMap<>();
        Intent intent = getIntent();
        String desString = intent.getStringExtra("des");
        String title = intent.getStringExtra("title");
        if(desString!=null){
            try{
                MethodInformation mi = new MethodInformation(this, url,"add",
                        new String[]{desString});
                AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
            } catch (Exception ex){
                android.util.Log.w(this.getClass().getSimpleName(),"Exception adding movie: "+
                        ex.getMessage());
            }
        }
        if(title !=null){
            try{
                MethodInformation mi = new MethodInformation(this, url,"remove",
                        new String[]{title});
                AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
            } catch (Exception ex){
                android.util.Log.w(this.getClass().getSimpleName(),"Exception deleting movie: "+
                        ex.getMessage());
            }
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        try{
            MethodInformation mi = new MethodInformation(this, url,"getGenreMovieMap",
                    new String[]{});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception getting genre movie map: "+
                    ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.addMenu:
                Intent intent = new Intent(this, AddActivity.class);
                this.startActivityForResult(intent, 1);
            default:
                super.onOptionsItemSelected(menuItem);
        }

        return true;
    }
}
