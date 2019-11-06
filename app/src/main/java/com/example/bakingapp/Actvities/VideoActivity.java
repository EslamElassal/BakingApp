package com.example.bakingapp.Actvities;

import androidx.annotation.NonNull;
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
    int ID,StepIndex;
    static int CurrentIndex=-1;
    ArrayList<Steps>steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.playerView);


        if(findViewById(R.id.videolayout).getTag().equals("land"))
        {
if(savedInstanceState!=null) {
    setTitle(savedInstanceState.getString("videotitle"));
    mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
            (getResources(), R.drawable.icon));
    VideoUrl = savedInstanceState.getString("videourl");
    if (VideoUrl == null || VideoUrl.equals("")) {
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.novideo));
    }
    initializePlayer(Uri.parse(VideoUrl));
}
else
{
    //get from prev page as Land
    Intent intent = getIntent();
    ID = intent.getIntExtra("id", 0);
    steps = intent.getParcelableArrayListExtra("steps");
    Title = intent.getStringExtra("title");

    if(CurrentIndex!=-1)
    {VideoUrl=steps.get(CurrentIndex).getVideoURL();
        StepIndex=CurrentIndex;
        Desc=steps.get(CurrentIndex).getDescription();
        ShortDesc=steps.get(CurrentIndex).getShortDescription();
    }
    else
    {
        VideoUrl = intent.getStringExtra("video");
        StepIndex = intent.getIntExtra("stepsindex", 0);
        Desc = intent.getStringExtra("desc");
        ShortDesc = intent.getStringExtra("shortdesc");

    }

    setTitle(Title);
    mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
            (getResources(), R.drawable.icon));

    if (VideoUrl == null || VideoUrl.equals("")) {
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.novideo));
    }
    initializePlayer(Uri.parse(VideoUrl));

}
            }else {
            Intent intent = getIntent();
            ID = intent.getIntExtra("id", 0);
            steps = intent.getParcelableArrayListExtra("steps");
            Title = intent.getStringExtra("title");

            if(CurrentIndex!=-1)
            {VideoUrl=steps.get(CurrentIndex).getVideoURL();
                StepIndex=CurrentIndex;
                Desc=steps.get(CurrentIndex).getDescription();
                ShortDesc=steps.get(CurrentIndex).getShortDescription();
            }
            else
            {
                VideoUrl = intent.getStringExtra("video");
                StepIndex = intent.getIntExtra("stepsindex", 0);
                Desc = intent.getStringExtra("desc");
                ShortDesc = intent.getStringExtra("shortdesc");

            }


            setTitle(Title);
            DescView = (TextView) findViewById(R.id.Video_Activity_Desc);
            ShortDescView = (TextView) findViewById(R.id.Video_Activity_Short_Desc);

            BackBut = (ImageButton) findViewById(R.id.Back);
            NextBut = (ImageButton) findViewById(R.id.Next);
            ShortDescView.setText(ShortDesc);
            DescView.setText(Desc);

            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.icon));

            if (VideoUrl == null || VideoUrl.equals("")) {
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
VideoUrl=newVideoUrl;
        initializePlayer(Uri.parse(VideoUrl));

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if(!findViewById(R.id.videolayout).getTag().equals("land")) {
            outState.putString("videourl", steps.get(StepIndex).getVideoURL());
            outState.putString("videotitle", Title);
            outState.putInt("videostepsindex", StepIndex);
            outState.putString("desc", steps.get(StepIndex).getDescription());
            outState.putString("shortdesc", steps.get(StepIndex).getShortDescription());
        }

        super.onSaveInstanceState(outState);
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
