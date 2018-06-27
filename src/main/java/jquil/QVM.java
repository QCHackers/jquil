package jquil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class QVM {

    public QVM() {}

    public static String run(Program p) {
        Config c = new Config();
        String user_id = c.get_id();
        String api_key = c.get_key();
        String result = null;
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost("https://api.rigetti.com/qvm");
            JSONObject obj = new JSONObject();
            JSONArray list = new JSONArray();

            obj.put("type", "multishot");
            list.add(1);
            obj.put("addresses", list);
            obj.put("trials", 1);
            obj.put("compiled-quil", p.getProgram());

            request.addHeader("Accept", "application/octet-stream");
            request.addHeader("X-User-Id", user_id);
            request.addHeader("X-Api-Key", api_key);

            request.setEntity(new StringEntity(obj.toString()));
            HttpResponse response = httpClient.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            //Might need to be in a while() loop
            result = rd.readLine();
            rd.close();

        } catch (Exception e) {

            System.out.println("Request didn't succeed " + e);

        } finally {
            return result;

        }
    }

}
