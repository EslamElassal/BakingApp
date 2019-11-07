
package com.example.bakingapp.Fragments;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.fragment.app.Fragment;

        import com.example.bakingapp.Models.Ingredients;
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
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

        import static androidx.constraintlayout.widget.Constraints.TAG;

public class VideoFragment extends Fragment {
    List<Steps>steps;
    Context context;
    String Image;
    int INDEX;
    private SimpleExoPlayer mExoPlayer;
    SimpleExoPlayerView mPlayerView;
    private static String STEPS_LIST_ID="STEPS_LIST_ID";
    private static String MEAL_STEP_INDEX="MEAL_STEP_INDEX";

    public VideoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(STEPS_LIST_ID);
            INDEX = savedInstanceState.getInt(MEAL_STEP_INDEX);
            Log.e("Eslam",INDEX+" Bundle");

        }


        View rootView = inflater.inflate(R.layout.meal_video_section_fragment, container, false);
        TextView Desc = (TextView)rootView.findViewById(R.id.Video_Activity_Desc_Fragment);
        TextView ShortDesc = (TextView)rootView.findViewById(R.id.Video_Activity_Short_Desc_Fragment);
        mPlayerView=(SimpleExoPlayerView)rootView.findViewById(R.id.playerView_Fragment) ;
        Log.e("Eslam",INDEX+"");

        initializePlayer(Uri.parse(steps.get(INDEX).getVideoURL()));
        Desc.setText(steps.get(INDEX).getDescription());
        ShortDesc.setText(steps.get(INDEX).getShortDescription());
        return rootView;
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getBaseContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getActivity().getBaseContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity().getBaseContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        else {
            String userAgent = Util.getUserAgent(getActivity().getBaseContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity().getBaseContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    public void setSteps(List<Steps> steps) { this.steps = steps; }
    public void setContext(Context context){this.context=context;}
    public void setMealImage(String Image){this.Image= Image; }
    public void setIndex(int Index){this.INDEX= Index; }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelableArrayList( STEPS_LIST_ID, new ArrayList<Steps>(steps));
        currentState.putInt(MEAL_STEP_INDEX,INDEX);
    }


}