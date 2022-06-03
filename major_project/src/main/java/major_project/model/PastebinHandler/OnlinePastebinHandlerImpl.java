package major_project.model.PastebinHandler;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class OnlinePastebinHandlerImpl implements PastebinHandler {

    @Override
    public String generateOutputReport(String toReport) throws InvalidParameterException {
        if (toReport == null) {
            throw new InvalidParameterException("Parameter is null.");
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postReq = new HttpPost("https://pastebin.com/api/api_post.php");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("api_dev_key", System.getenv("PASTEBIN_API_KEY")));
            nvps.add(new BasicNameValuePair("api_option", "paste"));
            nvps.add(new BasicNameValuePair("api_paste_code", toReport));
            postReq.setEntity(new UrlEncodedFormEntity(nvps));

            try (CloseableHttpResponse response = httpClient.execute(postReq)) {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                InputStream body = entity.getContent();
                String link = new String(body.readAllBytes(), StandardCharsets.UTF_8);
                // and ensure it is fully consumed
                EntityUtils.consume(entity);

                return link;
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        }

        return "";

    }
    //handles HTTP/mocked calls to the pastebin API
}
