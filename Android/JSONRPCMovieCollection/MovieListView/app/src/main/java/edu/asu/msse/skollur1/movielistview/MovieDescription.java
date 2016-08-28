package edu.asu.msse.skollur1.movielistview;
import org.json.JSONObject;

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

public class MovieDescription {
    public String title, rated, released, runtime, plot, genre, actors, year;
    public MovieDescription(){

    }
    public MovieDescription(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            title = jo.getString("Title");
            rated = jo.getString("Rated");
            released = jo.getString("Released");
            runtime = jo.getString("Runtime");
            plot = jo.getString("Plot");
            year = jo.getString("Year");
            genre = jo.getString("Genre");
            actors = jo.getString("Actors");

        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(), "error converting to/from json");
        }
    }

    public String toJsonString() {
        String ret = "";
        try {
            JSONObject jo = new JSONObject();
            jo.put("Title", title);
            jo.put("Rated", rated);
            jo.put("Released", released);
            jo.put("Runtime", runtime);
            jo.put("Plot", plot);
            jo.put("Year", year);
            jo.put("Genre", genre);
            jo.put("Actors", actors);
            ret = jo.toString();
        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(), "error converting to/from json");
        }
        return ret;
    }

    @Override
    public String toString() {
        return "MovieDescription [title=" + title + ", rated=" + rated + ", released=" + released + ", runtime="
                + runtime + ", plot=" + plot + ", year=" + year + ", genre=" + genre + ", actors=" + actors + "]";
    }

}
