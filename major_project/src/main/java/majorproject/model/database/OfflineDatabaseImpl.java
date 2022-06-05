package majorproject.model.database;

import majorproject.model.pojos.ResultsPOJO;

import java.util.List;


/**
 * Offline implementation, no logic in methods that return void, return null/false otherwise
 */
public class OfflineDatabaseImpl implements Database {//handles HTTP/mocked calls to the Guardian API

    @Override
    public void createDatabase() {

    }

    @Override
    public void setupDatabase() {

    }

    @Override
    public void addTag(String tag) {

    }

    @Override
    public void addResults(List<ResultsPOJO> results, String tag) {

    }

    @Override
    public List<ResultsPOJO> retrieveResults(String tag) {
        return null;
    }

    @Override
    public boolean queryCheckTagExists(String tag) {
        return false;
    }

    @Override
    public void clearCache() {

    }
}
