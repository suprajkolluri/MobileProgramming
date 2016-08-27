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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        Intent intent = new Intent(this,AlertActivity.class);
        startActivity(intent);
    }
}
