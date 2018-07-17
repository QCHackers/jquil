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

import jquil.Program;


public class QVMConnection {
 private static Wavefunction wva;
 private static ArrayList < String > cl;

 public QVMConnection() {}

 @SuppressWarnings("unchecked")
 public static ArrayList < ArrayList < String > > run(Program p, List < Integer > cl, int trials) {
  WavefunctionSimulator wfnsim = new WavefunctionSimulator();
  ArrayList < String > classical_measurements = new ArrayList < String > ();
  ArrayList < String > wvf = new ArrayList();
  ArrayList < ArrayList < String > > trials_list = new ArrayList < ArrayList < String >> ();

  for (int i: cl) {
   p.measure(i, i);

  }

  for (int j = 0; j < trials; j++) {
   wvf = wfnsim.wavefunction(p, cl).amplitudes();

   for (int i = 0; i < cl.size(); i++) {

    if (wvf.get(i).equals("1") && wvf.get(i + 1).equals("0")) {
     classical_measurements.add("1");
    } else if (wvf.get(i).equals("0") && wvf.get(i + 1).equals("1")) {
     classical_measurements.add("0");
    } else {
     classical_measurements.add("0");

    }

   }
   trials_list.add(classical_measurements);
   classical_measurements = new ArrayList < String > ();
  }

  return trials_list;

 }


 public static ArrayList < ArrayList < String > > run(Program p, List < Integer > cl) {
  return run(p, cl, 1);

 }

}
