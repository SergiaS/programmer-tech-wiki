package kru.sk.tech.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExampleWithMethodInvokeAny {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    List<Callable<String>> callables = new ArrayList<>();
    callables.add(newCallable("Task 1.1"));
    callables.add(newCallable("Task 1.2"));
    callables.add(newCallable("Task 1.3"));

    try {
      String result = executorService.invokeAny(callables);
      System.out.println(result);
    }
    catch (InterruptedException e) {}
    catch (ExecutionException e) {}

    executorService.shutdown();
  }

  private static Callable newCallable(String msg) {
    return new Callable() {
      @Override
      public Object call() {
        String completeMsg = Thread.currentThread().getName() + ": " + msg;
        return completeMsg;
      }
    };
  }
}
