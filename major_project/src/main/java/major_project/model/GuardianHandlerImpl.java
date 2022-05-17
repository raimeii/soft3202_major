package major_project.model;

import com.google.gson.Gson;

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

public class GuardianHandlerImpl implements GuardianHandler {
    //handles HTTP/mocked calls to the Guardian API
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
    List<ResultsPOJO> tagResults;

    public GuardianHandlerImpl(Database d) {
        database = d;
    }

    @Override
    public ArrayList<String> getMatchingTags(String tag) throws InvalidParameterException {
        if (tag == null) {
            throw new InvalidParameterException("Tag parameter is null");
        }
        //citation for hellohttp
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
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            ;
        }

        return new ArrayList<String>();
    }

    @Override
    public ArrayList<String> getResultsWithTagAPI(String tag) throws InvalidParameterException, IllegalStateException {
        if (tag == null) {
            throw new InvalidParameterException("Tag parameter is null");
        }
        if (database == null) {
            throw new IllegalStateException("Illegal state: calling API method when app is offline");
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
            System.out.println(e.getMessage());
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

        if (database == null) {
            throw new IllegalStateException("Illegal state: calling API method when app is offline");
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



}
