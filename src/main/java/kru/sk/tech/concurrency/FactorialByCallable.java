package kru.sk.tech.concurrency;

import java.util.concurrent.*;

public class FactorialByCallable {

  protected static int factorialResult;

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    FactorialByCallableImpl factorial = new FactorialByCallableImpl(-1);
    Future<Integer> future = executorService.submit(factorial);
    try {
      factorialResult = future.get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      System.out.println(e.getCause());
    } finally {
      executorService.shutdown();
    }
    System.out.println(factorialResult);
  }
}

class FactorialByCallableImpl implements Callable<Integer> {
  private final int f;

  public FactorialByCallableImpl(int f) {
    this.f = f;
  }

  @Override
  public Integer call() throws Exception {
    if (f <= 0) {
      throw new Exception("You enter wrong number");
    }
    int result = 1;
    for (int i = 1; i <= f; i++) {
      result *= i;
    }
    return result;
  }
}
