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
> В перегруженный метод `join()` можно передать время ожидания потока, по прошествии которого, если ожидаемый поток не пришел, тогда главный поток продолжит свою работу.



## Способы создания потоков


### 1 вариант создания потока:

Класс должен наследовать `Thread` и переопределять метод `run`:

```java
MyThread1 myThread1 = new MyThread1();
MyThread2 myThread2 = new MyThread2();
myThread1.start();
myThread2.start();

// Создание:
class MyThread extends Thread { 
    public void run() { 
        /*some code*/ 
    } 
}
    
// Запуск:
new MyThread().start();
```


### 2 вариант создания потока:

Использование интерфейса `Runnable`, который содержит метод `run` (который нужно реализовать):

```java
// Создание:
class MyRunnableImpliments Runnable {
    public void run() {
        /*some code*/
    }
}
    
// Запуск:
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


### Thread states = Состояния потока
Посмотреть состояние потока можно вызвав на нем метод `getState()`.

У потока может быть 3 состояния:

- `NEW` = когда поток создан, но ещё не запущен методом `start()`;
- `RUNNABLE` = когда поток уже запущен методом `start()`; Делится ещё на 2 состояния:
    - Ready = поток ожидает запуска операционной системой;
    - Running = поток выполняет работу. Сменяется с Ready.
- `TERMINATED` = поток закончил работу.


### Определения
- **Concurrency** - совпадение или согласованность. Выполнение сразу нескольких задач, но не обязательно в одно и то же время.
- **Параллелизм** - выполнение нескольких задач в одно и то же время, т.е. параллельно.
- **Синхронность** - в синхронном программировании задачи выполняются последовательно друг за другом.
- **Асинхронность** - асинхронное программирование (не детерминированное поведение) позволяет достичь concurrency - позволяет выполнять сразу несколько задач в одно время.



## Volatile
Ключевое слово `volatile` используется для пометки переменной, как хранящейся только в основной памяти «main memory».

>__*Для синхронизации значения переменной между потоками ключевое слово `volatile` используется тогда, 
когда только один поток может изменять значение этой переменной, а остальные потоки могут его только читать.*__

* `volatile` переменные не хранятся в кеше, они хранятся в памяти.
* `volatile` переменные работают медленнее за обычные переменные, но правильнее.


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

Пример синхронизации пары методов. 
Нужно использовать 1 объект для синхронизации нужных методов.
```java
static final Object lock = new Object();

void mobileCall(){
    synchronized (lock) {
        System.out.println("Mobile call starts");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mobile call ends");
    }
}

