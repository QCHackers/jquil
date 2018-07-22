# jquil
Contact: vtomole@iastate.edu

## Installation

1. Open `~/.jquilconfig.properties ` and enter your Forest User Id and API key:

   ```
   user_id=<YOUR USER ID>
   api_key=<YOUR API KEY>
   ```
You can get an API key from [Rigetti Forest](http://forest.rigetti.com/) if you don't have one.

## Hello World

The Hello World of quantum programming is preparing and measuring the [Bell State](https://www.quantiki.org/wiki/bell-state). Let's do that!

```java
WavefunctionSimulator wvfsim = new WavefunctionSimulator();
	
Program p = new Program(g.H(0),
			g.CNOT(0,1));   

System.out.println("Bell state " +  Wavefunction.bra_ket(wvfsim.wavefunction(p)));         
```
These qubits are entangled. The result will be

```
Bell state 0.71|00> + 0.71|11>
```
This Bell state will always measure to the same values.

```java
p.measure(0,0);
p.measure(1,1);
	
System.out.println("Bell state after measurement " + QVMConnection.run(p, Arrays.asList(0, 1)));

```

If one qubit measures to `1`, the other will be `1` and vice versa.

## jquil as a library

`compile 'org.qchackers.jquil:api:0.0.1'` to use jquil in your programs.

Check out [jgrove](https://github.com/QCHackers/jgrove) for more quantum programs.
