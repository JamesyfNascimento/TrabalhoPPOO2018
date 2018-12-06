/**
 * Classe que cuida dos passos da iteração
 * @author Anderson, Isabela, James
 */

public class ThreadRunner implements Runnable {
  
    private int numSteps = 0;
    private boolean infinite = false;
    private boolean threadRun;

  public ThreadRunner() {

  }
  /**
   * Número de passos da execução
   * @param numSteps 
   */
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

  /**
   * Para execução
   */
  public void stop() {
    numSteps = 0;
    threadRun = false;
    infinite = false;
  }

  /**
   * Executa o simulador, determina o tempo entre a execução de um passo para 
   * outro
   */
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
        //Dormir 1 segundo
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("InterruptedException");
      }
    }
    threadRun = false;
  }
}
