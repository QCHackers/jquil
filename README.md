Quickstart

1. Open `jQuil/src/main/java/jquil/Post.java` and enter your Forest User Id and API key in the `user_id` and `api_key` string variables.

2. ./gradlew build

3. ./gradlew run

4. ./gradlew clean


#Probabilities
```
  WavefunctionSimulator wfnsim = new WavefunctionSimulator();
  QVMConnection quantum_simulator = new QVMConnection();
  Wavefunction wfn = new Wavefunction();
  Program p = new Program(g.H(0));

  wfn = wfnsim.wavefunction(p);
  System.out.println("Before measurement: H|0> = " + wfn.amplitudes());

  p.measure(0, 0);

  for (int i = 0; i < 5; i++) {
   wfn = wfnsim.wavefunction(p);
   System.out.println("After measurement: " + wfn.amplitudes());
  }

```

```
Before measurement: H|0> = [0.71, 0.71]
After measurement: [0, 1]
After measurement: [0, 1]
After measurement: [1, 0]
After measurement: [1, 0]
After measurement: [1, 0]
```
