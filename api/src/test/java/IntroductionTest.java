package jquil;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 * <h1>Introduction to quantum programming test</h1>
 * The IntroductionTest class tests 
 * most of the programs on
 * http://pyquil.readthedocs.io/en/latest/intro.html
 * <p>
 */
public class IntroductionTest {

    @Test
    public void State() {
	WavefunctionSimulator wfsim = new WavefunctionSimulator();
     
	Program p = new Program(g.I(0));

	assertEquals(Wavefunction.bra_ket(wfsim.wavefunction(p)), "1|0>");

     
    }
}
