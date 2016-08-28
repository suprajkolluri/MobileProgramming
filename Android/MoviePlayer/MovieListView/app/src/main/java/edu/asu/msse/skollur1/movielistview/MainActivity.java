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
 * @version April 24, 2016
 */
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ExpandableListView expandableView;
    public LinkedHashMap<String, List<String>> genreMovieMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableView = (ExpandableListView)findViewById(R.id.expandableView);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadFields();
    }

    private void loadFields(){
        try{
            genreMovieMap = new LinkedHashMap<>();
            MovieDB movieDB = new MovieDB(this);
            SQLiteDatabase crsDB = movieDB.openDB();
            Cursor cur = crsDB.rawQuery("select title, genre from movie;",
                    new String[]{});
            while (cur.moveToNext()) {
                String title = cur.getString(0);
                String genre = cur.getString(1).split(",")[0].trim();
                if(genreMovieMap.containsKey(genre)){
                    genreMovieMap.get(genre).add(title);
                }
                else{
                    List<String> movieLi = new ArrayList<>();
                    movieLi.add(title);
                    genreMovieMap.put(genre, movieLi);
                }
            }
            cur.close();
            crsDB.close();
            movieDB.close();
            MovieListAdapter movieListAdapter = new MovieListAdapter(this);
            expandableView.setAdapter(movieListAdapter);

        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception getting movie info: "+
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
