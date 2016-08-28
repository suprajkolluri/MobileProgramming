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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class AddActivity extends AppCompatActivity {
    EditText titleView;
    EditText yearView;
    EditText ratedView;
    EditText releasedView;
    EditText runtimeView;
    Spinner genreView;
    EditText actorsView;
    EditText plotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        List<String> genreList = intent.getStringArrayListExtra("genreList");
        fillSpinner(genreList);
    }

    private void fillSpinner(List<String> list){
        genreView = (Spinner)findViewById(R.id.genreDD);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreView.setAdapter(dataAdapter);
    }

    public void onClickButton(View view){
        titleView = (EditText) findViewById(R.id.titleET);
        yearView = (EditText) findViewById(R.id.yearET);
        ratedView = (EditText) findViewById(R.id.ratedET);
        releasedView = (EditText) findViewById(R.id.dateET);
        runtimeView = (EditText) findViewById(R.id.runtimeET);
        actorsView = (EditText) findViewById(R.id.actorsET);
        plotView = (EditText) findViewById(R.id.plotET);

        MovieDescription des = new MovieDescription();
        des.title = titleView.getText().toString();
        des.year = yearView.getText().toString();
        des.rated = ratedView.getText().toString();
        des.released = releasedView.getText().toString();
        des.runtime = runtimeView.getText().toString();
        des.actors = actorsView.getText().toString();
        des.plot = plotView.getText().toString();
        des.genre = genreView.getSelectedItem().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("des", des.toJsonString());
        this.startActivityForResult(intent, 1);
    }
}
