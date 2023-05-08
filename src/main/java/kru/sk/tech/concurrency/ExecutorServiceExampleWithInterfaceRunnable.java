package kru.sk.tech.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExampleWithInterfaceRunnable {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    Future<?> future = executorService.submit(newRunnable("Task 1.1"));

    System.out.println(future.isDone());

    try {
      future.get();
    }
    catch (InterruptedException e) {}
    catch (ExecutionException e) {}

    System.out.println(future.isDone());

    executorService.shutdown();
  }

  private static Runnable newRunnable(String msg) {
    return new Runnable() {
      @Override
      public void run() {
        String completeMsg = Thread.currentThread().getName() + ": " + msg;
        System.out.println(completeMsg);
      }
    };
  }
}
