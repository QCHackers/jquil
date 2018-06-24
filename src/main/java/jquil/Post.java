package jquil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;

public class Post {
    public static void main(String[] args) {
        String user_id = "4c2166b5-a99d-433c-a289-afcfcd1f1bda";
        String api_key = "nmRPAVunQl19TtQz9eMd11iiIsArtUDTaEnsSV6u";

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            HttpPost request = new HttpPost("https://api.rigetti.com/qvm");
            String post = "{\"type\": \"multishot\", \"addresses\": [0, 1], \"trials\": 2, \"compiled-quil\": \"H 0\\nCNOT 0 1\\nMEASURE 0 [0]\\nMEASURE 1 [1]\\n\"}";

            StringEntity params = new StringEntity(post);
            request.addHeader("Accept", "application/octet-stream");
            request.addHeader("X-User-Id", user_id);
            request.addHeader("X-Api-Key", api_key);

            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {

            System.out.println("Request didn't succeed " + e);

        }
    }
}
