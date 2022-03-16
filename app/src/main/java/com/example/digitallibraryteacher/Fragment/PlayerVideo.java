package com.example.digitallibraryteacher.Fragment;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.digitallibraryteacher.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;


public class PlayerVideo extends Fragment {

    private boolean isShowingTrackSelectionDialog;
    private DefaultTrackSelector trackSelector;
    String[] speed = {"0.25x","0.5x","Normal","1.5x","2x"};
    //demo url
    String url1 = "https://5b44cf20b0388.streamlock.net:8443/vod/smil:bbb.smil/playlist.m3u8";

    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    View view;


    public PlayerVideo(String file, String title) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_player_video, container, false);

        trackSelector = new DefaultTrackSelector(getContext());
        simpleExoPlayer = new SimpleExoPlayer.Builder(getContext()).setTrackSelector(trackSelector).build();
        playerView =view. findViewById(R.id.exoPlayerView);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url1);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();


        ImageView farwordBtn = playerView.findViewById(R.id.fwd);
        ImageView rewBtn = playerView.findViewById(R.id.rew);

        ImageView play=playerView.findViewById(R.id.exo_play);
        ImageView pause=playerView.findViewById(R.id.exo_pause);






        farwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition() + 10000);

            }
        });
        rewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long num = simpleExoPlayer.getCurrentPosition() - 10000;
                if (num < 0) {

                    simpleExoPlayer.seekTo(0);


                } else {

                    simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition() - 10000);

                }


            }
        });

        ImageView fullscreenButton = playerView.findViewById(R.id.fullscreen);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {


                int orientation = getActivity().getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // code for portrait mode


                view.    findViewById(R.id.exo_play).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            simpleExoPlayer.play();

                        }
                    });
                    view.findViewById(R.id.exo_pause).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            simpleExoPlayer.pause();

                        }
                    });


                } else {
                    // code for landscape mode

                    // Toast.makeText(MainActivity.this, "Land", Toast.LENGTH_SHORT).show();
                 view.   findViewById(R.id.exo_play).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            simpleExoPlayer.play();

                        }
                    });
                 view.   findViewById(R.id.exo_pause).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            simpleExoPlayer.pause();

                        }
                    });

                }


            }
        });


       view. findViewById(R.id.exo_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simpleExoPlayer.play();

            }
        });
      view.  findViewById(R.id.exo_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simpleExoPlayer.pause();

            }
        });


        simpleExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == ExoPlayer.STATE_ENDED) {

                }

            }
        });


        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {

            }
        });






        return  view;
    }
}