package com.example.thecompleteandroidnsection3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow 1 = red

    int activePlayer = 0;
    boolean isActive = true;
    int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int [][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void fadeBart(View view) {

        ImageView bartImageView = findViewById(R.id.bartImageView);
        bartImageView.animate().scaleX(0.5f).scaleY(0.5f).rotation(360f).setDuration(2000);

        bartImageView.animate().scaleX(1f).scaleY(1f).rotation(360f).setDuration(2000);
    }

    public void getTranslation(View view) {
        ImageView bartImageView = findViewById(R.id.bartImageView);
        //bartImageView.animate().xBy(100).setDuration(2000);

        Log.i("INFO getY", String.valueOf(bartImageView.getX()));
        Log.i("INFO getTranslationY", String.valueOf(bartImageView.getTranslationX()));
    }


    public void fadeHomer(View view) {
        ImageView bartImageView = findViewById(R.id.bartImageView);
        ImageView homerImageView = findViewById(R.id.homeImageView);

        bartImageView.animate().alpha(1f).setDuration(2000);
        homerImageView.animate().alpha(0f).setDuration(2000);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int counterIndex = Integer.valueOf(counter.getTag().toString());


        // Code to bring the counter on view using animation
        if (gameState[counterIndex] == 2 && isActive) {
            counter.setTranslationY(-1000f);
            gameState[counterIndex] = activePlayer;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360f).setDuration(1000);
        }


        // Code to determine if someone has won.
        for (int[] winningPosition: winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[0]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2
            ) {
                LinearLayout playAgainLayoutView = findViewById(R.id.playAgainLayout);
                TextView playerWonInfoLayout = findViewById(R.id.playerWonInfo);

                isActive = false;
                if (gameState[winningPosition[0]] == 0) {
                    playerWonInfoLayout.setText("Yellow Won!");
                } else {
                    playerWonInfoLayout.setText("Red Won!");
                }


                if (playAgainLayoutView.getVisibility() == View.INVISIBLE) {

                    playAgainLayoutView.setVisibility(View.VISIBLE);
                }

                break;
            }
        }

        // Code to check whether it's a draw.
        boolean isDraw = true;
        for (int counterState: gameState) {
            if (counterState == 2) {
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            LinearLayout playAgainLayoutView = findViewById(R.id.playAgainLayout);
            TextView playerWonInfoLayout = findViewById(R.id.playerWonInfo);
            playerWonInfoLayout.setText("It's a draw!");
            isActive = false;

            if (playAgainLayoutView.getVisibility() == View.INVISIBLE) {

                playAgainLayoutView.setVisibility(View.VISIBLE);
            }
        }

    }

    public void resetGame(View view) {
        activePlayer = 0;
        gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        isActive = true;

        LinearLayout playAgainLayoutView = findViewById(R.id.playAgainLayout);
        if (playAgainLayoutView.getVisibility() == View.VISIBLE) {

            playAgainLayoutView.setVisibility(View.INVISIBLE);
        }

        androidx.gridlayout.widget.GridLayout boardGameLayoutView = findViewById(R.id.boardLayout);

        for (int i = 0; i < boardGameLayoutView.getChildCount(); i+= 1) {
            ((ImageView)boardGameLayoutView.getChildAt(i)).setImageResource(0);
        }
    }


    MediaPlayer mpPlayer;
    AudioManager audioManager;
    public void startAudio(View view) {
        mpPlayer.start();
    }

    public void pauseAudio(View view) {
        if (mpPlayer.isPlaying()) {
            mpPlayer.pause();
        }
    }

    public void buttonTapped(View view) {
        int id = view.getId();
        String stringId = "";

        stringId = view.getResources().getResourceEntryName(id);

        int resourceId = getResources().getIdentifier(stringId, "raw", "com.example.thecompleteandroidnsection3");

        MediaPlayer mplayer = MediaPlayer.create(this, resourceId);
        mplayer.start();

        // Log.i("INFO", "Button Tapped Resource" + resourceId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fading_animation);
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.connect_3_1);

        // Code to use video in android studio.
//        setContentView(R.layout.videoview);
//        VideoView videoView = findViewById(R.id.videoView);
//
//        MediaController mediaController = new MediaController(this);
//
//        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.demovideo);
//
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//        videoView.start();

        // Code to use MediaPlayer, SeekBar, Controlling Volume in android.
//        setContentView(R.layout.audioview);
//        mpPlayer = MediaPlayer.create(this, R.raw.laugh);
//
//
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//
//
//        SeekBar volumeControl = findViewById(R.id.volumeSeekBar);
//        volumeControl.setMax(maxVolume);
//        volumeControl.setProgress(curVolume);
//
//        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // Log.i("INFO", "Seekbar Progress: " + progress);
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//
//        SeekBar audioScrubber = findViewById(R.id.audioScrubber);
//        audioScrubber.setMax(mpPlayer.getDuration());
//
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                audioScrubber.setProgress(mpPlayer.getCurrentPosition());
//            }
//        }, 0, 100);
//
//
//        audioScrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // Log.i("INFO", "Audio Seekbar Progress " + progress);
//                if (mpPlayer != null && fromUser) {
//                    mpPlayer.seekTo(progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                if (mpPlayer.isPlaying()) {
//                    mpPlayer.pause();
//                }
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (!mpPlayer.isPlaying()) {
//                    mpPlayer.start();
//                }
//            }
//        });

        setContentView(R.layout.basic_phrases);

    }
}