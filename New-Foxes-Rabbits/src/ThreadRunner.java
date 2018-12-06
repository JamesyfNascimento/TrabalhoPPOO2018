

public class ThreadRunner implements Runnable {

  private int numSteps = 0;
  private boolean infinite = false;
  private boolean threadRun;

  public ThreadRunner() {

  }

  public void startRun(int numSteps) {
    if (numSteps == 0) {
      this.numSteps = 1;
      infinite = true;
    } else {
      this.numSteps += numSteps;
    }

    try {
      if (!threadRun && Thread.currentThread().isAlive()) {
        new Thread(this).start();
      }
    } catch (IllegalThreadStateException e) {
      infinite = false;
      System.out.println("InterruptedException");
    }
  }

  
  public void stop() {
    numSteps = 0;
    threadRun = false;
    infinite = false;
  }

  
  @Override
  public void run() {
    threadRun = true;
    Simulador simulator = Principal.getSimulador();

    while (threadRun && numSteps > 0 && simulator.getSimuladorTela().isViable(simulator.getCampo())) {
      Principal.getSimulador().umPasso();
      numSteps--;
      while (infinite && numSteps == 0) {
        numSteps++;
      }

      try {
        Thread.sleep(0);
      } catch (Exception e) {
        System.out.println("InterruptedException");
      }
    }
    threadRun = false;
  }
}
