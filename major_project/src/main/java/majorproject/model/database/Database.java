package majorproject.model.database;

import majorproject.model.pojos.ResultsPOJO;

import java.util.List;

public interface Database {

    /**
     * Creates the database file for caching results
     */
    void createDatabase() ;

    /**
     * setups the tables in the database file to be used in the caching
     */
    void setupDatabase();

    /**
     * Performs a sql query to insert a new record with the tag parameter
     * @param tag new tag record
     */
    void addTag(String tag);

    /**
     * Loops through the results list of ResultsPOJOS and performs a sql query to insert each result as a new record in
     * the results table, with the tag to link it to the tags table
     * @param results list of ResultsPOJOs to be inserted into the SQL table
     * @param tag tag associated with these results
     */
    void addResults(List<ResultsPOJO> results, String tag);

    /**
     * Performs an sql query to retrieve all results with a certain tag, and for each result found, creates a
     * ResultsPOJO with that result's details. Collects all results and returns it as a list.
     * @param tag tag to retrieve results by
     * @return list of ResultsPOJO with the specified tag.
     */
    List<ResultsPOJO> retrieveResults(String tag);

    /**
     * Checks if the tag given has been cached into the database before
     * @param tag tag to check
     * @return true if the tag has been cached before, false otherwise
     */
    boolean queryCheckTagExists(String tag);

    /**
     * performs delete queries for both tables to reset the cache
     */
    void clearCache();
}
