package major_project.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GuardianHandler {
    //handles HTTP/mocked calls to the Guardian API
    private GuardianPOJO currentTagResponse;

    private GuardianPOJO lastResultResponse;


    public ArrayList<String> getMatchingTags(String tag) {
        if (tag == null) {
            return null;
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

        return null;
    }

    public ArrayList<String> getResultsWithTag(String tag) {
        if (tag == null) {
            return null;
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
            lastResultResponse = gson.fromJson(response.body(), GuardianPOJO.class);
            ArrayList<String> ret = new ArrayList<>();

            for (ResultsPOJO result : lastResultResponse.returnResponse().getResults()) {
                ret.add(result.toString());
            }

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

    public GuardianPOJO getCurrentTagResponse() {
        return currentTagResponse;
    }

    public GuardianPOJO getLastResultResponse() {
        return lastResultResponse;
    }

    public String getURL(String title) {
        if (title == null) {
            return null;
        }
        //whenever this is called, the latest response would have been set from a getResultsWithTag, so we can just go through that and find the same POJO we displayer
        //marking because I feel like this might cause an error in the future
        List<ResultsPOJO> currentResults = lastResultResponse.returnResponse().getResults();

        for (ResultsPOJO result: currentResults) {
            if (result.toString().equals(title)) {
                return result.getWebURL();
            }
        }
        return null;
    }
}
