package edu.asu.msse.skollur1.movielistview;

import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

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
public class MovieLibrary {
    public static ArrayList<String> genreList;
    public static HashMap<String, ArrayList<MovieDescription>> genreMovieList = null;
    private JSONObject jo;

    public MovieLibrary(AssetManager assets){
        try {
            if(genreMovieList == null) {
                genreMovieList = new LinkedHashMap<>();
                InputStream io = assets.open("movies.json");
                byte[] buffer = new byte[io.available()];
                io.read(buffer);
                io.close();
                String json = new String(buffer, "UTF-8");
                createLibrary(json);
            }
        } catch (IOException e) {
            android.util.Log.w(this.getClass().getSimpleName(),e.getMessage());
        }
    }

    public MovieLibrary(String str){
        createLibrary(str);
    }

    private void createLibrary(String str){
        try {
            jo = new JSONObject(str);
            Iterator<String> keys = jo.keys();
            MovieDescription movieDescription;
            ArrayList<MovieDescription> list;
            String genre;
            while(keys.hasNext()){
                String movieName = keys.next();
                movieDescription = new MovieDescription(jo.get(movieName).toString());
                genre = movieDescription.genre;
                if(genreMovieList.containsKey(genre)){
                    list  = genreMovieList.get(genre);
                }else {
                    list = new ArrayList<>();
                }
                list.add(movieDescription);
                genreMovieList.put(genre, list);
                genreList = new ArrayList<>(genreMovieList.keySet());
            }
        } catch (JSONException e) {
            android.util.Log.w(this.getClass().getSimpleName(), e.getMessage());
        }
    }
}
