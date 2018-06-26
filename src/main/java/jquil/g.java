package jquil;

public class g {

    public static String X(int qubit) {
        return "X " + Integer.toString(qubit) + "\n";
    }

    public static String Y(int qubit) {
        return "Y " + Integer.toString(qubit) + "\n";
    }

    public static String Z(int qubit) {
        return "Z " + Integer.toString(qubit) + "\n";
    }

    public static String H(int qubit) {
        return "H " + Integer.toString(qubit) + "\n";
    }

    public static String CNOT(int qubit0, int qubit1) {
        return "CNOT " + Integer.toString(qubit0) + " " + Integer.toString(qubit1) + "\n";
    }

    public static String MEASURE(int qubit, int classical) {
        return "MEASURE " + Integer.toString(qubit) + " [" + Integer.toString(classical) + "]" + "\n";
    }

}
