# Multithreading
* [Обзор java.util.concurrent.*](https://habr.com/ru/company/luxoft/blog/157273/)

Класс `Thread` имплементирует интерфейс `Runnable` у которого есть метод `run()`.

Поток запускается методом `start()`, который сам вызовет метод `run()`.

**Мы сами никогда не должны запускать метод `run()`.**

> Никто не гарантирует, что нить (Thread) можно остановить! 
> Она может остановиться только сама. 

> Последовательность выполнения потоков, программист контролировать не может!

> Если нить (Thread) обращается к статическому методу (а он общий для всех), то блокируется мьютекс класса, и все статические методы становятся заблокированными. 

> Технология context switch, где происходит постоянное переключение выполнения потока - сначала выполняется одно задание, 
> потом поток ставится на паузу и начинает или продолжает выполнение другое задание... 

> Метод `join()` говорит, чтобы поток в котором он был вызван (не **на** котором, а именно **в**) - подождал выполнения потока на котором был вызван.
> В перегруженный метод `join()` можно передать время ожидания потока, по прошествии которого, если ожидаемый поток не пришел, 
тогда главный поток продолжит свою работу.

***


## ThreadLocal
* [ScopedValue vs ThreadLocal. Новий крок в еволюції Java](https://dou.ua/forums/topic/43126/)

Головне призначення **ThreadLocal** — зберігання змінних з областю видимості (scope) у поточному потоці.

Всі значення зберігаються не в самому **ThreadLocal**, а в самому потоці.

> Значення **ThreadLocal** буде видно лише на конкретному потоці!

> ThreadLocal можна відключити для поточного потоку! 
> Для цього є спеціальна бітова маска, яку потрібно вказати при створенні потоку (в його характеристиках):
> 
> `static final int NO_THREAD_LOCALS = 1 << 1;`
>
> У такому разі метод get() завжди повертає початкове значення.`


## Як працюють потоки

### Platform threads
* Кожний екземпляр `java.lang.Thread` в **JDK** є платформеним потоком.
  Такий потік запускає **Java** код, що лежить в основі ОС, і захоплює потік ОС протягом усього життя коду.
* Кількість потоків платформи обмежена кількістю потоків ОС.
* Дороговартісні потоки треба об'єднувати.

### Virtual threads
* [Вступ до Project Loom. Частина 1: Virtual Threads](https://dou.ua/forums/topic/38676/)

**Віртуальний потік (virtual thread)** - це екземпляр `java.lang.Thread` який запускає **Java** код, що лежить в основі ОС, 
але НЕ захоплює потік ОС протягом усього життя коду.
Це означає, що багато віртуальних потоків можуть запускати свій Java код на одному потоці ОС, ефективно розділяючи його.

* Платформений потік монополізує дорогоцінний потік ОС, віртуальний потокік цього не робить.
* Кількість віртуальних потоків може бути набагато більшою, аніж кількість потоків ОС.
* Віртуальні потоки не швидкі - вони не виконують код швидше, аніж платформені потоки.
  Вони існують для забезпечення масштабування (більшої пропускної здатності), а не швидкості (меншої затримки).
* Віртуальні потоки допомагають поліпшити пропускну здатність типових серверних додатків саме тому, 
  що такі програми складаються з великої кількості одночасних завдань, які витрачають більшу частину свого часу на очікування.


## Способи створення потоків Thread

### 1 вариант создания потока:

Класс должен наследовать `Thread` и переопределять метод `run`:

```java
MyThread1 myThread1 = new MyThread1();
MyThread2 myThread2 = new MyThread2();
myThread1.start();
myThread2.start();

// створення:
class MyThread extends Thread { 
    public void run() { 
        /* some code */ 
    } 
}
    
// запуск:
new MyThread().start();
```


### 2 вариант создания потока:

Использование интерфейса `Runnable`, который содержит метод `run` (который нужно реализовать):

```java
// створення:
class MyRunnableImpliments Runnable {
    public void run() {
        /*some code*/
    }
}
    
// запуск:
new Thread(new MyRunnableImpl()).start();
```


### 3 вариант создания потока:

Можно сразу реализовать в конструкторе метод `run`:

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello1");
    }
}).start();
```


### 4 вариант создания потока:

Интерфейс `Runnable` содержит всего 1 метод, а значит это функциональный интерфейс, который можно реализовать с помощью
лямбда выражения:

```java
new Thread(() -> System.out.println("Hello2")).start();
```


## Приоритет потока

Каждый поток имеет дефолтное значение `5`, шкала от `1` до `10`. Где `10` наивысший приоритет!

Установить или узнать приоритет можно вызвав на потоке методы `setPriority()` и `getPriority()` соответственно.


### Thread states = Стани потока
Подивитися стан потоку можна викликав на ньому метод `getState()`.

Потоки існують у 6-ти станах (у класі `Thread` є **enum** `State`):
- **NEW** (Новий): коли потік був створений, але ще не було викликано метод `start()`, який запускає потік на виконання.
- **RUNNABLE** (Запущений): коли потік був запущений або він готовий до виконання, але може бути призупинений системою для виконання іншого потоку.
- **BLOCKED** (Заблокований): роботу потоку можна заблокувати, коли він чекає на ресурс (очікує на отримання монітора для входу у роботу).
- **WAITING** (Чекає): Потік у цьому стані чекає на спеціальний сигнал від іншого потоку про завершення певної операції.
- **TIMED_WAITING** (Чекає з таймаутом): Потік у цьому стані чекає на спеціальний сигнал від іншого потоку з певним таймаутом.
- **TERMINATED** (Закінчений): Потік завершив своє виконання і більше не буде виконуватися.

### Определения
- **Concurrency** - совпадение или согласованность. Выполнение сразу нескольких задач, но не обязательно в одно и то же время.
- **Параллелизм** - выполнение нескольких задач в одно и то же время, т.е. параллельно.
- **Синхронность** - в синхронном программировании задачи выполняются последовательно друг за другом.
- **Асинхронность** - асинхронное программирование (не детерминированное поведение) позволяет достичь concurrency - позволяет выполнять сразу несколько задач в одно время.



## Volatile
Ключевое слово `volatile` используется для пометки переменной, как хранящейся только в основной памяти «main memory».

>__*Для синхронизации значения переменной между потоками ключевое слово `volatile` используется тогда, 
когда только один поток может изменять значение этой переменной, а остальные потоки могут его только читать.*__

* значення `volatile` змінної завжди буде зчитуватися з основної пам'яті, а не з кеш-пам'яті потока;
* `volatile` змінні працюють повільніше за звичайні змінні.


### Data race
Data race – это проблема, которая может возникнуть когда два и более потока обращаются к одной и той же переменной, и как минимум 1 поток её изменяет.



## Synchronized методы
В один и тот же момент времени есть доступ только у одного потока.
```java
public static synchronized void increment(){
    counter++;
}
```

### Монитор
Монитор – это сущность/механизм, благодаря которому достигается корректная работа при синхронизации.

> В Java у каждого класса и объекта есть привязанный к нему монитор. 
> Может иметь статус свободен или занят.

Монитор объекта или класса принимает состояние занято, если метод помечен `synchronized`.

### Synchronized блоки
В принципе работают так же, как и `synchronized` методы, только синхронизирует не весь код в методе, а лишь его часть.

В скобках указывается объект, чей монитор для синхронизации будет использоваться. По умолчанию у методов это `this`.

> Когда мы используем `synchronized` блок, в скобках нужно указать монитор какого объекта будет использоваться для синхронизации.

> Когда мы работаем с `synchronized` методами, если метод не статичный, то всегда идёт синхронизация на объекте `this`. 
То же самое что и `synchronized` блок, где явно указывается используемый монитор конкретного объекта.

> Невозможно синхронизировать конструкторы! 
> JVM гарантирует, что конструктор может обрабатываться только одним потоком!

На статическом методе или классе используется монитор всего класса.
```java
public class SynchronizedBlock {
    volatile static int counter = 0;

    public static void increment() {
        synchronized (SynhcronizedBlock.class) {
            counter++;
        }
    }
}
```

Шаблон синхронізації пари методів за одним об'єктом.
```java
static final Object lock = new Object();
public void abc() { // method body
  synchronized(lock) { block body } // method body 
}
```
<details>
<summary>Приклад сінхронізації монітору одного і тогож об'єкту</summary>

```java
// основна логіка
public class SynchronizedBlocksExample {

  // конкретний об'єкт на якому буде концентруватися синхронізація
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
```
```java
// три класи реалізатори Runnable
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
```
</details>

### Методы wait и notify
Для извещения потоком других потоков о своих действиях часто используются следующие методы:

1. `wait` - освобождает монитор и переводит вызывающий поток в состояние ожидания до тех пор, пока другой поток не вызовет метод `notify()`;
  Может принимать параметры в мс.
2. `notify` – НЕ освобождает монитор и будит поток, у которого ранее был вызван метод `wait()`;
3. `notifyAll` – НЕ освобождает монитор и будит все потоки, у которых ранее был вызван метод `wait()`;

### Ситуации в многопоточности
- __Deadlock__ – ситуация, когда 2 или более потоков залочены навсегда, ожидают друг друга и ничего не делают.
- __Livelock__ – ситуация, когда 2 или более потоков залочены навсегда, ожидают друг друга, проделывают какую-то работу, но без какого-либо прогресса.
- __Lock starvation__ – ситуация, когда менее приоритетные потоки ждут долгое время или всё время для того, чтобы могли запуститься.

### Lock и ReentrantLock
Основна відмінність між `synchronized` і `ReentrantLock` полягає в тому, що `synchronized` - це ключове слово, яке автоматично надає синхронізацію методам та блокам коду.

`ReentrantLock` є класом з пакету `java.util.concurrent.locks` та надає більш гнучкий інтерфейс для управління блокуванням.
Для того, щоб його використовувати, потрібно створити новий об'єкт ReentrantLock. Блокування виконується методом `lock()`, а розблокування - `unlock()`.

Також `ReentrantLock` надає можливість встановлювати рівень справедливості блокування (**Fair**, **Non-Fair**) та можливість встановлювати таймаути блокування.

Але водночас, використання `ReentrantLock` може вимагати більшої кількості коду та бути менш ефективним за часом виконання, ніж `synchronized`.

* Метод `lock()` - потік чекатиме поки звільниться місце.
* Метод `tryLock()` - потік подивиться, якщо вільно працюватиме, а якщо ні – займеться іншими справами.
* Метод `unlock()` - потік завершив роботу та звільнив місце.

Плюс `synchronized` конструкції в тому, що нам не потрібно дбати про закриття lock, тут же потрібно використовувати
методи `lock()` і `unlock()`:
```java
// створюємо потоки
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public static void main(String[] args) {
        Call call = new Call();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                call.mobileCall();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                call.skypeCall();
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                call.whatsappCall();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```
```java
// синхронізація на основі Lock
class Call {
    private Lock lock = new ReentrantLock();

    void mobileCall() {
        lock.lock(); // працює як synchronized
        try {
            System.out.println("Mobile call starts");
            Thread.sleep(3000);
            System.out.println("Mobile call ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void skypeCall() {
        lock.lock(); // працює як synchronized
        try {
            System.out.println("Skype call starts");
            Thread.sleep(5000);
            System.out.println("Skype call ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void whatsappCall() {
        lock.lock(); // працює як synchronized
        try {
            System.out.println("Whatsapp call starts");
            Thread.sleep(7000);
            System.out.println("Whatsapp call ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

### Daemon потоки
- Daemon потоки предназначены для выполнения фоновых задач и оказания различных сервисов User потокам.
- При завершении работы последнего User потока, программа завершает своё выполнение, не дожидаясь окончания работы Daemon потоков.
- Чтобы установить Daemon поток, необходимо на потоке вызвать метод `setDaemon` с параметром `true`. __Это нужно сделать перед стартом потока!__
- Проверить является ли потока демоном, можно методом `isDaemon()`.

### Прерывание потоков
- Используй метод `interrupt()`. Метод `stop()` грубо обрывает поток с незаконченными процессами.
- `isInterrupted()` проверка на прерывание потока.



## Thread pool
При создании потока под капотом происходит множество операций в JVM и ОС, и управлять отдельно созданными потоками неудобно...
На помощь приходит механизм Thread pool. 

__Thread pool__ – это множество потоков, каждый из которых предназначен для выполнения той или иной задачи.

В Java с thread pool-ами нужно работать через объект `Execute`. Удобнее всего работать посредством интерфейса `ExecutorService`.


### ExecutorService
Напрямую, Thread pool, практически никогда не создают:
```java
ExecutorService es = new ThreadPoolExecutor();
```
Thread pool удобнее всего создавать используя один из фактори-методов класса `Executors`:
```java
ExecutorService es1 = Executors.newFixedThreadPool(5);    // создаст pool с 5-ю потоками;
ExecutorService es2 = Executors.newSingleThreadExecutor() // создаст pool с одним потоком.
```
Простой пример:
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolEx1 {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            es.execute(new RunnableImpl00());
        }
        es.shutdown();
        System.out.println("Main ends");
    }
}

class RunnableImpl00 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```


#### *execute()*
Запуск Thread pool'a происходит через метод `execute`, в который передается объект имплементируемый `Runnable`:
```java
executorService.execute(new RunnableImpl100());
```


#### *shutdown()*
После выполнения метода `shutdown`, `ExecutorService` понимает, что новых заданий больше не будет и, выполнив поступившие до этого задания, прекращает работу.


#### *awaitTermination()*
Метод `awaitTermination` принуждает поток в котором он вызвался подождать до тех пор, пока не выполнится одно из двух событий: 
либо `ExecutorService` прекратит свою работу, либо пройдёт время, указанное в параметре метода `awaitTermination`. Работает подобно методу `join`.
__Метод `awaitTermination` вызывается всегда после `shutdown`.__


#### Приклади

> <details>
> <summary>Приклад роботи ExecutorService з Runnable через метод execute</summary>
>
> Мінус `Runnable` у тому, що не можна викинути **exception** та метод `run()` нічого не повертає.
> ```java
> // код вираховує факторіал числа
> public class FactorialByRunnable {
> 
>   protected static int factorialResult;
> 
>   public static void main(String[] args) throws InterruptedException {
>     ExecutorService executorService = Executors.newSingleThreadExecutor();
>     FactorialByRunnableImpl factorial = new FactorialByRunnableImpl(5);
>     executorService.execute(factorial);
>     executorService.shutdown();
>     executorService.awaitTermination(10, TimeUnit.SECONDS);
>     System.out.println(factorialResult);
>   }
> }
> 
> class FactorialByRunnableImpl implements Runnable {
>   private final int f;
> 
>   public FactorialByRunnableImpl(int f) {
>     this.f = f;
>   }
> 
>   @Override
>   public void run() {
>     if (f <= 0) {
>       System.out.println("You enter wrong number");
>       return;
>     }
>     int result = 1;
>     for (int i = 1; i <= f; i++) {
>       result *= i;
>     }
>     FactorialByRunnable.factorialResult = result;
>   }
> }
> ```
> </details>


> <details>
> <summary>Приклад роботи ExecutorService з Callable через метод submit</summary>
> 
> ```java
> public class FactorialByCallable {
> 
>   protected static int factorialResult;
> 
>   public static void main(String[] args) {
>     ExecutorService executorService = Executors.newSingleThreadExecutor();
>     FactorialByCallableImpl factorial = new FactorialByCallableImpl(-1);
>     Future<Integer> future = executorService.submit(factorial);
>     try {
>       factorialResult = future.get();
>     } catch (InterruptedException e) {
>       throw new RuntimeException(e);
>     } catch (ExecutionException e) {
>       System.out.println(e.getCause());
>     } finally {
>       executorService.shutdown();
>     }
>     System.out.println(factorialResult);
>   }
> }
> 
> class FactorialByCallableImpl implements Callable<Integer> {
>   private final int f;
> 
>   public FactorialByCallableImpl(int f) {
>     this.f = f;
>   }
> 
>   @Override
>   public Integer call() throws Exception {
>     if (f <= 0) {
>       throw new Exception("You enter wrong number");
>     }
>     int result = 1;
>     for (int i = 1; i <= f; i++) {
>       result *= i;
>     }
>     return result;
>   }
> }
> ```
> </details>


## ScheduledExecutorService
`ScheduledExecutorService` мы используем тогда, когда хотим установить расписание на запуск потоков из пула.

> - __schedule__ - одноразовое выполнение через 3 сек. `scheduledExecutorService.schedule(new RunnableImpl200(), 3, TimeUnit.SECONDS);`
> - __scheduleAtFixedRate__ - выполнение с периодичностью в 1 сек. `scheduledExecutorService.scheduleAtFixedRate(new RunnableImpl200(), 3, 1, TimeUnit.SECONDS);`
> - __scheduleWithFixedDelay__ - Между выполнением заданий будет проходить секунда:
`scheduledExecutorService.scheduleWithFixedDelay(new RunnableImpl200(), 3, 1, TimeUnit.SECONDS);`
> - __Executors.newCachedThreadPool()__ - Кешированный Thread pool будет создавать в себе потоки по надобности
`ExecutorService executorService = Executors.newCachedThreadPool();`

### Callable
Інтерфейс `Callable`, як і інтерфейс `Runnable`, є певним завданням, яке виконується потоком.
На відміну від класу `Runnable`, `Callable`:
- має **return type** (ні `void`);
- може **викидати** `Exception`.

> `Runnable` можна використовувати як з `ExecuteService` та і з окремим створенням `Thread`, 
> `Callable` можна використовувати тільки з `ExecuteService`.
> 
> `Callable` треба використовувати коли потрібно повертати результат, інакше викорустовуй `Runnable`. 

> **Як працювати з Callable:**
> - `submit`: у `ExecutorService`, щоб запустити завдання (task), замість метода `execute` для `Callable` потрібно використовувати метод `submit`.
Цей метод передає наше завдання у thread pool, для виконання його одним із потоків, і повертає об'єкт типу `Future`, 
у якому зберігається результат виконання нашого завдання.
>   - `get`: метод позволяет получить результат выполнения нашего задания из объекта `Future`.
>   - `Future.isDone()`: перевіряє чи завершене завдання у об'єкта `Future`.



## Синхронізатори 
Це високорівневі рішення (механізми) які використовують під капотом низько рівневі механізми (`Lock`, `synchronized`...).

***

### Синхронізатор Semaphore
`Semaphore` – це синхронізатор, що дозволяє обмежити доступ до якогось ресурсу.
У конструктор Semaphore потрібно передавати кількість потоків, яким Semaphore дозволятиме одночасно використовувати цей ресурс.

* `acquire()` - спроба отримати дозвіл від **семафора**. Даний метод заблокує потік, доки ресурс не буде доступним для нас.
* `release()` - звільняємо дозвіл **семафора**, тим самим звільшується _counter_ у семафора на 1.

> <details>
> <summary>ПРИКЛАД Semaphore</summary>
> 
> ```java
> // 5 людей телефонують по 2 телефонам
> public class SemaphoreExample {
>   public static void main(String[] args) {
>     Semaphore callBox = new Semaphore(2);
> 
>     new Person("Bob", callBox);
>     new Person("Carl", callBox);
>     new Person("Mina", callBox);
>     new Person("Kate", callBox);
>     new Person("Ted", callBox);
>   }
> }
> 
> class Person extends Thread {
>   private final String name;
>   private final Semaphore callBox;
> 
>   Person(String name, Semaphore callBox) {
>     this.name = name;
>     this.callBox = callBox;
>     this.start();
>   }
> 
>   @Override
>   public void run() {
>     try {
>       System.out.println(name + " - 1 - WAIT");
>       callBox.acquire();
>       System.out.println(name + " - 2 - USE");
>       sleep(2000);
>       System.out.println(name + " - 3 - END");
>     } catch (InterruptedException e) {
>       e.printStackTrace();
>     } finally {
>       callBox.release();
>     }
>   }
> }
> ```
> </details>

***

### Синхронізатор CountDownLatch (замок із зворотним відліком)
**CountDownLatch** – це синхронізатор, що дозволяє будь-якій кількості потоків чекати доки не завершиться певна кількість операцій.
У конструктор `CountDownLatch` потрібно передавати кількість операцій, які мають завершитись, щоб потоки продовжили свою роботу.

* `await()` - якщо лічильник більше `> 0`, потік буде заблокований доти, поки лічильник не дорівнюватиме `0`.

> <details>
> <summary>ПРИКЛАД Semaphore</summary>
>
> ```java
> public class CountDownLatchExample {
>   static CountDownLatch countDownLatch = new CountDownLatch(3);
> 
>   private static void marketStaffIsOnPlace() throws InterruptedException {
>     Thread.sleep(2000);
>     System.out.println("Market staff came to work");
>     countDownLatch.countDown();
>     System.out.println("countDownLatch = " + countDownLatch);
>   }
> 
>   private static void everythingIsReady() throws InterruptedException {
>     Thread.sleep(3000);
>     System.out.println("Everything is ready, so let's open market");
>     countDownLatch.countDown();
>     System.out.println("countDownLatch = " + countDownLatch);
>   }
> 
>   private static void openMarket() throws InterruptedException {
>     Thread.sleep(4000);
>     System.out.println("Market is opened");
>     countDownLatch.countDown();
>     System.out.println("countDownLatch = " + countDownLatch);
>   }
> 
>   public static void main(String[] args) throws InterruptedException {
>     new Friend("Bob", countDownLatch);
>     new Friend("Carl", countDownLatch);
>     new Friend("Ted", countDownLatch);
>     new Friend("Mina", countDownLatch);
>     new Friend("Helga", countDownLatch);
> 
>     marketStaffIsOnPlace();
>     everythingIsReady();
>     openMarket();
>   }
> }
> 
> class Friend extends Thread {
>   private final String name;
>   private final CountDownLatch countDownLatch;
> 
>   public Friend(String name, CountDownLatch countDownLatch) {
>     this.name = name;
>     this.countDownLatch = countDownLatch;
>     this.start();
>   }
> 
>   @Override
>   public void run() {
>     try {
>       countDownLatch.await();
>       System.out.println(name + " shopping");
>     } catch (InterruptedException e) {
>       e.printStackTrace();
>     }
>   }
> }
> ```
> </details>

***

### Синхронізатор Exchanger
**Exchanger** – це синхронізатор, що дозволяє обмінюватися даними між двома потоками, забезпечує те, що обидва потоки отримають інформацію один від одного одночасно.



## AtomicInteger
**AtomicInteger** – это класс, который предоставляет возможность работать с целочисленным значением `int`, используя атомарные операции.

***

## Synchronized collections
> По суті, це синхронізована обертка для ново створенної колекції.
> 
> Concurrent колекції працюють швидше за синхрозінозані колекції.

**Synchronized collections** досягають потокобезпеки завдяки тому, що використовують лок через synchronized блоки для всіх методів.

`synchronizedList()` - синхронізована обгортка для нового листа, приклад:
```java
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
```

***

### ConcurrentHashMap
`ConcurrentHashMap` - це багатопотокова версія **мапи**, яка імплементує інтерфейс `ConcurrentMap`, який своєю чергою походить від інтерфейсу `Map`.
- використовує **fail-save** ітератор який не викидає `ConcurrentModificationException`.
- будь-яка кількість потоків може читати елементи, не блокуючи його;
- завдяки його сегментуванню (діленню на частини), при зміні якогось елемента блокується тільки **bucket**, в якому він знаходиться;
- під капот сігменти використовують `ReentrantLock`;
- ані **key**, ані **value** не можуть бути `null`.

***

### CopyOnWriteArrayList
**CopyOnWriteArrayList** имплементирует интерфейс List. Следует использовать тогда, когда вам нужно добиться потокобезопасности, 
у вас небольшое количество операций по изменению элементов и большое количество по их чтению.

Що разу при видалені чи запису елементів створюється копія колекції в потоці.
В такому випадку колекція fail-save!


***

### ArrayBlockingQueue
**ArrayBlockingQueue** – потокобезпечна черга з обмеженим розміром елементів (capacity restricted).
```java
ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
```
* Максимальна кількість елементів, які вона може містити, задається при створенні.
* Всі операції додавання та видалення елементів будуть блокуючими, якщо черга досягне свого максимального розміру.

Зазвичай один або кілька потоків додають елементи у кінець черги, а інший або інші потоки забирають елементи з початку черги.

- `add` - викине помилку `Queue full`, якщо черга повна.
- `offer` - НЕ викине помилку якщо черга повна, і нічого не додасть у чергу.




## [Running Delayed or Periodic Tasks](https://www.baeldung.com/java-start-thread)
Java has few tools that can help us to run delayed or recurring operations:
- `java.util.Timer`
- `java.util.concurrent.ScheduledThreadPoolExecutor`

### Which Tool Is Better?
**Timer:**

* does not offer real-time guarantees: it schedules tasks using the Object.wait(long) method
* there's a single background thread, so tasks run sequentially and a long-running task can delay others
* runtime exceptions thrown in a TimerTask would kill the only thread available, thus killing Timer

**ScheduledThreadPoolExecutor:**
* can be configured with any number of threads
* can take advantage of all available CPU cores
* catches runtime exceptions and lets us handle them if we want to (by overriding afterExecute method from ThreadPoolExecutor)
* cancels the task that threw the exception, while letting others continue to run
* relies on the OS scheduling system to keep track of time zones, delays, solar time, etc.
* provides collaborative API if we need coordination between multiple tasks, like waiting for the completion of all tasks submitted
* provides better API for management of the thread life cycle

