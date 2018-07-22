package jquil;

import java.util.TreeMap;
import java.util.ArrayList;
import java.lang.Math;

/**
 * <h1>Contains procedures that operate on wavefuntions</h1>
 * The Wavefunction class
 * has utilities that are helpul for wavefunctions
 * <p>
 */
public class Wavefunction {
    private ArrayList < String > amplitudes;

    public Wavefunction() {}

    /**
     *Sets the amplitudes of the wavefunction.
     * @param wavefunction represented as an ArraList
     */
    public Wavefunction(ArrayList < String > cl) {
	amplitudes = cl;
    }

    /**
     * Returns amplitudes.
     * @return amplitudes as an ArrayList
     */
    public ArrayList < String > amplitudes() {
	return amplitudes;
    }

    /**
     * The resulting dictionary of this method contain
     * outcomes as keys and the probabilities of those outcomes as values.
     * @return Dictionary of outcome probabilities
     */
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

    /**
     * Transforms the wavefunction from an ArrayList
     * to Paul Dirac's Bra-Ket notation
     * @param Wavefunction object
     * @return Bra-Ket notation as a String
     */
    public static String bra_ket(Wavefunction wv) {
	ArrayList cl = wv.amplitudes();
	int n = (int)(Math.log(cl.size()) / Math.log(2));
        int rows = (int) Math.pow(2, n);
        StringBuilder wf = new StringBuilder("");
	
        for (int i = 0; i < cl.size(); i++) {
	    	
	    StringBuilder sb = new StringBuilder("");
            if (!cl.get(i).equals("0")) {
                sb.append(cl.get(i));
                sb.append("|");
                for (int j = n - 1; j >= 0; j--) {
		    
                    sb.append((i / (int) Math.pow(2, j)) % 2);
                }
                sb.append(">");		
		wf.append(sb + " + ");                   
                
            }
        }
	
	String str = wf.toString();
        return str.substring(0, str.length() - 2).trim();
    }


}
