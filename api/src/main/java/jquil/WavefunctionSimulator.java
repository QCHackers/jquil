package jquil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexFormat;

import jquil.Wavefunction;
import jquil.PostQuil;

/**
 * <h1>Simulates Wavefunctions</h1>
 * The Wavefunction class defines
 * procedures that act on wavefunctions
 * <p>
 */
public class WavefunctionSimulator {

    public WavefunctionSimulator() {}

    /**
     * Converts sign byte to unsigned
     * @param byte
     * @return unsigned byte
     */
    private static int unsignedToBytes(byte b) {
	return b & 0xFF;
    }
    
    /** 
     * Decodes wavefunction piece by piece
     * @param  piece of wavefunction
     * @return that piece as a number
     */
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

    /** 
     * Rounds to next multiple number
     * @param number
     * @param multiple
     * @return rounded number
     */
    private static int round_to_next_multiple(int n, int m) {
	if (n % m == 0) {
	    return n;
	} else {
	    return n + m - n % m;
	}
    }

    /** 
     * Decodes wavefunction of program
     * @param program
     * @param classical addresses
     * @param whether or not to run program
     * @return wavefunction of program
     */
    public static Wavefunction wavefunction(Program p, List < Integer > classical_addresses, boolean run) {
     
	byte[] bytes = PostQuil.post(p, classical_addresses, run);
	ArrayList < String > cl = new ArrayList();
	String result = null;
	Double re;
	Double im;
	ComplexFormat format = new ComplexFormat();
	int OCTETS_PER_DOUBLE_FLOAT = 8;
	int OCTETS_PER_COMPLEX_DOUBLE = 2 * OCTETS_PER_DOUBLE_FLOAT;
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

     
	return new Wavefunction(cl);
    }

    /** 
     * Decodes wavefunction of unxecuted program
     * @param program
     * @param classical addresses
     * @return wavefunction of program
     */
    public static Wavefunction wavefunction(Program p, List < Integer > classical_addresses) {
	return wavefunction(p, classical_addresses, false);
    }
    
    /** 
     * Decodes wavefunction of unxecuted program 
     * with one classical address
     * @param program
     * @return wavefunction of program
     */
    @SuppressWarnings("unchecked")
    public static Wavefunction wavefunction(Program p) {
	return wavefunction(p, Arrays.asList(1));
    } 
}
