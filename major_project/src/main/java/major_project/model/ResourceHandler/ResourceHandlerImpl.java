package major_project.model.ResourceHandler;

import java.util.Objects;

public class ResourceHandlerImpl implements ResourceHandler {

    boolean audioPlaying = true;

    @Override
    public String getMusicResource() {
        try {
            return Objects.requireNonNull(getClass().getClassLoader().getResource("amelia_watson_bgm.mp3")).toString();
        } catch (NullPointerException e) {
            return null;
        }

    }
    @Override
    public boolean isAudioPlaying() {
        return audioPlaying;
    }
    @Override
    public void setAudioPlaying(boolean audioPlaying) {
        this.audioPlaying = audioPlaying;
    }
}
