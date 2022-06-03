package major_project.model.GuardianHandler;
import major_project.model.POJOS.GuardianPOJO;

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
     * GuardianPOJO.returnResponse().getResults(). Afterwards, checks if the tag is cached in the cache, and caches the
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
}
