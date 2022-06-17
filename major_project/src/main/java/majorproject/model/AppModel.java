package majorproject.model;

import majorproject.model.guardianhandler.GuardianHandler;

import java.util.ArrayList;
import java.util.List;

public interface AppModel {

    /**
     * Setter for model's current tag
     * @param tag the new tag to be set
     */
    void setCurrentTag(String tag);

    /**
     * Getter for model's current tag
     * @return the model's current tag
     */
    String getCurrentTag();

    /**
     * Checks the if the input tag has already been cached in the database
     * @param input the tag to check for
     * @return true if the tag has been cached before, false otherwise
     */
    boolean checkTagExistsInDatabase(String input);

    /**
     * Prompts the model's GuardianHandler to make an API call to the TheGuardian API to return a list of tag
     * strings that are a partial match to the input.
     *
     * @param input partial tag
     * @return list of partial matches to tag
     */
    ArrayList<String> searchMatchingTags(String input);

    /**
     * Prompts the model's GuardianHandler to make an API call to the TheGuardian API to return a list of the string
     * representations of ResultsPOJOs retrieved from the API call.
     *
     * @param tag tag to search results by
     * @return list of string representations of ResultsPOJOs
     */
    public ArrayList<String> getResultsWithTagAPI(String tag);

    /**
     * Prompts the model's Database to query the SQLite database cache to return the list of the string representations
     * of ResultsPOJOs retrieved from a previous API call with the matching tag.
     *
     * @param tag tag to search results by
     * @return list of string representations of ResultsPOJOs
     */
    ArrayList<String> getResultsWithTagDB(String tag);

    /**
     * Prompts the model's GuardianHandler to return the web URL of the ResultsPOJO with the matching string representation
     * as title
     *
     * @param title string representation to search by
     * @return the web url for the relevant result
     */
    String getContentURL(String title);

    /**
     * Checks if the GuardianHandler has a tag response GuardianPOJO currently
     *
     * @return true if a response is stored, false otherwise
     */
    boolean hasTagResponseStored();


    /**
     * Builds a singular string from the resultsMatches list attribute to output to the output api
     *
     * @return the string to be sent to the output api
     */
    String buildOutputReport();

    /**
     * sends a the result of a call to buildOutputReport to the PastebinHandler to return a URL with the pastebin dump
     *
     * @return the url to the generated pastebin dump
     */
    String generateOutputReport();

    /**
     * Prompts the model's Database to remove all previous cached entries
     */
    void clearDatabaseCache();

    /**
     * Getter for the string list of string representations of the current tag partial matches
     * to be displayed in the view.
     * @return list of current partial tag matches
     */
    List<String> getTagMatches();

    /**
     * Sets the model's current tag matches to the argument
     *
     * @param tagMatches the new list of string representations of the current tag partial matches to be displayed in
     * the view
     */
    void setTagMatches(List<String> tagMatches);

    /**
     * Getter for the string list of string representations of the current results with the current tag to be displayed
     * in the view
     *
     * @return list of results with current tag match
     */
    List<String> getResultMatches();

    /**
     * sets the model's current result matches to the argument
     *
     * @param resultMatches the new list of string representations of the current tag's results to be displayed in the
     * view
     */
    void setResultMatches(List<String> resultMatches);

    /**
     * returns the path to the audio file used for the extra feature implementation
     *
     * @return the string to be used as the path to retrieve the audio file
     */
    String getMusicResource();

    /**
     * getter method for if the resource handler is currently playing music
     *
     * @return true if model is prompting to play music, false otherwise
     */
    boolean isAudioPlaying();

    /**
     * sets the current state of the resource handler's music to play (true) or pause (false
     *
     * @param audioPlaying the new state
     */
    void setAudioPlaying(boolean audioPlaying);
    /**
     * returns the model's current GuardianHandler
     *
     * @return model's GuardianHandler
     */
    GuardianHandler getGuardianHandler();


    //exam new features

    /**
     * retrieves the saved article's url with the relevant title from the list of saved articles
     * @param title article title to search by
     * @return the url of the corresponding article
     */
    String getSavedArticleURL(String title);

    /**
     * adds to the saved article list an article from the current article-with-tag search with the given title
     * @param title title of the article to add
     * @return true if the operation is successful, false otherwise or if parameter is null
     */
    boolean addToSavedArticles(String title);

    /**
     * removes from the saved article list an article with the same title as parameter
     * @param title title of the article to remove
     * @return true if the operation is successful, false otherwise or if parameter is null
     */
    boolean removeFromSavedArticles(String title);


    /**
     * Returns a string list of all the article titles from the saved articles list
     * @return string list of saved article titles
     */
    ArrayList<String> getSavedArticles();
}
