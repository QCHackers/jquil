package jquil;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class Wavefunction {
 ArrayList < String > amplitudes;

 public Wavefunction() {}

 public Wavefunction(ArrayList < String > cl) {
  amplitudes = cl;
 }

 public ArrayList < String > amplitudes() {
  return amplitudes;
 }


 public TreeMap get_outcome_probs() {
  TreeMap tm = new TreeMap();
  ArrayList < String > amplitudes = this.amplitudes();
  int n = amplitudes.size() / 2;
  int rows = (int) Math.pow(2, n);
  StringBuilder wf = new StringBuilder("");

  for (int i = 0; i < rows; i++) {
   StringBuilder sb = new StringBuilder("");
   for (int j = n - 1; j >= 0; j--) {
    sb.append((i / (int) Math.pow(2, j)) % 2);
   }
   tm.put(sb.toString(), amplitudes.get(i));
  }

  return tm;
 }

}
