package majorproject.view;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import majorproject.model.AppModel;

/**
 * Handles the UI elements and their event handlers for the media button
 */

public class MediaHandler {

    private AppModel model;
    private MediaPlayer mediaPlayer;

    public MediaHandler(AppModel model) {
        this.model = model;
        createMediaPlayer();
    }

    private void createMediaPlayer() {
        Media musicMedia = new Media(model.getMusicResource());
        mediaPlayer = new MediaPlayer(musicMedia);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }

    public Button createPlayPauseButton() {
        Button playPause = new Button("Play/Pause music");

        playPause.setOnAction(event -> {
            playPause();
        });

        return playPause;
    }

    public void playPause() {
        if (model.isAudioPlaying()) {
            mediaPlayer.pause();
            model.setAudioPlaying(false);
        } else {
            mediaPlayer.play();
            model.setAudioPlaying(true);
        }
    }
}
