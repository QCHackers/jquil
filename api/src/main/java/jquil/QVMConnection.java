package jquil;

import java.util.List;
import java.io.UnsupportedEncodingException;

public class QVMConnection {
    
    public QVMConnection() {}
    
    /**
     * Uses API to run quantum program and returns
     *result as a string.
     * @param program 
     * @param which classical addresses
     * the program should run with
     * @param how many times to run program
     * @return result of executed program
     */
    @SuppressWarnings("unchecked")
    public static String run(Program p, List < Integer > classical_addresses, int trials) {
	
	String result = null;
	
	try{
	    result = new String(PostQuil.post(p, classical_addresses, true), "UTF-8");
	    
	}catch(UnsupportedEncodingException e){
	    
	    e.printStackTrace();
	}
	return result;  

    }

     /**
     * Overloaded method to run program once.
     * @param program 
     * @param which classical addresses
     * the program should run with
     * @return result of executed program
     */
    public static String run(Program p, List < Integer > cl) {
	return run(p, cl, 1);
    }

}