void skypeCall(){
    synchronized (lock) {
        System.out.println("Skype call starts");
        try { 
            ...
```

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
`ReentrantLock` - классом, который имплементирует `Lock` интерфейс.
Цель - чтобы только один поток работал в один момент времени, а остальные потоки ждали.

Так же как и ключевое слова `synchronized`, **Lock** нужен для достижения синхронизации между потоками.

Метод `lock()` - поток будет ждать пока освободится место.
Метод `tryLock()` - поток посмотрит, если свободно будет работать, а если нет - займется другими делами.
Метод `unlock()` - поток завершил работу и освободил место. 

Плюс synchronized конструкции в том, что нам не нужно заботиться о закрытии lock, здесь же нужно использовать
методы `lock()` и `unlock()`:
```java
// создаём потоки
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
// синхронизация на основе Lock
class Call {
    private Lock lock = new ReentrantLock();

    void mobileCall() {
        lock.lock(); // работает как synchronized
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
        lock.lock(); // работает как synchronized
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
        lock.lock(); // работает как synchronized
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
- Проверить является ли потока демоном, можно методом isDaemon().

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



## ScheduledExecutorService
`ScheduledExecutorService` мы используем тогда, когда хотим установить расписание на запуск потоков из пула.

> - __schedule__ - одноразовое выполнение через 3 сек. `scheduledExecutorService.schedule(new RunnableImpl200(), 3, TimeUnit.SECONDS);`
> - __scheduleAtFixedRate__ - выполнение с периодичностью в 1 сек. `scheduledExecutorService.scheduleAtFixedRate(new RunnableImpl200(), 3, 1, TimeUnit.SECONDS);`
> - __scheduleWithFixedDelay__ - Между выполнением заданий будет проходить секунда:
`scheduledExecutorService.scheduleWithFixedDelay(new RunnableImpl200(), 3, 1, TimeUnit.SECONDS);`
> - __Executors.newCachedThreadPool()__ - Кешированный Thread pool будет создавать в себе потоки по надобности
`ExecutorService executorService = Executors.newCachedThreadPool();`

### Callable
Интерфейс `Callable`, так же как и интерфейс `Runnable`, представляет собой определённое задание, которое выполняется потоком.
В отличие от класса `RunnableCallable`:
- имеет **return type** не `void`;
- может **выбрасывать** `Exception`.

> - `submit` - В `ExecutorService`, чтобы запустить таску, вместо метода `execute` для `Callable` нужно запустить `submit`.
Метод `submit` передаёт наше задание (task)в thread pool, для выполнения его одним из потоков, и возвращает тип `Future`, в котором и хранится результат выполнения нашего задания.
> - `get` - Метод `get` позволяет получить результат выполнения нашего задания из объекта `Future`.
> - `Future.isDone()` - Проверяет завершена ли таска у объекта `Future`.

### Semaphore
`Semaphore` – это синхронизатор, позволяющий ограничить доступ к какому-то ресурсу. В конструктор Semaphore нужно передавать количество потоков, которым Semaphore будет разрешать одновременно использовать этот ресурс.
Часто используется в программировании.

`acquire()` - Попытка получить разрешение от Semaphore. Данный метод заблокирует поток, пока ресурс не будет доступен для нас.

## CountDownLatch (замок с обратным отсчетом)
**CountDownLatch** – это синхронизатор, позволяющий любому количеству потоков ждать пока не завершится определённое количество операций. В конструктор CountDownLatchнужно передавать количество операций, которые должны завершится, чтобы потоки продолжили свою работу.

`await()` - Если счетчик больше 0, поток будет заблокирован до тех пор, пока счетчик не будет равен 0.

### Exchanger
**Exchanger** – это синхронизатор, позволяющий обмениваться данными между двумя потоками, обеспечивает то, что оба потока получат информацию друг от друга одновременно.

### AtomicInteger
**AtomicInteger** – это класс, который предоставляет возможность работать с целочисленным значением int, используя атомарные операции.

### Synchronized collections
**Synchronized collections** достигают потокобезопасности благодаря тому что, используют лок через synchronized блоки для всех методов.

`synchronizedList()` - Синхронизированная обертка для нового листа, пример:
```java
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
```

### ConcurrentHashMap
**ConcurrentHashMap** имплементирует интерфейс ConcurrentMap, который в свою очередь происходит от интерфейса Map.

- В ConcurrentHashMap ни key, ни value не могут быть null.
- В ConcurrentHashMap, благодаря его сегментированию, при изменении какого-либо элемента блокируется только bucket, в котором он находится.

### CopyOnWriteArrayList
**CopyOnWriteArrayList** имплементирует интерфейс List. Следует использовать тогда, когда вам нужно добиться потокобезопасности, у вас небольшое количество операций по изменению элементов и большое количество по их чтению.

### ArrayBlockingQueue
**ArrayBlockingQueue** – потокобезопасная очередь с ограниченным размером (capacity restricted).
```java
ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
```
Обычно один или несколько потоков добавляют элементы в конец очереди, а другой или другие потоки забирают элементы из начала очереди.

- `add` - Если очередь полная выбросит ошибку.
- `offer` - Если очередь полная НЕ выбросит ошибку, и в очередь ничего не добавит.


##[Running Delayed or Periodic Tasks](https://www.baeldung.com/java-start-thread)
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

