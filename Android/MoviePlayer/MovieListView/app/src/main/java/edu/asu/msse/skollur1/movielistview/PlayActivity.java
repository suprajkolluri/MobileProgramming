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
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener{

    private VideoView mVideoView;
    private MediaController mController;
    private MediaMetadataRetriever mMetadataRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        mVideoView = (VideoView) findViewById(R.id.avideoview);
        mVideoView.setVideoPath(this.getString(R.string.streamUrlVal) + filename);
        //mVideoView.setVideoPath(getString(R.string.urlprefix) + "Crazy.mp3");
        MediaController mediaController = new MediaController((Context)this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setOnPreparedListener(this);
    }

    public void onPrepared(MediaPlayer mp){
        android.util.Log.d(this.getClass().getSimpleName(), "onPrepared called. Video Duration: "
                + mVideoView.getDuration());
        mVideoView.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        android.util.Log.d(this.getClass().getSimpleName(), "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}