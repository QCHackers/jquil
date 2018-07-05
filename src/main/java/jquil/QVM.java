package jquil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexFormat;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class QVM {

    public QVM() {}

    public static String run(Program p) {
        Config c = new Config();
        String user_id = c.get_id();
        String api_key = c.get_key();
        String result = null;

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
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
            e.printStackTrace();

        } finally {
            return result;

        }
    }

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    private static long decode(byte[] a1) {
        int oc = 0;
        long bt = 0;
        byte b;

        for (int i = 0; i < a1.length; i++) {
            b = a1[i];
            oc = unsignedToBytes(b);
            bt = oc | bt << 8;
        }
        return bt;
    }

    private static int round_to_next_multiple(int n, int m) {
        if (n % m == 0) {
            return n;
        } else {
            return n + m - n % m;
        }
    }

    private static String prettyPrintWf(int n, ArrayList < String > cl) {
        int rows = (int) Math.pow(2, n);
        StringBuilder wf = new StringBuilder("");

        for (int i = 0; i < rows; i++) {
            if (!cl.get(i).equals("0")) {
                StringBuilder sb = new StringBuilder("");
                sb.append(cl.get(i));
                sb.append("|");
                for (int j = n - 1; j >= 0; j--) {

                    sb.append((i / (int) Math.pow(2, j)) % 2);
                }
                sb.append(">");
                if (i == rows - 1) {
                    wf.append(sb);
                } else {
                    wf.append(sb + " + ");
                }
            }
        }
        return wf.toString();
    }

    public static String wavefunction(Program p, List < Integer > classical_addresses) {
        Config c = new Config();
        String user_id = c.get_id();
        String api_key = c.get_key();

        String result = null;
        byte[] bytes = null;
        Double re;
        Double im;
        ArrayList < String > cl = new ArrayList();
        ComplexFormat format = new ComplexFormat();
        int OCTETS_PER_DOUBLE_FLOAT = 8;
        int OCTETS_PER_COMPLEX_DOUBLE = 2 * OCTETS_PER_DOUBLE_FLOAT;


        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("https://api.rigetti.com/qvm");
            JSONObject obj = new JSONObject();
            JSONArray list = new JSONArray();

            obj.put("type", "wavefunction");
            list.add(0);
            obj.put("addresses", list);
            obj.put("trials", 1);
            obj.put("compiled-quil", p.getProgram());

            request.addHeader("Accept", "application/octet-stream");
            request.addHeader("X-User-Id", user_id);
            request.addHeader("X-Api-Key", api_key);

            request.setEntity(new StringEntity(obj.toString()));
            HttpResponse response = httpClient.execute(request);
            InputStream is = response.getEntity().getContent();

            bytes = IOUtils.toByteArray(is);

            int num_octets = bytes.length;
            int num_addresses = classical_addresses.size();
            int num_memory_octets = round_to_next_multiple(6, 8) / 8;
            int num_wavefunction_octets = num_octets - num_memory_octets;

            for (int i = num_memory_octets; i < num_octets; i = i + OCTETS_PER_COMPLEX_DOUBLE) {

                byte[] a1 = Arrays.copyOfRange(bytes, i, i + OCTETS_PER_DOUBLE_FLOAT);
                byte[] a2 = Arrays.copyOfRange(bytes, i + OCTETS_PER_DOUBLE_FLOAT, i + OCTETS_PER_COMPLEX_DOUBLE);

                re = Double.longBitsToDouble(decode(a1));
                im = Double.longBitsToDouble(decode(a2));
                Complex cx = new Complex(re, im);
                result = format.format(cx);
                cl.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            return prettyPrintWf(cl.size() / 2, cl);


        }

    }
}
