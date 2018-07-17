package jquil;

import jquil.g;

public class Program {
 private String program = "";

 public Program(String...args) {
  for (String arg: args) {
   program += arg;
  }
 }

 public String getProgram() {
  return program;
 }

 public Program inst(String inst) {
  program += inst;
  return new Program(program);

 }

 public Program measure(int qubit_index, int classical_reg) {
  return this.inst(g.MEASURE(qubit_index, classical_reg));

 }
}
