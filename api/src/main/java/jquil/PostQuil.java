package jquil;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.commons.io.IOUtils;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.InputStream;

import jquil.Config;

import java.util.List;

/**
 * <h1>Posts quantum programs to Rigetti Forest API</h1>
 * The PostQuil class sends quantum programs to Rigetti's
 * servers and returns the response.
 * <p>
 */
public class PostQuil{

    public PostQuil(){}

    /** Sends a post request with quantum programs
     * and returns the result as a byte array
     * @param quantum program
     * @param classical addresses
     * @param whether or not to execute the program
     * @return result of response as a byte array
     */
    public static byte[] post(Program p, List < Integer > classical_addresses, boolean run){
	Config c = new Config();
	String user_id = c.get_id();
	String api_key = c.get_key();
	byte[] post_result = null;
	
	try {
	    HttpClient httpClient = HttpClientBuilder.create().build();
	    HttpPost request = new HttpPost("https://api.rigetti.com/qvm");
	    JSONObject obj = new JSONObject();
	    JSONArray list = new JSONArray();
	    
	    if(run){
		obj.put("type","multishot");
	    }
	    else{       
		obj.put("type", "wavefunction");
	    }
	    
	    for (int i: classical_addresses) {
		list.add(i);
	    }

	    obj.put("addresses", list);
	    obj.put("trials", 1);
	    obj.put("compiled-quil", p.getProgram());

	    request.addHeader("Accept", "application/octet-stream");
	    request.addHeader("X-User-Id", user_id);
	    request.addHeader("X-Api-Key", api_key);

	    request.setEntity(new StringEntity(obj.toString()));
	    HttpResponse response = httpClient.execute(request);
	    InputStream is = response.getEntity().getContent();
	    post_result = IOUtils.toByteArray(is);
	    
	    return post_result;

	} catch (Exception e) {
	    e.printStackTrace();

	}
	
	return post_result;
    }
    
}
