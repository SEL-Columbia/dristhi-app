package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;

public class VideosController {
    public static final String VIDEO_PLAYER_INTENT = "org.ei.dristhi_iec.VIDEO_PLAYER";
    public static final String VIDEO_NAME_PARAMETER = "VideoName";
    private Context context;

    public VideosController(Context context) {
        this.context = context;
    }

    public void play(String videoName) {
        Intent videoPlayerIntent = new Intent(VIDEO_PLAYER_INTENT);
        videoPlayerIntent.putExtra(VIDEO_NAME_PARAMETER, videoName);
        context.startActivity(videoPlayerIntent);
    }
}
