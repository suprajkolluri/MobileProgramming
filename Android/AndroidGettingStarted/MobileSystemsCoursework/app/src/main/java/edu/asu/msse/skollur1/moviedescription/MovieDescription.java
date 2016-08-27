package edu.asu.msse.skollur1.moviedescription;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * @version January 18, 2016
 */

public class MovieDescription {
    public String title, rated, released, runtime, plot;
    public int year;
    public List<String> genre = new ArrayList<String>(), actors = new ArrayList<String>();

    MovieDescription(String json) {
        try {
            JSONObject jo = new JSONObject(json);
            title = jo.getString("Title");
            rated = jo.getString("Rated");
            released = jo.getString("Released");
            runtime = jo.getString("Runtime");
            plot = jo.getString("Plot");
            year = jo.getInt("Year");
            genre = Arrays.asList(jo.getString("Genre").split(","));
            actors = Arrays.asList(jo.getString("Actors").split(","));

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
            jo.put("Genre", genre.toString().replace("[", "").replace("]", ""));
            jo.put("Actors", actors.toString().replace("[", "").replace("]", ""));
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
