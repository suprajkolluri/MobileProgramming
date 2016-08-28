package edu.asu.msse.skollur1.server;

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

	public MovieDescription() {

	}

	public MovieDescription(JSONObject jsonObj) {
		try {
			System.out.println("constructor from json received: " + jsonObj.toString());
			title = jsonObj.getString("Title");
			rated = jsonObj.getString("Rated");
			released = jsonObj.getString("Released");
			runtime = jsonObj.getString("Runtime");
			plot = jsonObj.getString("Plot");
			genre = jsonObj.getString("Genre");
			actors = jsonObj.getString("Actors");
			year = jsonObj.getString("Year");

		} catch (Exception ex) {
			System.out.println(this.getClass().getSimpleName() + ": error converting from json string");
		}
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
			ex.printStackTrace();
		}
	}

	public JSONObject toJson() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("Title", title);
			jo.put("Rated", rated);
			jo.put("Released", released);
			jo.put("Runtime", runtime);
			jo.put("Plot", plot);
			jo.put("Year", year);
			jo.put("Genre", genre);
			jo.put("Actors", actors);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jo;
	}

	public String toJsonString() {
		String ret = "";
		try {

			ret = this.toJson().toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public String toString() {
		return "MovieDescription [title=" + title + ", rated=" + rated + ", released=" + released + ", runtime="
				+ runtime + ", plot=" + plot + ", year=" + year + ", genre=" + genre + ", actors=" + actors + "]";
	}

}
