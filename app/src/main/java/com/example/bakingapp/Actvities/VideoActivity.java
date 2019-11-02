package com.example.bakingapp.Actvities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.quicksettings.Tile;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bakingapp.Models.Steps;
import com.example.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private String NoVideoImage="https://t4.ftcdn.net/jpg/01/08/11/51/240_F_108115131_AOEj4RDIuo3g3Hy3BvS2ciqOGm57rEas.jpg";
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView DescView,ShortDescView;
    private ImageButton NextBut,BackBut;
    String Desc,ShortDesc,VideoUrl,Title;
    int ID,StepIndex,CurrentIndex;

    ArrayList<Steps>steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        ID=intent.getIntExtra("id",0);
        Desc=intent.getStringExtra("desc");
        ShortDesc=intent.getStringExtra("shortdesc");
        VideoUrl=intent.getStringExtra("video");
        Title=intent.getStringExtra("title");
        StepIndex=intent.getIntExtra("stepsindex",0);
        steps=intent.getParcelableArrayListExtra("steps");
        setTitle(Title);
        DescView=(TextView)findViewById(R.id.Video_Activity_Desc);
        ShortDescView=(TextView)findViewById(R.id.Video_Activity_Short_Desc);
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.playerView);
        BackBut=(ImageButton)findViewById(R.id.Back);
        NextBut=(ImageButton)findViewById(R.id.Next);
        ShortDescView.setText(ShortDesc);
        DescView.setText(Desc);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.icon));

if(VideoUrl==null||VideoUrl.equals(""))
{
    mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
            (getResources(), R.drawable.novideo));
}
    initializePlayer(Uri.parse(VideoUrl));

NextBut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Next();
    }
});
        BackBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
              String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
             mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        else {
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;

    }
    void SetupPageViews(int index)
    {
        ShortDescView.setText(steps.get(index).getShortDescription());
        DescView.setText(steps.get(index).getDescription());
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.icon));
String newVideoUrl=steps.get(index).getVideoURL();
        if(newVideoUrl==null||newVideoUrl.equals(""))
        {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.novideo));
        }

        initializePlayer(Uri.parse(newVideoUrl));

    }
    void Back( )
    {
        if(StepIndex>0)
        {
            CurrentIndex=StepIndex-1;
            StepIndex=CurrentIndex;
        }
        else if(StepIndex==0)
        {CurrentIndex=steps.size()-1;
        StepIndex=CurrentIndex;}
        SetupPageViews(CurrentIndex);
    }
    void Next ( )
    {
        if(StepIndex<steps.size()-1)
        {
            CurrentIndex=StepIndex+1;
            StepIndex=CurrentIndex;
        }
        else if(StepIndex<steps.size())
        {CurrentIndex=0;
            StepIndex=CurrentIndex;
        }
        SetupPageViews(CurrentIndex);
    }
}
