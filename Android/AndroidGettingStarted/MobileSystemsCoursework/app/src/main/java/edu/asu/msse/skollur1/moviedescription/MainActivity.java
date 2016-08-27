package edu.asu.msse.skollur1.moviedescription;
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
 * @version January 18, 2016
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView titleView;
    TextView yearView;
    TextView ratedView;
    TextView releasedView;
    TextView runtimeView;
    TextView genreView;
    TextView actorsView;
    TextView plotView;

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

    public void onClickButton(View view){
        titleView = (TextView) findViewById(R.id.titleid);
        yearView = (TextView) findViewById(R.id.yeadid);
        ratedView = (TextView) findViewById(R.id.ratedid);
        releasedView = (TextView) findViewById(R.id.releasedid);
        runtimeView = (TextView) findViewById(R.id.runtimeid);
        genreView = (TextView) findViewById(R.id.genreid);
        actorsView = (TextView) findViewById(R.id.actorsid);
        plotView = (TextView) findViewById(R.id.plotid);

        MovieDescription des = new MovieDescription("{\"Title\":\"Frozen\",\"Year\":\"2013\",\"Rated\":\"PG\",\"Released\":\"27 Nov 2013\",\"Runtime\":\"102 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Chris Buck, Jennifer Lee\",\"Writer\":\"Jennifer Lee (screenplay), Hans Christian Andersen (story), Chris Buck (story), Jennifer Lee (story), Shane Morris (story)\",\"Actors\":\"Kristen Bell, Idina Menzel, Jonathan Groff, Josh Gad\",\"Plot\":\"When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.\",\"Language\":\"English, Icelandic\",\"Country\":\"USA\",\"Awards\":\"Won 2 Oscars. Another 69 wins & 56 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQ1MjQwMTE5OF5BMl5BanBnXkFtZTgwNjk3MTcyMDE@._V1_SX300.jpg\",\"Metascore\":\"74\",\"imdbRating\":\"7.6\",\"imdbVotes\":\"390,405\",\"imdbID\":\"tt2294629\",\"Type\":\"movie\",\"Response\":\"True\"}");
        android.util.Log.w(this.getClass().getSimpleName(), des.toJsonString());
        titleView.setText(des.title);
        yearView.setText(String.valueOf(des.year));
        ratedView.setText(des.rated);
        releasedView.setText(des.released);
        runtimeView.setText(des.runtime);
        genreView.setText(des.genre.toString().replace("[", "").replace("]", ""));
        actorsView.setText(des.actors.toString().replace("[", "").replace("]", ""));
        plotView.setText(des.plot);

    }
}
