package edu.asu.msse.skollur1.movielistview;

import android.os.AsyncTask;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
 * @version March 15, 2016
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
            JsonRPCRequestViaHttp conn = new JsonRPCRequestViaHttp((new URL(aRequest[0].urlString)), aRequest[0].parent);
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
            if (res.method.equals("getGenreMovieMap")) {
                LinkedHashMap<String, List<String>> genreMovieMap = new LinkedHashMap<>();
                String json = (String)new JSONObject(res.resultAsJson).get("result");

                JSONObject jo = new JSONObject(json);
                Iterator<?> keys = jo.keys();

                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    List<String> li = new ArrayList<>();
                    JSONArray ja = jo.getJSONArray(key);
                    int len = ja.length();
                    for (int i=0;i<len;i++){
                        li.add(ja.get(i).toString());
                    }
                    genreMovieMap.put(key,li);
                }
                res.parent.genreMovieMap = genreMovieMap;
                res.parent.movieListAdapter = new MovieListAdapter(res.parent);
                res.parent.expandableView.setAdapter(res.parent.movieListAdapter);
                res.parent.movieListAdapter.notifyDataSetChanged();

            } else  if (res.method.equals("get")) {
                JSONObject jo = new JSONObject(res.resultAsJson).getJSONObject("result");
                res.displayActivity.filldata(jo.toString());
            } else if(res.method.equals("getGenreNames")){
                JSONArray ja = new JSONObject(res.resultAsJson).getJSONArray("result");
                List<String> li = new ArrayList<>();
                for(int i=0;i<ja.length();i++){
                    li.add(ja.getString(i));
                }
                res.addActivity.fillSpinner(li);
            }
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
        }
    }
}
