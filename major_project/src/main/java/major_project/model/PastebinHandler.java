package major_project.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class PastebinHandler {
    public static String generateOutputReport(AppModel model) {
        try {
            String URL = String.format("https://pastebin.com/api/api_post.php?api_dev_key=%s&api_paste_code=test&api_option=paste", System.getenv("OUTPUT_API_KEY"));
            System.out.println(URL);

            HttpRequest request = HttpRequest.newBuilder(new URI(URL))
                    .header("Content-Type", "application/x-www-form-urlencoded; UTF-8")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            System.out.println(request.headers());

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + response.body());


        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

        return null;

    }
    //handles HTTP/mocked calls to the pastebin API
}
