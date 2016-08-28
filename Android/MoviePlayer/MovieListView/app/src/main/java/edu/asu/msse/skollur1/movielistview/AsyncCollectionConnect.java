package edu.asu.msse.skollur1.movielistview;

import android.os.AsyncTask;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

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
 * @version April 24, 2016
 */
public class AsyncCollectionConnect extends AsyncTask<MethodInformation, Integer, MethodInformation> {

    @Override
    protected void onPreExecute(){
        android.util.Log.d(this.getClass().getSimpleName(),"in onPreExecute on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Collect Thread"));
    }

    @Override
    protected MethodInformation doInBackground(MethodInformation... aRequest){
        // key is the waypoint category, value is an array of waypoint names in that category
        android.util.Log.d(this.getClass().getSimpleName(),"in doInBackground on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
        try {
            JSONArray ja = new JSONArray(aRequest[0].params);
            android.util.Log.d(this.getClass().getSimpleName(),"params: "+ja.toString());
            String requestData = "{ \"jsonrpc\":\"2.0\", \"method\":\""+aRequest[0].method+"\", \"params\":"+ja.toString()+
                    ",\"id\":3}";
            android.util.Log.d(this.getClass().getSimpleName(),"requestData: "+requestData+" url: "+aRequest[0].urlString);
            JsonRPCRequestViaHttp conn = new JsonRPCRequestViaHttp((new URL(aRequest[0].urlString)), aRequest[0].addActivity);
            String resultStr = conn.call(requestData);
            aRequest[0].resultAsJson = resultStr;
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                    ex.getMessage());
        }
        return aRequest[0];
    }
    @Override
    protected void onPostExecute(MethodInformation res){
        android.util.Log.d(this.getClass().getSimpleName(), "in onPostExecute on " +
                (Looper.myLooper() == Looper.getMainLooper() ? "Main thread" : "Async Thread"));
        android.util.Log.d(this.getClass().getSimpleName(), " resulting is: " + res.resultAsJson);
        try {
            if (res.method.equals("get")) {
                String json = new JSONObject(res.resultAsJson).getString("result");

                MovieDescription des = new MovieDescription(json);
                if(des.title.equals("Unknown")){
                    res.addActivity.callOMDB();
                } else {
                    res.addActivity.titleView.setText(des.title);
                    res.addActivity.yearView.setText(des.year);
                    res.addActivity.ratedView.setText(des.rated);
                    res.addActivity.releasedView.setText(des.released);
                    res.addActivity.runtimeView.setText(des.runtime);
                    int index = res.addActivity.list.indexOf(des.genre.split(",")[0].trim());
                    res.addActivity.genreView.setSelection(index);
                    res.addActivity.posterView.setText(des.poster);
                    res.addActivity.actorsView.setText(des.actors);
                    res.addActivity.plotView.setText(des.plot);
                    res.addActivity.fileName = des.filename;
                }
            }
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
        }
    }
}
