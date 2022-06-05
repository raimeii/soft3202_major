package majorproject.model.resourcehandler;


/**
 * Simple resource handling to move the play/pause logic outside the view
 */
public interface ResourceHandler {

    /**
     * returns the path to the audio file used for the extra feature implementation
     *
     * @return the string to be used as the path to retrieve the audio file
     */
    String getMusicResource();

    /**
     * getter method for if the model is currently playing music
     *
     * @return true if model is prompting to play music, false otherwise
     */
    boolean isAudioPlaying();

    /**
     * sets the current state of the model's music to play (true) or pause (false
     *
     * @param audioPlaying the new state
     */
    void setAudioPlaying(boolean audioPlaying);

}
