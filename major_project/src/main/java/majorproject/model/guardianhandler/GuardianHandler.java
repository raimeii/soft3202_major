package majorproject.model.guardianhandler;
import majorproject.model.pojos.GuardianPOJO;

import java.util.ArrayList;

public interface GuardianHandler {

    /**
     * Makes a get request to the TheGuardian API to retrieve the first 10 partial matches to the tag parameter, setting
     * the handler's currentTagResponse GuardianPOJO to the return response parsed by GSON.
     *
     * @param tag the tag to search partial matches for
     * @return an array list of the strings of the tags that are a partial match to the parameter
     */
    ArrayList<String> getMatchingTags(String tag);

    /**
     * Makes a get request to the TheGuardian API to retrieve the first 10 results with the tag specified. The return
     * response is a GuardianPOJO, then the handler's tagResults list of ResultsPOJOS is set to
     * the returned response's results. Afterwards, checks if the tag is cached in the cache, and caches the
     * tag and results if it does not. Finally, collects the string representation  of the tagResults list, sorts
     * alphabetically, and returns it as an array list of strings.
     *
     * @param tag tag to search results by
     * @return string representation of results
     */
    ArrayList<String> getResultsWithTagAPI(String tag);

    /**
     * Retrieves a cached result with tag that matches the parameter, collects its string representations, sorts
     * alphabetically, and returns it as an array list of strings.
     *
     * @param tag tag to search results by
     * @return
     */
    ArrayList<String> getResultsWithTagDB(String tag);

    /**
     * Loop through the resultsList of ResultsPOJOS, and returns the url of the ResultsPOJO whose string representation
     * matches the title.
     *
     * @param title title to compare string representation of ResultsPOJO by
     * @return url of the requisite ResultsPOJO
     */
    String getURL(String title);



    /**
     * Returns the GuardianPOJO current tag response
     *
     * @return GuardianPOJO of the current tag response from the api.
     */
    GuardianPOJO getCurrentTagResponse();

    //exam new features

    /**
     * Retrieves the URL of the ResultsPOJO object with the matching title from the saved list of results
     * @param title title of the article we are interested in
     * @return URL for that particular article
     */
    String getURLFromSaved(String title);

    /**
     * Goes through the current tag search article results and adds the result with the matching title to the saved list of results
     * @param title title of the article we are interested in
     * @return true if the operation is successful, false otherwise or if the parameter is null
     */
    boolean addToSaved(String title);

    /**
     * Goes through the current saved articles list and removes the result with the matching title from the list
     * @param title title of the article we are interested in
     * @return true if the operation was successful, false otherwise or if the parameter is null
     */
    boolean removeFromSaved(String title);

    /**
     * Returns the string representation (title) of each result stored in the savedResults list
     * @return string list of article titles
     */
    ArrayList<String> getResultsFromSaved();
}
