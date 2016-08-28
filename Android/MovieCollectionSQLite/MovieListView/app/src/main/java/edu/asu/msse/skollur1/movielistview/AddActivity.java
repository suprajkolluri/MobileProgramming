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
 * @version March 30, 2016
 */

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    EditText titleView;
    EditText yearView;
    EditText ratedView;
    EditText releasedView;
    EditText runtimeView;
    Spinner genreView;
    EditText actorsView;
    EditText plotView;
    EditText posterView;

    SearchView searchView;
    Menu menu;
    String searchString;
    List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        titleView = (EditText) findViewById(R.id.titleET);
        yearView = (EditText) findViewById(R.id.yearET);
        ratedView = (EditText) findViewById(R.id.ratedET);
        releasedView = (EditText) findViewById(R.id.dateET);
        runtimeView = (EditText) findViewById(R.id.runtimeET);
        actorsView = (EditText) findViewById(R.id.actorsET);
        plotView = (EditText) findViewById(R.id.plotET);
        posterView = (EditText) findViewById(R.id.posterET);
        fillSpinner();
    }

    private void fillSpinner(){
        list.add("Adventure");
        list.add("Action");
        list.add("Drama");
        list.add("Comedy");
        list.add("Fantasy");
        list.add("Biography");
        list.add("Animation");

        genreView = (Spinner)findViewById(R.id.genreDD);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreView.setAdapter(dataAdapter);
    }

    public void onClickButton(View view){

        try {
            MovieDB db = new MovieDB(this);
            SQLiteDatabase crsDB = db.openDB();
            ContentValues insertValues = new ContentValues();
            insertValues.put("Title",titleView.getText().toString());
            insertValues.put("Rated", ratedView.getText().toString());
            insertValues.put("Released", releasedView.getText().toString());
            insertValues.put("Runtime",runtimeView.getText().toString());
            insertValues.put("Plot", plotView.getText().toString().replaceAll("'","''"));
            insertValues.put("Year",yearView.getText().toString() );
            insertValues.put("Genre",genreView.getSelectedItem().toString());
            insertValues.put("Actors",actorsView.getText().toString());
            insertValues.put("Poster", posterView.getText().toString());
            crsDB.insert("movie", null, insertValues);
            crsDB.close();
            db.close();

        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception inserting movie: "+
                    ex.getMessage());
        }
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivityForResult(intent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(this.getClass().getSimpleName(), "in onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        this.menu = menu;

        // setup the search in action bar
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    // OnQueryListener methods for search menu item
    public boolean onQueryTextChange(String query){
        //android.util.Log.d(this.getClass().getSimpleName(), "in onQueryTextChange: " + query);
        return false;
    }

    public boolean onQueryTextSubmit(String query){
        android.util.Log.d(this.getClass().getSimpleName(), "in onQueryTextSubmit: " + query);
        this.searchString = query;
        //MenuItemCompat.collapseActionView((MenuItem)menu.findItem(R.id.action_search));
        searchView.clearFocus();
        android.util.Log.d(this.getClass().getSimpleName(), "Search string is " + this.searchString);
        new MovieAsyncTask().execute(new Info(this.searchString, this));
        return false;
    }

    private String getMovie(String movie){
        String jsonString = new String();
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://www.omdbapi.com/?t="+movie.replaceAll(" ", "%20")+"&y=&plot=short&r=json");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.setDoOutput(true);

            urlConnection.connect();

            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

            char[] buffer = new char[1024];

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            jsonString = sb.toString();

            android.util.Log.d(this.getClass().getSimpleName(), "JSON Response->" + jsonString);
        } catch (Exception e) {
            android.util.Log.w(this.getClass().getSimpleName(), "Exception making http connection: " +
                    e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonString;
    }

    private class MovieAsyncTask extends AsyncTask<Info, Void, Info> {
        @Override
        protected Info doInBackground(Info... info) {

            String response =  getMovie(info[0].movie);
            info[0].response = response;
            return info[0];
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Info info) {
            MovieDescription des = new MovieDescription(info.response);
            info.parent.titleView.setText(des.title);
            info.parent.yearView.setText(des.year);
            info.parent.ratedView.setText(des.rated);
            info.parent.releasedView.setText(des.released);
            info.parent.runtimeView.setText(des.runtime);
            int index = list.indexOf(des.genre.split(",")[0].trim());
            info.parent.genreView.setSelection(index);
            info.parent.posterView.setText(des.poster);
            info.parent.actorsView.setText(des.actors);
            info.parent.plotView.setText(des.plot);
        }
    }

    private class Info{
        String movie;
        AddActivity parent;
        String response;
        Info(String movie, AddActivity parent){
            this.movie = movie;
            this.parent = parent;
        }
    }
}
