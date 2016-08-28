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
 * @version April 24, 2016
 */
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    TextView posterView;
    String movieName = "";
    String fileName = "";
    Button playMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        movieName = intent.getStringExtra("movieName");
        playMovie = (Button)findViewById(R.id.playButton);
        filldata();
    }

    private void filldata(){
        titleView = (TextView) findViewById(R.id.titleid);
        yearView = (TextView) findViewById(R.id.yeadid);
        ratedView = (TextView) findViewById(R.id.ratedid);
        releasedView = (TextView) findViewById(R.id.releasedid);
        runtimeView = (TextView) findViewById(R.id.runtimeid);
        genreView = (TextView) findViewById(R.id.genreid);
        actorsView = (TextView) findViewById(R.id.actorsid);
        plotView = (TextView) findViewById(R.id.plotid);
        posterView = (TextView) findViewById(R.id.posterid);

        try {
            MovieDB db = new MovieDB(this);
            SQLiteDatabase crsDB = db.openDB();
            Cursor cur = crsDB.rawQuery("select title,year,rated,released,runtime,genre,actors,plot,poster,filename from movie where title=? ;",
                    new String[]{movieName});
            while (cur.moveToNext()){
                titleView.setText(cur.getString(0));
                yearView.setText(cur.getString(1));
                ratedView.setText(cur.getString(2));
                releasedView.setText(cur.getString(3));
                runtimeView.setText(cur.getString(4));
                genreView.setText(cur.getString(5));
                actorsView.setText(cur.getString(6));
                plotView.setText(cur.getString(7));
                posterView.setText(cur.getString(8));
                fileName = cur.getString(9);
                if(fileName == null){
                    playMovie.setVisibility(View.GONE);
                } else{
                    playMovie.setVisibility(View.VISIBLE);
                }
            }

            cur.close();
            crsDB.close();
            db.close();

        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception getting movie info in display page: "+
                    ex.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        try {
            MovieDB db = new MovieDB(this);
            SQLiteDatabase crsDB = db.openDB();
            crsDB.delete("movie","title=?",new String[]{movieName});

            crsDB.close();
            db.close();

        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception deleting movie info from display page: "+
                    ex.getMessage());
        }
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivityForResult(intent, 1);
    }

    public void onClickPlayButton(View view){
        Intent display = new Intent(this, PlayActivity.class);
        display.putExtra("filename", fileName);
        this.startActivityForResult(display, 1);
    }
}
