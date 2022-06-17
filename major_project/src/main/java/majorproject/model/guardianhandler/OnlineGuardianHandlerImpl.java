package majorproject.model.guardianhandler;

import com.google.gson.Gson;
import majorproject.model.database.Database;
import majorproject.model.pojos.GuardianPOJO;
import majorproject.model.pojos.ResultsPOJO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Online implementation of the GuardianHandler interface, makes API calls to retrieve information
 */
public class OnlineGuardianHandlerImpl implements GuardianHandler {
    /**
     * GuardianPOJO object containing the last response from the theguardian API
     */
    private GuardianPOJO currentTagResponse;

    /**
     * Database handler to process SQL calls when caching
     */
    private final Database database;

    /**
     * List of ResultsPOJOS to refer to when looking for result URLs
     */
    private List<ResultsPOJO> tagResults;

    private final List<ResultsPOJO> savedResults = new ArrayList<>();

    public OnlineGuardianHandlerImpl(Database d) {
        database = d;
    }

    @Override
    public ArrayList<String> getMatchingTags(String tag) throws InvalidParameterException {
        if (tag == null) {
            throw new InvalidParameterException("Tag parameter is null");
        }
        try {
            String URIVariable = String.format("https://content.guardianapis.com/tags?web-title=%s&api-key=%s", tag, System.getenv("INPUT_API_KEY"));
            HttpRequest request = HttpRequest.newBuilder(new URI(URIVariable))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            //set current response to a new
            currentTagResponse = gson.fromJson(response.body(), GuardianPOJO.class);
            ArrayList<String> ret = new ArrayList<>();

            for (ResultsPOJO result : currentTagResponse.returnResponse().getResults()) {
                ret.add(result.getID());
            }

            return ret;


        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            return null;
        } catch (URISyntaxException ignored) {
            ;
        }

        return new ArrayList<String>();
    }

    @Override
    public ArrayList<String> getResultsWithTagAPI(String tag) throws InvalidParameterException {
        if (tag == null) {
            throw new InvalidParameterException("Tag parameter is null");
        }
        try {
            String URIVariable = String.format("https://content.guardianapis.com/search?tag=%s&api-key=%s", tag, System.getenv("INPUT_API_KEY"));
            HttpRequest request = HttpRequest.newBuilder(new URI(URIVariable))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            //set current response to a new
            GuardianPOJO lastResultResponse = gson.fromJson(response.body(), GuardianPOJO.class);
            tagResults = lastResultResponse.returnResponse().getResults();

            if (!database.queryCheckTagExists(tag)){
                database.addTag(tag);
                database.addResults(tagResults, tag);
            }

            ArrayList<String> ret = new ArrayList<>();

            for (ResultsPOJO result : tagResults) {
                ret.add(result.toString());
            }
            Collections.sort(ret);

            return ret;


        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            return null;
        } catch (URISyntaxException ignored) {
            ;
        }
        return null;
    }

    @Override
    public ArrayList<String> getResultsWithTagDB(String tag) throws InvalidParameterException {
        if (tag == null) {
            throw new InvalidParameterException("Tag parameter is null");
        }
        ArrayList<String> ret = new ArrayList<>();
        tagResults = database.retrieveResults(tag);
        for (ResultsPOJO result: tagResults) {
            ret.add(result.toString());
        }
        Collections.sort(ret);
        return ret;
    }

    @Override
    public GuardianPOJO getCurrentTagResponse() {
        return currentTagResponse;
    }


    @Override
    public String getURL(String title) throws InvalidParameterException {
        if (title == null) {
            throw new InvalidParameterException("Web title is null");
        }
        for (ResultsPOJO result: tagResults) {
            if (result.toString().equals(title)) {
                return result.getWebURL();
            }
        }
        return null;
    }

    //exam implementation
    @Override
    public String getURLFromSaved(String title) {
        if (title == null) {
            throw new InvalidParameterException("Web title is null");
        }
        for (ResultsPOJO result: savedResults) {
            if (result.toString().equals(title)) {
                return result.getWebURL();
            }
        }
        return null;
    }

    @Override
    public boolean addToSaved(String title) {
        if (title == null) {
            return false;
        }
        for (ResultsPOJO result: tagResults) {
            if (result.toString().equals(title)) {
                savedResults.add(result);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFromSaved(String title) {
        if (title == null) {
            return false;
        }
        for (ResultsPOJO result: savedResults) {
            if (result.toString().equals(title)) {
                savedResults.remove(result);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getResultsFromSaved() {
        ArrayList<String> ret = new ArrayList<>();
        for (ResultsPOJO results: savedResults) {
            ret.add(results.toString());
        }
        return ret;
    }


}
