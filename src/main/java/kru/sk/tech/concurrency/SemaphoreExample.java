package kru.sk.tech.concurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
  public static void main(String[] args) {
    Semaphore callBox = new Semaphore(2);

    new Person("Bob", callBox);
    new Person("Carl", callBox);
    new Person("Mina", callBox);
    new Person("Kate", callBox);
    new Person("Ted", callBox);
  }
}

class Person extends Thread {
  private final String name;
  private final Semaphore callBox;

  Person(String name, Semaphore callBox) {
    this.name = name;
    this.callBox = callBox;
    this.start();
  }

  @Override
  public void run() {
    try {
      System.out.println(name + " - 1 - WAIT");
      callBox.acquire();
      System.out.println(name + " - 2 - USE");
      sleep(2000);
      System.out.println(name + " - 3 - END");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      callBox.release();
    }
  }
}
