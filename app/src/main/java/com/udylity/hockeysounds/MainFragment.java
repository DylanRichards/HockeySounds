package com.udylity.hockeysounds;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements View.OnClickListener{

    public static MediaPlayer mediaPlayer;
    Button btnPlay, btnStop;

    int song = R.raw.powerplay;

    private static Notification.Builder mNotificationBuilder;
    private static NotificationManager mNotificationManager;
    private static Notification mNotification;
    String text = "This is the text of the notification";
    String title = "Title of the notification";
    static final int UNIQUE_ID = 8932;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mNotificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        loadSong(song);

        btnPlay = (Button)rootView.findViewById(R.id.btnPlay);
        btnStop = (Button)rootView.findViewById(R.id.btnStop);

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        return rootView;
    }

    public void loadSong(int resid){
        mediaPlayer = MediaPlayer.create(getActivity(), song);
        createNotification();
    }

    public void playSong() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btnPlay.setText("Play");
        } else {
            mediaPlayer.start();
            btnPlay.setText("Pause");
        }
    }

    public void resetSong(){
        mediaPlayer.reset();
        loadSong(song);
    }

    private void createNotification() {
        String action = "ACTION";
        Intent playIntent = new Intent(getActivity(), MainActivity.class);
        playIntent.putExtra(action, "play");
        PendingIntent playPendingintent = PendingIntent.getActivity(getActivity(), 0, playIntent, 0);
        Intent stopIntent = new Intent(getActivity(), MainActivity.class);
        stopIntent.putExtra(action, "stop");
        PendingIntent stopPendingintent = PendingIntent.getActivity(getActivity(), 0, stopIntent, 0);
        mNotificationBuilder = new Notification.Builder(getActivity());
        mNotificationBuilder
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_MAX)
                .addAction(R.drawable.ic_launcher, "Play", playPendingintent)
                .addAction(R.drawable.ic_launcher, "Stop", stopPendingintent);
        mNotification = mNotificationBuilder.build();
        mNotificationManager.notify(UNIQUE_ID, mNotification);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                playSong();
                break;
            case R.id.btnStop:
                resetSong();
                btnPlay.setText("Play");
                break;
        }
    }

//    @Override
//      public void onDestroy() {
//        super.onDestroy();
//        mediaPlayer.release();
//        mediaPlayer = null;
//    }
}
