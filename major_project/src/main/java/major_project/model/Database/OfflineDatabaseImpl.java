package major_project.model.Database;

import major_project.model.POJOS.ResultsPOJO;

import java.util.List;

public class OfflineDatabaseImpl implements Database {
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
        return true;
    }

    @Override
    public void clearCache() {

    }
}
