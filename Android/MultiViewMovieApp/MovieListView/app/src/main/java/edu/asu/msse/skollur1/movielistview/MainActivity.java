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
 * @version February 10, 2016
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ExpandableListView expandableView;
    public MovieListAdapter movieListAdapter;
    MovieLibrary movieLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableView = (ExpandableListView)findViewById(R.id.expandableView);
        movieLibrary = new MovieLibrary(getAssets());
        Intent intent = getIntent();
        String desString = intent.getStringExtra("des");
        String title = intent.getStringExtra("title");
        String genre = intent.getStringExtra("genre");
        if(desString!=null){
            MovieDescription des = new MovieDescription(desString);
            if(!movieLibrary.genreMovieList.containsKey(des.genre)){
                movieLibrary.genreMovieList.put(des.genre, new ArrayList<MovieDescription>());
            }
            List<MovieDescription> list = movieLibrary.genreMovieList.get(des.genre);
            list.add(des);
        }
        if(title !=null && genre!=null){
            List<MovieDescription> list = movieLibrary.genreMovieList.get(genre);
            int count = 0;
            for(MovieDescription curDes: list){
                if(curDes.title.equals(title)){
                    break;
                }
                count ++;
            }
            list.remove(count);
            if(list.size() == 0){
                movieLibrary.genreMovieList.remove(genre);
            }
        }
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movieLibrary);
        expandableView.setAdapter(movieListAdapter);
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
                intent.putStringArrayListExtra("genreList", MovieLibrary.genreList);
                this.startActivityForResult(intent, 1);
            default:
                super.onOptionsItemSelected(menuItem);
        }

        return true;
    }
}
