package edu.asu.msse.skollur1.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.json.JSONTokener;
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
public class MovieLibraryImpl implements MovieLibrary {
	public HashMap<String, MovieDescription> movieMap;
	private static final boolean debugOn = false;
	private static final String studentJsonFileName = "movies.json";
	public HashMap<String, List<String>> genreMovieMap;

	public MovieLibraryImpl() {
		debug("creating a new movie collection");
		movieMap = new HashMap<String, MovieDescription>();
		genreMovieMap = new HashMap<String, List<String>>();
		this.resetFromJsonFile();
	}

	private void debug(String message) {
		if (debugOn)
			System.out.println("debug: " + message);
	}

	@Override
	public boolean saveToJsonFile() {
		boolean ret = true;
		try {
			JSONObject obj = new JSONObject();
			for (String e : movieMap.keySet()) {
				MovieDescription curMovie = movieMap.get(e);
				obj.put(curMovie.title, curMovie.toJson());
			}
			PrintWriter out = new PrintWriter(studentJsonFileName);
			out.println(obj.toString(2));
			out.close();
		} catch (Exception ex) {
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean resetFromJsonFile() {
		boolean ret = true;
		try {
			movieMap.clear();
			String fileName = studentJsonFileName;
			File f = new File(fileName);
			FileInputStream is = new FileInputStream(f);
			JSONObject jo = new JSONObject(new JSONTokener(is));
			Iterator<String> it = jo.keys();
			while (it.hasNext()) {
				String mType = it.next();
				JSONObject movieJson = jo.optJSONObject(mType);
				MovieDescription movie = new MovieDescription(movieJson);
				movieMap.put(movie.title, movie);
				debug("added " + movie.title + " : " + movie.toJsonString() + "\nmovie.size() is: " + movieMap.size());
			}
		} catch (Exception ex) {
			System.out.println("Exception reading json file: " + ex.getMessage());
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean add(MovieDescription movie) {
		boolean ret = true;
		debug("adding movie named: " + ((movie == null) ? "unknown" : movie.title));
		try {
			movieMap.put(movie.title, movie);
		} catch (Exception ex) {
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean remove(String movieName) {
		debug("removing movie named: " + movieName);
		return ((movieMap.remove(movieName) == null) ? false : true);

	}

	@Override
	public MovieDescription get(String movieName) {
		MovieDescription ret = new MovieDescription();
		ret.title = "unkown";
		MovieDescription curMovie = movieMap.get(movieName);
		if (curMovie != null) {
			ret = curMovie;
		}
		return ret;
	}

	@Override
	public String[] getMovieNames() {
		String[] ret = {};
		debug("getting " + movieMap.size() + " movie names.");
		if (movieMap.size() > 0) {
			ret = (String[]) (movieMap.keySet()).toArray(new String[0]);
		}
		return ret;
	}

	@Override
	public String[] getGenreNames() {
		String[] genres = { "Adventure", "Action", "Drama", "Comedy", "Fantasy", "Biography", "Animation" };
		return genres;
	}

	@Override
	public String getGenreMovieMap() {
		genreMovieMap.clear();
		JSONObject jo = new JSONObject();
		try {
			for(Entry<String,MovieDescription> entry: movieMap.entrySet()){
				List<String> li;
				if (genreMovieMap.containsKey(entry.getValue().genre)) {
					li = genreMovieMap.get(entry.getValue().genre);
				} else {
					li = new ArrayList<String>();
				}
				li.add(entry.getValue().title);
				genreMovieMap.put(entry.getValue().genre, li);
			}
			for (Entry<String, List<String>> entry : genreMovieMap.entrySet()) {
				jo.put(entry.getKey(), entry.getValue());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jo.toString();
	}
}
