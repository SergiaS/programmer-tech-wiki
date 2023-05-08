package kru.sk.tech.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FactorialByRunnable {

  protected static int factorialResult;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    FactorialByRunnableImpl factorial = new FactorialByRunnableImpl(5);
    executorService.execute(factorial);
    executorService.shutdown();
    executorService.awaitTermination(10, TimeUnit.SECONDS);
    System.out.println(factorialResult);
  }
}

class FactorialByRunnableImpl implements Runnable {
  private final int f;

  public FactorialByRunnableImpl(int f) {
    this.f = f;
  }

  @Override
  public void run() {
    if (f <= 0) {
      System.out.println("You enter wrong number");
      return;
    }
    int result = 1;
    for (int i = 1; i <= f; i++) {
      result *= i;
    }
    FactorialByRunnable.factorialResult = result;
  }
}