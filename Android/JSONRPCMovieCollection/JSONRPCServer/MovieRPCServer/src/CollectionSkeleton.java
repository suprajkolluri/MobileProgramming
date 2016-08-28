package edu.asu.msse.skollur1.server;

import org.json.JSONArray;
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
public class CollectionSkeleton {

	MovieLibrary mLib;

	public CollectionSkeleton(MovieLibrary mLib) {
		this.mLib = mLib;
	}

	public String callMethod(String request) {
		JSONObject result = new JSONObject();
		try {
			JSONObject theCall = new JSONObject(request);
			String method = theCall.getString("method");
			int id = theCall.getInt("id");
			JSONArray params = null;
			if (!theCall.isNull("params")) {
				params = theCall.getJSONArray("params");
			}
			result.put("id", id);
			result.put("jsonrpc", "2.0");
			if (method.equals("resetFromJsonFile")) {
				mLib.resetFromJsonFile();
				result.put("result", true);
			} else if (method.equals("saveToJsonFile")) {
				boolean saved = mLib.saveToJsonFile();
				result.put("result", saved);
			} else if (method.equals("remove")) {
				String movieName = params.getString(0);
				boolean removed = mLib.remove(movieName);
				result.put("result", removed);
			} else if (method.equals("add")) {
				String movieJson = params.getString(0);
				MovieDescription movie = new MovieDescription(movieJson);
				boolean added = mLib.add(movie);
				result.put("result", added);
			} else if (method.equals("get")) {
				String movieName = params.getString(0);
				MovieDescription movie = mLib.get(movieName);
				result.put("result", movie.toJson());
			} else if (method.equals("getMovieNames")) {
				String[] names = mLib.getMovieNames();
				JSONArray resArr = new JSONArray();
				for (int i = 0; i < names.length; i++) {
					resArr.put(names[i]);
				}
				result.put("result", resArr);
			} else if (method.equals("getGenreNames")) {
				String[] names = mLib.getGenreNames();
				JSONArray resArr = new JSONArray();
				for (int i = 0; i < names.length; i++) {
					resArr.put(names[i]);
				}
				result.put("result", resArr);
			} else if (method.equals("getGenreMovieMap")) {
				String resultArr = mLib.getGenreMovieMap();
				result.put("result", resultArr);
			}
		} catch (Exception ex) {
			System.out.println("exception in callMethod: " + ex.getMessage());
		}
		System.out.println("returning: " + result.toString());
		return "HTTP/1.0 200 Data follows\nServer:localhost:8080\nContent-Type:text/plain\nContent-Length:"
				+ (result.toString()).length() + "\n\n" + result.toString();
	}
}
