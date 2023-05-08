package kru.sk.tech.concurrency;

// приклад сінхронізації монітору одного і тогож об'єкту
public class SynchronizedBlocksExample {

  private static final Object lock = new Object();

  public static void main(String[] args) {
    Thread thread1 = new Thread(new RunnableMobileCall());
    Thread thread2 = new Thread(new RunnableSkypeCall());
    Thread thread3 = new Thread(new RunnableWhatsappCall());
    thread1.start();
    thread2.start();
    thread3.start();
  }

  protected void mobileCall() {
    synchronized (lock) {
      System.out.println("Mobile call starts!");
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Mobile call ends!");
    }
  }

  protected void skypeCall() {
    synchronized (lock) {
      System.out.println("Skype call starts!");
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Skype call ends!");
    }
  }

  protected synchronized void whatsappCall() {
    synchronized (lock) {
      System.out.println("WhatsApp call starts!");
      try {
        Thread.sleep(7000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("WhatsApp call ends!");
    }
  }
}


class RunnableMobileCall implements Runnable {
  @Override
  public void run() {
    new SynchronizedBlocksExample().mobileCall();
  }
}

class RunnableSkypeCall implements Runnable {
  @Override
  public void run() {
    new SynchronizedBlocksExample().skypeCall();
  }
}

class RunnableWhatsappCall implements Runnable {
  @Override
  public void run() {
    new SynchronizedBlocksExample().whatsappCall();
  }
}