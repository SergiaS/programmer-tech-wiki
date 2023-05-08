package kru.sk.tech.concurrency;

public class ThreadLocalExample {

  public static void main(String[] args) throws InterruptedException {
    MyRunnable myRunnable = new MyRunnable();

    Thread t1 = new Thread(myRunnable);
    Thread t2 = new Thread(myRunnable);

    t1.start();
    t2.start();

    t1.join();
    t2.join();

    String text = "DONE!";
    System.out.println(text);
  }

  public static class MyRunnable implements Runnable {
    private final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    @Override
    public void run() {
      threadLocal.set((int) (Math.random() * 50D));

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println(threadLocal.get() + " : " + Thread.currentThread().getName());
    }
  }

}
