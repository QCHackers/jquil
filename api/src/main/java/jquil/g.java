package jquil;

/**
 * <h1>Returns program procedures as strings</h1>
 * The g class represents the gate class. 
 * it is named with one lowercase to make it
 * easy to read and write because it will be called
 * frequently.
 * <p>
 */
public class g {
    //One qubit gates
    
    public static String I(int qubit) {
	return String.format("I %s\n", Integer.toString(qubit));
    }

    public static String X(int qubit) {
	return String.format("X %s\n", Integer.toString(qubit));
    }

    public static String Y(int qubit) {
	return String.format("Y %s\n", Integer.toString(qubit));
    }

    public static String Z(int qubit) {
	return String.format("Z %s\n", Integer.toString(qubit));
    }

    public static String H(int qubit) {
	return String.format("H %s\n", Integer.toString(qubit));
    }
    
    //Two qubit gates
    
    public static String CNOT(int qubit0, int qubit1) {
	return String.format("CNOT %s %s\n", Integer.toString(qubit0),  Integer.toString(qubit1));
    }

    public static String SWAP(int qubit0, int qubit1) {
	return String.format("SWAP %s %s\n", Integer.toString(qubit0),  Integer.toString(qubit1));
    }

    public static String CPHASE(double angle, int qubit0, int qubit1) {
	return String.format("CPHASE (%s) %s %s\n",Double.toString(angle), Integer.toString(qubit0),  Integer.toString(qubit1));
    }

    //For measuring qubits
    
    public static String MEASURE(int qubit, int classical) {
	return String.format("MEASURE %s [%s]\n", Integer.toString(qubit),  Integer.toString(classical));
    }

    //Classical control flow
    
    public static String JUMP_WHEN(String label, int classical) {
	return String.format("JUMP-WHEN %s [%s] %s\n", label, Integer.toString(classical));
    }

    public static String JUMP(String label) {
	return String.format("JUMP %s [%s] %s\n", label);
    }

    public static String LABEL(String label) {
	return String.format("LABEL %s [%s] %s\n", label);
    }

}
