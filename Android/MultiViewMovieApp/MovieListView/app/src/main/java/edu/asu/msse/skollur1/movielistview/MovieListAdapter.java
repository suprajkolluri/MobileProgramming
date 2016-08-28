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
 * @version February 10, 2016
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieListAdapter extends BaseExpandableListAdapter implements View.OnTouchListener, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener{
    MainActivity mainActivity;
    MovieLibrary movieLibrary;
    TextView textView;

    public MovieListAdapter(MainActivity mainActivity, MovieLibrary movieLibrary){
        this.movieLibrary = movieLibrary;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getGroupCount() {
        return movieLibrary.genreMovieList.size();

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        for(Map.Entry<String, ArrayList<MovieDescription>> entry : movieLibrary.genreMovieList.entrySet()){
            if(count == groupPosition){
                return entry.getValue().size();
            }
            count++;
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        int count = 0;
        for(Map.Entry<String, ArrayList<MovieDescription>> entry : movieLibrary.genreMovieList.entrySet()){
            if(count == groupPosition){
                return entry.getKey();
            }
            count++;
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        int count = 0;
        for(Map.Entry<String, ArrayList<MovieDescription>> entry : movieLibrary.genreMovieList.entrySet()){
            if(count == groupPosition){
                MovieDescription movieDescription = entry.getValue().get(childPosition);
                return movieDescription.title;
            }
            count++;
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mainActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.super_layout, null);
        }
        TextView genreTextView = (TextView) convertView.findViewById(R.id.name_genre);
        genreTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        genreTextView.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String name = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sub_layout, null);
        }
        TextView movieTextView = (TextView)convertView.findViewById(R.id.name_movie);
        convertView.setOnTouchListener(this);
        movieTextView.setText(name);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        if (textView != null){
            textView = null;
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (textView != null){
            textView = null;
        }
        for (int i=0; i< this.getGroupCount(); i++) {
            if(i != groupPosition){
                mainActivity.expandableView.collapseGroup(i);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            if (v instanceof android.widget.LinearLayout) {
                android.widget.LinearLayout layoutView = (android.widget.LinearLayout) v;
                for (int i = 0; i <= layoutView.getChildCount(); i++) {
                    if (layoutView.getChildAt(i) instanceof TextView) {
                        TextView tmp = ((TextView) layoutView.getChildAt(i));
                        tmp.setBackgroundColor(Color.GREEN);
                        textView = tmp;
                        MovieDescription selectedMovie = null;
                        String movieName = tmp.getText().toString();
                        boolean flag = false;
                        for(Map.Entry<String, ArrayList<MovieDescription>> entry : movieLibrary.genreMovieList.entrySet()){
                            if(flag){
                                break;
                            }
                            List<MovieDescription> movieList = entry.getValue();
                            for(MovieDescription curMovie: movieList){
                                selectedMovie = curMovie;
                                if(curMovie.title.equals(movieName)){
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        String movieJson = selectedMovie.toJsonString();
                        Intent display = new Intent(mainActivity, DisplayActivity.class);
                        display.putExtra("movieJson", movieJson);
                        mainActivity.startActivityForResult(display,1);
                    }
                }
            }
        }
        return true;
    }
}

