package jquil;

public class EPR extends Program {

    public static void main(String[] args) {
        QVM qvm = new QVM();
        Program p = new Program(g.H(0),
            g.CNOT(0, 1),
            g.MEASURE(0, 0));

        //p.inst(g.X(0));
        System.out.println(qvm.run(p));

    }

}
