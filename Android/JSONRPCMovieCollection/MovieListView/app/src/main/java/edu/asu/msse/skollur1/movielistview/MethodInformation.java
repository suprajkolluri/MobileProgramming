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
 * @version March 15, 2016
 */
public class MethodInformation {
    public String method;
    public String[] params;
    public MainActivity parent;
    public String urlString;
    public String resultAsJson;
    public DisplayActivity displayActivity;
    public AddActivity addActivity;

    MethodInformation(MainActivity parent, String urlString, String method, String[] params){
        this.method = method;
        this.parent = parent;
        this.urlString = urlString;
        this.params = params;
        this.resultAsJson = "{}";
    }

    MethodInformation(DisplayActivity displayActivity, String urlString, String method, String[] params){
        this.method = method;
        this.displayActivity = displayActivity;
        this.urlString = urlString;
        this.params = params;
        this.resultAsJson = "{}";
    }

    MethodInformation(AddActivity addActivity, String urlString, String method, String[] params){
        this.method = method;
        this.addActivity = addActivity;
        this.urlString = urlString;
        this.params = params;
        this.resultAsJson = "{}";
    }
}
