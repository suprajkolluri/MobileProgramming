package edu.asu.msse.skollur1.server;
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
public interface MovieLibrary {

	public boolean saveToJsonFile();

	public boolean resetFromJsonFile();

	public boolean add(MovieDescription movie);

	public boolean remove(String movieName);

	public MovieDescription get(String movieName);

	public String[] getMovieNames();

	public String[] getGenreNames();

	public String getGenreMovieMap();

}
