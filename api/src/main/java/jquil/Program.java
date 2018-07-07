package jquil;

public class Program {
    String program = "";

    public Program(String...args) {
        for (String arg: args) {
            program += arg;
        }
    }

    public String getProgram() {
        return program;
    }

    public String inst(String inst) {
        return inst;

    }
}
