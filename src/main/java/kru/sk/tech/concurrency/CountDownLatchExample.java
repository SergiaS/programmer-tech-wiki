package kru.sk.tech.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
  static CountDownLatch countDownLatch = new CountDownLatch(3);

  private static void marketStaffIsOnPlace() throws InterruptedException {
    Thread.sleep(2000);
    System.out.println("Market staff came to work");
    countDownLatch.countDown();
    System.out.println("countDownLatch = " + countDownLatch);
  }

  private static void everythingIsReady() throws InterruptedException {
    Thread.sleep(3000);
    System.out.println("Everything is ready, so let's open market");
    countDownLatch.countDown();
    System.out.println("countDownLatch = " + countDownLatch);
  }

  private static void openMarket() throws InterruptedException {
    Thread.sleep(4000);
    System.out.println("Market is opened");
    countDownLatch.countDown();
    System.out.println("countDownLatch = " + countDownLatch);
  }

  public static void main(String[] args) throws InterruptedException {
    new Friend("Bob", countDownLatch);
    new Friend("Carl", countDownLatch);
    new Friend("Ted", countDownLatch);
    new Friend("Mina", countDownLatch);
    new Friend("Helga", countDownLatch);

    marketStaffIsOnPlace();
    everythingIsReady();
    openMarket();
  }
}

class Friend extends Thread {
  private final String name;
  private final CountDownLatch countDownLatch;

  public Friend(String name, CountDownLatch countDownLatch) {
    this.name = name;
    this.countDownLatch = countDownLatch;
    this.start();
  }

  @Override
  public void run() {
    try {
      countDownLatch.await();
      System.out.println(name + " shopping");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}