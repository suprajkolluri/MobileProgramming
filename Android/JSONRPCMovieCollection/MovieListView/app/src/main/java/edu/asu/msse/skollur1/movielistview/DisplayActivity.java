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


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    TextView titleView;
    TextView yearView;
    TextView ratedView;
    TextView releasedView;
    TextView runtimeView;
    TextView genreView;
    TextView actorsView;
    TextView plotView;
    MovieDescription des;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        String movieName = intent.getStringExtra("movieName");
        try{
            url = this.getString(R.string.defaulturl);;
            MethodInformation mi = new MethodInformation(this, url,"get",
                    new String[]{movieName});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception getting movie: "+
                    ex.getMessage());
        }
    }

    public void filldata(String json){
        titleView = (TextView) findViewById(R.id.titleid);
        yearView = (TextView) findViewById(R.id.yeadid);
        ratedView = (TextView) findViewById(R.id.ratedid);
        releasedView = (TextView) findViewById(R.id.releasedid);
        runtimeView = (TextView) findViewById(R.id.runtimeid);
        genreView = (TextView) findViewById(R.id.genreid);
        actorsView = (TextView) findViewById(R.id.actorsid);
        plotView = (TextView) findViewById(R.id.plotid);

        des = new MovieDescription(json);
        titleView.setText(des.title);
        yearView.setText(String.valueOf(des.year));
        ratedView.setText(des.rated);
        releasedView.setText(des.released);
        runtimeView.setText(des.runtime);
        genreView.setText(des.genre);
        actorsView.setText(des.actors);
        plotView.setText(des.plot);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.backmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.backMenu:
                this.finish();
            default:
                super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    public void onClickDelButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", des.title);
        this.startActivityForResult(intent, 1);
    }

}
