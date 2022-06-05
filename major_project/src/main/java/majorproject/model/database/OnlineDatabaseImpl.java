package majorproject.model.database;

import majorproject.model.pojos.ResultsPOJO;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Online implementation of the Database interface that uses SQLite to manage the local database file
 */
public class OnlineDatabaseImpl implements Database {

    /**
     * name of the db file
     */
    private static final String appDB = "app.db";

    /**
     * url to connect to in order to process SQL queries
     */
    private static final String appDBURL = "jdbc:sqlite:" + appDB;

    @Override
    public void createDatabase() {
        File appDBFile = new File(appDB);

        if (appDBFile.exists()) {
            return;
        }

        try (Connection ignored = DriverManager.getConnection(appDBURL)) {
            ;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void setupDatabase() {
        String createTagTableSQL =
                """
                    CREATE TABLE IF NOT EXISTS tags (
                        tag_entry_id PRIMARY KEY,
                        tag text NOT NULL
                    );
                """;

        String createResultsTableSQL =
                """
                    CREATE TABLE IF NOT EXISTS results (
                        result_id_pkey int PRIMARY KEY,
                        result_id text NOT NULL,
                        result_web_title text NOT NULL,
                        result_web_publication_date NOT NULL,
                        result_web_url NOT NULL,
                        tag text NOT NULL,
                        FOREIGN KEY(tag) REFERENCES tags(tag)
                    );
                """;

        try (Connection connection = DriverManager.getConnection(appDBURL);
             Statement statement = connection.createStatement()) {
            statement.execute(createTagTableSQL);
            statement.execute(createResultsTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void addTag(String tag) {
        String addTagSQL =
                """
                INSERT INTO tags(tag) VALUES
                    (?)
                """;
        try (Connection connection = DriverManager.getConnection(appDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(addTagSQL)) {
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void addResults(List<ResultsPOJO> results, String tag) {
        String addResultSQL =
                """
                INSERT INTO results(result_id, result_web_title, result_web_publication_date, result_web_url, tag) VALUES
                    (?, ?, ?, ?, ?)
                """;

        for (ResultsPOJO result: results) {
            try (Connection connection = DriverManager.getConnection(appDBURL);
                 PreparedStatement preparedStatement = connection.prepareStatement(addResultSQL)) {
                preparedStatement.setString(1, result.getID());
                preparedStatement.setString(2, result.getWebTitle());
                preparedStatement.setString(3, result.getWebPublicationDate());
                preparedStatement.setString(4, result.getWebURL());
                preparedStatement.setString(5, tag);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    @Override
    public List<ResultsPOJO> retrieveResults(String tag) {
        List<ResultsPOJO> ret = new ArrayList<ResultsPOJO>();
        String allSavesQuery =
                """
                SELECT result_id, result_web_title, result_web_publication_date, result_web_url
                FROM tags AS t
                INNER JOIN results AS r ON t.tag = r.tag
                WHERE t.tag = ?
                """;

        try (Connection connection = DriverManager.getConnection(appDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(allSavesQuery)) {
            preparedStatement.setString(1, tag);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String id = result.getString("result_id");
                String webTitle = result.getString("result_web_title");
                String webPublicationDate = result.getString("result_web_publication_date");
                String webURL = result.getString("result_web_url");
                ResultsPOJO tagResult = new ResultsPOJO(id, webTitle, webPublicationDate, webURL);
                ret.add(tagResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ret;
    }

    @Override
    public boolean queryCheckTagExists(String tag) {
        String determineExistsQuery =
                """
                SELECT tag
                FROM tags
                where tag = ?
                """;
        try (Connection connection = DriverManager.getConnection(appDBURL);
             PreparedStatement preparedStatement = connection.prepareStatement(determineExistsQuery)) {
            preparedStatement.setString(1, tag);
            ResultSet result = preparedStatement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return false;
    }

    @Override
    public void clearCache() {
        String deleteFromTags =
                """
                DELETE
                FROM tags
                """;

        String deleteFromResults =
                """
                DELETE
                FROM results
                """;

        try (Connection connection = DriverManager.getConnection(appDBURL);
             Statement statement = connection.createStatement()) {
            statement.execute(deleteFromTags);
            statement.execute(deleteFromResults);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
