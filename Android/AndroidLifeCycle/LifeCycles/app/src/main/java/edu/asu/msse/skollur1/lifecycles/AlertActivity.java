package edu.asu.msse.skollur1.lifecycles;
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
 * @version January 28, 2016
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onRestart() {
        super.onRestart();
        android.util.Log.w(this.getClass().getSimpleName(),"onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        android.util.Log.w(this.getClass().getSimpleName(), "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        android.util.Log.w(this.getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        android.util.Log.w(this.getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        android.util.Log.w(this.getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        android.util.Log.w(this.getClass().getSimpleName(), "onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
    }

    public void onClickAlert(View v){
        this.finish();
    }
}
