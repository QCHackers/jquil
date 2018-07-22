package jquil;

/**
* <h1>Simulates quantum programs</h1>
* The Program class defines a program object
* that can be instatiated and modified.
* <p>
*/
public class Program {
    private String program = "";
    //for keeping track on how to assign addresses for classical control flow
    private int adr = 1;

    /**
     * Instatiantes a program by
     * concatenating supplied quantum gates
     * to a string
     * @param List of of quantum gates
     */
    public Program(String...args) {
	for (String arg: args) {
	    program += arg;
	}
    }

    /** 
     * Returns string representation of program.
     * @return  String program
     */
    public String getProgram() {
	return program;
    }
    
    /** 
     * Adds a string representation of program to this program 
     * @param instruction
     * @return Program object with supplied instruction 
     * appended to it
     */
    public Program inst(String inst) {
	program += inst;
	return new Program(program);
    }

    /**
     *Performs a measurement on the program
     * @param address qubit
     * @param address bit
     * @return Program with MEASURE instruction 
     * appended to it
     */
    public Program measure(int qubit_index, int classical_reg) {
	return this.inst(g.MEASURE(qubit_index, classical_reg));
    }

    /**
     * Peforms conditional classical control flow on quantum programs.
     * @param address of bit
     * @param if branch of program
     * @param else branch of program
     * @return Program with embeded if_then control flow
     */
    public Program if_then(int classical_reg, Program if_program, Program else_program){
	String counter = Integer.toString(adr);
	String counter_incr = Integer.toString(adr+1);
	
	this.inst(g.JUMP_WHEN("@THEN" + counter, classical_reg));
	this.inst(else_program.getProgram());
	this.inst(g.JUMP("@END" + counter_incr));
	this.inst(g.LABEL("@THEN" + counter));
	this.inst(if_program.getProgram());
	return this.inst(g.LABEL("@END" + counter_incr));
    }

    /** 
     * Peforms conditional classical control flow on quantum programs
     * without else conditional.
     * @param address of bit
     * @param  if branch of program
     * @return Program with embeded if control flow
     */
    public Program if_then(int classical_reg, Program if_program){
	return this.if_then(classical_reg, if_program, new Program());
    }

    /**
     *Concatenates two programs together.
     * @param program to concatenate
     * @return concatenated program
     */
    public Program add(Program b){	
	return new Program(this.getProgram() +  b.getProgram());
    }
}
