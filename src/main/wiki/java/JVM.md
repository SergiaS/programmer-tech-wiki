# Java Virtual Machine (JVM)
* [Java Memory Leaks | How to Avoid? | Prevention Techniques](https://seagence.com/blog/java-memory-leaks-how-to-avoid-prevention-techniques/)

> String Pool є частиною Купи (heap).

## Як працює Java
При запуске команды `javac`, приложение Java компилируется из Java-кода в байткод JVM, который является бинарным представлением приложения.
Но обычный процессор компьютера не может просто так выполнить байткод JVM.
Для работы вашей программы JVM нужен ещё один компилятор уже для этого байткода, который преобразуется в машинный код - процессор уже в состоянии использовать.


## [Stack та Heap в Java + приклад | TOPJAVA](https://topjava.ru/blog/stack-and-heap-in-java)

| Властивості                     | Stack (Стек)                                                                                             | Heap (Купа, Хип)                                      |
|:-------                         |:--------                                                                                                 |:--------                                              |
| Использование приложением       | Для каждого потока используется свой стек                                                                | Пространство кучи является общим для всего приложения |
| Размер                          | Предел размера стека определен операционной системой                                                     | Размер кучи не ограничен |
| Хранение                        | Хранит примитивы и ссылки на объекты                                                                     | Все созданные объекты хранятся в куче |
| Порядок                         | Работает по схеме последним вошел, первым вышел (LIFO)                                                   | Доступ к этой памяти осуществляется с помощью сложных методов управления памятью, включая Young, Old и Permanent Generation |
| Существование                   | Память стека существует пока выполняется текущий метод                                                   | Пространство кучи существует пока работает приложение |
| Скорость                        | Обращение к памяти стека происходит значительно быстрее, чем к памяти кучи                               | Медленнее, чем стек |
| Выделение и освобождение памяти | Эта память автоматически выделяется и освобождается, когда метод вызывается и завершается соответственно | Память в куче выделяется, когда создается новый объект и освобождается сборщиком мусора, когда в приложении не остается ни одной ссылки на его |


### Стек (Stack)
* Стек работает по схеме **LIFO** (последним вошел, первым вышел).
* Стек хранит значения примитивных переменных, создаваемых в методах, а также ссылки на объекты в куче на которые ссылается метод.
* Стек заполняется и освобождается по мере вызова и завершения новых методов.
* Переменные в стеке существуют до тех пор, пока выполняется метод в котором они были созданы.
* Если память стека будет заполнена, Java бросит исключение `java.lang.StackOverFlowError`.
* Доступ к этой области памяти осуществляется быстрее, чем к куче.
* Является потокобезопасным, поскольку для каждого потока создается свой отдельный стек.

### Хип (Heap)
* Хип используется для динамического выделения памяти - для объектов и классов **JRE** во время выполнения. 
* Новые объекты всегда создаются в куче, а ссылки на них хранятся в стеке.
* Эти объекты имеют глобальный доступ и могут быть получены из любого места программы.
* Эта область памяти разбита на несколько более мелких частей, называемых поколениями:
  * **Young Generation** — область где размещаются недавно созданные объекты. Когда она заполняется, происходит быстрая сборка мусора.
  * **Old (Tenured) Generation** — здесь хранятся долгоживущие объекты. Когда объекты из **Young Generation** достигают определенного порога «возраста», они перемещаются в **Old Generation**.
  * **Permanent Generation** — эта область содержит метаинформацию о классах и методах приложения, но начиная с Java 8 данная область памяти была упразднена.
* Когда эта область памяти полностью заполняется, Java бросает `java.lang.OutOfMemoryError`.
* Доступ к ней медленнее, чем к стеку.
* Эта память, в отличие от стека, автоматически не освобождается. Для сбора неиспользуемых объектов используется сборщик мусора.
* В отличие от стека, куча не является потокобезопасной и ее необходимо контролировать, правильно синхронизируя код.

### Пример разбора
```java
class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class PersonBuilder {
    private static Person buildPerson(int id, String name) {
        return new Person(id, name);
    }

    public static void main(String[] args) {
        int id = 23;
        String name = "John";
        Person person = null;
        person = buildPerson(id, name);
    }
}
```
Рассмотрим выполнение кода по шагам:
1. До начала выполнения метода `main()`, в стеке будет выделено пространство для хранения примитивов и ссылок этого метода:
   * примитивное значение `id` типа `int` будет храниться непосредственно в стеке;
   * ссылочная переменная name типа String будет создана в стеке, но сама строка "John" будет храниться в области, называемой String Pool (является частью Кучи);
   * ссылочная переменная `person` типа `Person` будет также создана в памяти стека, но будет указывать на объект, расположенный в куче;

2. Для вызова конструктора с параметрами `Person (int, String)` из метода `main()` в стеке, поверх предыдущего вызова метода `main()`, будет выделен блок памяти, который будет хранить:
   * `this` — ссылка на текущий объект;
   * примитивное значение `id`;
   * ссылочную переменную `name` типа `String`, которая указывает на объект строки из пула строк;

3. В методе `main` дополнительно вызывается метод `buildPerson` для которого будет выделен блок памяти в стеке поверх предыдущего вызова. 
Этот блок снова сохранит переменные способом, описанным выше.

4. Для вновь созданного объекта `person` типа `Person` все переменные будут сохранены в памяти кучи.

![img](https://raw.githubusercontent.com/SergiaS/programmer-tech-wiki/master/src/main/resources/img/java-heap-stack-diag.jpg)


## [Java Memory Management With example](https://sathyaventhan.medium.com/java-memory-management-with-example-464f30d22d3e)

### Разбор примера:
```java
public class JavaStackMemory {
    public static void main(String[] args) {        // Line 1
        int i = 1;                                  // Line 2                                      
        Object obj = new Object();                  // Line 3
        JavaStackMemory mem = new JavaStackMemory();// Line 4
        mem.foo(obj);                               // Line 5
    }                                               // Line 9
    
    private void foo(Object param) {                // Line 6
        String str = param.toString();              // Line 7
        System.out.println(str);
    }                                               // Line 8
}
```
Как только мы запускаем программу, происходит загрузка всех классов runtime в хип память.

Когда **на строке 1** найден метод `main()`, Java runtime создает стек память, которая будет использоваться потоком метода `main()`.

**На строке 2** мы создаем примитивную локальную переменную, она создается и хранится в стековой памяти метода `main()`.

Поскольку мы создаем объект **на строке 3**, он создается в хипе, а ссылка на него помещается в стек.
То же самое происходит **в строке 4**.

Теперь **на строке 5** когда мы вызываем метод `foo()`, создается блок в верхней части стека, который будет использоваться методом `foo()`.
Поскольку в Java используется передача по значению, новая ссылка на объект создается в блоке стека `foo()` **в 6-й строке**.

Создается String **на 7-й строке** - помещается в пул строк в области хипа, и создается ссылка в стека метода `foo()`.

**На 8 строке** работа метода `foo()` завершается, в это время освобождается блок памяти, выделенный для `foo()` в стеке.

**В строке 9** метод `main()` завершается, и память стека, созданная для метода `main()`, уничтожается. 
Кроме того, программа заканчивается на этой строке, поэтому Java Runtime освобождает всю память и завершает выполнение программы.


### Разница памяти хипа от стека (Heap VS Stack Memory)
Основываясь на примере выше можно легко подметить разницу между хип и стек памятью:

1. Хип память используется всеми частями приложения (1 хип на 1 приложение), тогда как память стека используется только одним потоком выполнения (1 стек для каждого потока).

2. Всякий раз, когда создается объект, он всегда сохраняется в хипе, а в стек помещается ссылка на него. 
Память стека содержит только локальные примитивные переменные и переменные-ссылки на объекты - в куче.

3. Объекты, хранящиеся в куче, доступны глобально, тогда как другие потоки не могут получить доступ к памяти стека.

4. Управление памятью в стеке осуществляется по принципу LIFO, тогда как в памяти Heap оно более сложное, поскольку оно используется глобально. 
Память кучи делится на Young-Generation, Old-Generation и т. д. 

5. Память стека недолговечна, тогда как память кучи живет от старта и до конца выполнения приложения.

6. Мы можем использовать опции `-Xms` и `-Xmx` JVM, чтобы определить стартовый размер и максимальный размер памяти хипа. 
Мы можем использовать `-Xss` для определения размера памяти стека.

7. Когда память стека заполнена, Java выдает ошибку `java.lang.StackOverFlowError`, а если память кучи заполнена - `java.lang.OutOfMemoryError: Java Heap Space`.

8. Размер памяти стека намного меньше памяти кучи. 
Из-за простоты распределения памяти (LIFO) стековая память работает очень быстро по сравнению с памятью кучи. 


## [Управление памятью Java](https://habr.com/ru/post/549176/)
Максимальные размеры стека и кучи не определены заранее - это зависит от работающей JVM машины.

### Стек (Stack)
Стековая память отвечает за хранение ссылок на объекты кучи и за хранение типов значений (также известных в Java как примитивные типы),
которые содержат само значение, а не ссылку на объект из кучи.

Кроме того, переменные в стеке имеют определенную видимость, также называемую областью видимости.
Используются только объекты из активной области. Например, предполагая, что у нас нет никаких глобальных переменных (полей) области видимости,
а только локальные переменные, если компилятор выполняет тело метода, он может получить доступ только к объектам из стека, которые находятся внутри тела метода.
Он не может получить доступ к другим локальным переменным, так как они не выходят в область видимости.
Когда метод завершается и возвращается, верхняя часть стека выталкивается, и активная область видимости изменяется.

### Куча (Heap)
Эта часть памяти хранит в памяти фактические объекты, на которые ссылаются переменные из стека.

Сама куча разделена на несколько частей, что облегчает процесс сборки мусора.

### Советы и приемы
* Чтобы, минимизировать объем памяти, максимально ограничьте область видимости переменных. 
Помните, что каждый раз, когда выскакивает верхняя область видимости из стека, ссылки из этой области теряются, 
и это может сделать объекты пригодными для сбора мусора.
* Явно устанавливайте в `null` устаревшие ссылки. Это сделает объекты, на которые ссылаются, подходящими для сбора мусора.
* Избегайте финализаторов (`finalizer`). Они замедляют процесс и ничего не гарантируют. 
Фантомные ссылки предпочтительны для работы по очистке памяти.
* Не используйте сильные ссылки там, где можно применить слабые или мягкие ссылки. 
Наиболее распространенные ошибки памяти - это сценарии кэширования, когда данные хранятся в памяти, даже если они могут не понадобиться.
* **JVisualVM** также имеет функцию создания дампа кучи в определенный момент, чтобы вы могли анализировать для каждого класса, сколько памяти он занимает.
* Настройте JVM в соответствии с требованиями вашего приложения. Явно укажите размер кучи для JVM при запуске приложения. 
Процесс выделения памяти также является дорогостоящим, поэтому выделите разумный начальный и максимальный объем памяти для кучи. 
Если вы знаете его, то не имеет смысла начинать с небольшого начального размера кучи с самого начала, JVM расширит это пространство памяти.
Указание параметров памяти выполняется с помощью следующих параметров:
  * Начальный размер кучи `-Xms512m-` установите начальный размер кучи на 512 мегабайт.
  * Максимальный размер кучи `-Xmx1024m-` установите максимальный размер кучи 1024 мегабайта.
  * Размер стека потоков `-Xss1m-` установите размер стека потоков равным 1 мегабайту.
  * Размер поколения `-Xmn256m-` установите размер поколения 256 мегабайт.
* Если приложение Java выдает ошибку `OutOfMemoryError` и вам нужна дополнительная информация для обнаружения утечки, 
запустите процесс с `–XX:HeapDumpOnOutOfMemory` параметром, который создаст файл дампа кучи, когда эта ошибка произойдет в следующий раз.
* Используйте опцию `-verbose:gc`, чтобы получить вывод процесса сборки мусора. 
Каждый раз, когда происходит сборка мусора, будет генерироваться вывод.



## [How Java Virtual Machine(JVM) works](https://medium.com/@harindu973/how-java-virtual-machine-jvm-works-2403fdba4fca)
Languages such as `C` and `C++` is compiled into machine code unique to the operating system. 
As a result, these programming languages are referred to as __compiled languages__.

Languages such as `JavaScript` or `Python`, the code is executed immediately by the computer without being compiled. 
These are referred to as __interpreted languages__.

> Java is unique in that it employs a combination of compilation and interpretation.
> As a result, in Java, source code is first compiled to a `.class` file containing bytecode.
> The Interpreter or JIT compiler is then used to run this `.class` file.

JVM, on the other hand, does not come as a separate installation and cannot be downloaded and installed. JVM is always included with JDK or JRE.

_In Java architecture, there are three key components:_

* __<u>JDK (Java Development Kit).</u>__
  JDK gives the environment for developing and running Java programs. 
  It includes development tools for writing Java applications as well as JRE for running them.

* __<u>JRE (Java Runtime Environment).</u>__ 
  JRE provides the environment for executing a Java application, because it is only utilized by people who want to run Java programs.

* __<u>JVM (Java Virtual Machine).</u>__ 
  JVM is a Specification. It defines how JVM should function and how it should be implemented. 
  Anyone can use that specification to create their own JVM.

![img](https://miro.medium.com/max/2640/1*LuKOZMDCX8e1zDyGyMUu_w.png)


### Inside of the JVM Architecture
In the JVM architecture there are 3 main components named as:

1. __Class Loader.__ When we execute a Java program, the Class Loader loads the compiled class files.
   
   _JVM has three built-in Class Loaders:_
   * Bootstrap Class Loader
   * Extension Class Loader
   * System Class-Loader
   * You may construct a “User Defined Class Loader” in Java.
   
   _There are the three sub-components of Class Loader:_
   * Loading
   * Linking
   * Initialization
2. __Memory Area.__
   Only one method and one heap area are created per JVM.
   That is, regardless of whether your software has several threads, those threads must share these two.
   However, the other three regions are created in accordance with a thread.   
   _There are 5 sub-areas in Memory Area:_
   * __Method Area (Class Area)__ - 
       Every class’s class level data is stored here, such as the runtime constant pool, field and method data, and method code.
   * __Heap Area__ - 
       The heap area, which is created during virtual machine startup and from which memory is allocated for all class instances and arrays, represents the runtime data area.
   * __Stack__ - 
       In Java, stack memory is used for static memory allocation and thread execution. It’s made for each thread. 
       When threads start, the JVM creates a separate runtime stack in which method calls are stored. 
       For each method call, a frame will be produced and pushed into the runtime stack’s storage. 
       When a frame’s method invocation is finished, it is destroyed.
   * __Program Counter Register (PC Register)__ -
       Each thread will have its own PC Register, which will store the address of the currently executing instruction. 
       Once the instruction is completed, the PC register will be updated with the next instruction.
   * __Native Method Area__ -
       This will store information about native methods, which are written in a language other than Java, such as C or C++. 
       Every new thread will have its own native method stack, same like stack and PC register.
   
3. __Execution Engine.__
     The execution engine executes Java class files - reads the bytecode one by one and executes it. 
     It consists of three parts that work together to execute the byte code. 
   _They are as follows:_
   * <u>Interpreter.</u>
     Line by line, the interpreter reads the bytecode and translates it to machine code. 
     Although an interpreter can process a single line of byte code faster, it is slow when executing the complete code.
     Another problem is that when the same method is called numerous times, each time a fresh interpretation is required. 
     As a result, JIT Compiler is utilized to address the aforementioned shortcomings.
   * <u>JIT Compiler.</u> 
     When a method is called several times, the Interpreter is confronted with a difficulty. 
     As a result, JIT Compiler comes to the rescue. 
     JIT Compiler is a tool that compiles bytecode into machine code. 
     Then, for those “repeated method calls,” this machine code will be used directly. 
     It is substantially faster than the Interpreter in this manner.
   * <u>Garbage Collector.</u> 
     It’s a memory management application that removes an unreachable item from the heap memory area.

![img](https://www.freecodecamp.org/news/content/images/2021/01/image-39.png)




## [JIT Java | Just In Time compiler](https://medium.com/nerd-for-tech/jit-java-just-in-time-jit-compiler-af1cc86fe53b)

It’s vital to understand how a program runs on our computer.
There are two types which we can classify how a program runs on our computer:

1. Execute the program directly on our operating system.
2. Execute the program on a virtual machine that is run on our Operating system.

A compiler is a software program that translates computer code written in one programming language into another language.
A compiler is used for programs that translate source code from a high-level programming language to a lower-level language.


### How does a program run on a Virtual Machine?
There are 3 steps in compilation time.
1. Writing the Java code (abc.java as in diagram)
2. Compilation
3. Getting the byte code after compilation (abc. class file as in diagram)

![img](https://miro.medium.com/max/2640/1*ZAzYxBXnTKl6RGZ9jvZmvg.jpeg)

In this process, we will first write our java code and save it as a **.java** file (abc.java).
So with command `javac abc.java`, next, we will invoke the javac compiler which converts java code into byte code.
Here we can get the **.class** file (abc.class) after compilation.

Actually, this bytecode cannot that understand by our OS directly. This only can understand by the Java Virtual Machine (JVM) on our RAM.

> Виртуальная машина - это программа, которая позволяет запускать программу (байт-код) на этой машине, а не непосредственно в операционной системе.
> Когда мы запускаем наш код непосредственно в операционной системе, мы называем его native кодом, а когда мы запускаем его на виртуальной машине, мы называем его byte кодом.

This is the end of compile-time in java. Now next we will move to the run time of the java program.

As of the end of the compile-time, we get the bytecode which is a **.class** file.
So, next with the `java abc` command we will load **.class** files/**bytecodes** into the **class loader** which is inside the JVM.

Then next, the bytecode verifier inside JVM verify that the bytecode and ensures that it will not violate security requirements.
Right after that, invoke of the interpreter or JIT (Just In Time) compiler happens.
When we talk about the interpreter, it converts the bytecode into machine code (native code) by reading line by line.

The main difference between compiler and interpreter is, the compiler compiles the whole code at once into machine code(native code).
While the interpreter converts code into machine code by reading line by line.
So we can say that the Java program is slower than other languages like C because it uses interpreters during run time.

After the interpreter interprets the code then it transfers to the OS and is executed.


### The Just in Time
The JIT compiler converts recurring bytecode code blocks into machine code, which the interpreter can use immediately.
У Java JIT є невід'ємним компонентом JVM. Це покращує продуктивність виконання в десять разів порівняно з попереднім рівнем.
It improves the performance of a Java application during compilation or execution.

The JIT compiler is located inside the JVM, which consist of 3 components:
1. Class Loader - to load the class files
2. Runtime memory - to store the classes
3. Execution Engine - to execute/provide run time environment

![img](https://miro.medium.com/max/2640/1*jr9u4kZidlDWg4Q0FrILrw.jpeg)




## [Java Virtual Machine(JVM) Architecture](https://fasrinaleem.medium.com/java-virtual-machine-jvm-architecture-87b5bdd47403)

> A Java Virtual Machine (JVM) instance will be built for each program.
> As a result, after the program is finished, the JVM instance is destroyed. 
> JVM will also create a non-daemon (user threads) thread to execute the Java program.


### Lifetime of a Java Virtual Machine
* When a java application starts, a runtime instance of JVM is created. 
  When the application completes, the instance terminates.
* If you start three java applications at the same time, on the same computer, you will get three java virtual machine instances running
* Each java application runs inside its own java virtual machine.

![img](https://miro.medium.com/max/521/1*Tur-pSbx3tZOgy1rTEopWw.png)

JVM will be destroyed under 2 circumstances such as...
* If there are no non-daemon threads running. At that moment, the JVM will forcefully terminate all the active daemon threads.
* If the Java app kills itself (by calling System.exit() method).

...and obviously, JVM will be destroyed if it crashes.



## [Java Memory Management](https://medium.com/geekculture/java-memory-management-8ff9bc202a58)

Java objects are created at their instantiation and destroyed after they are dereferenced. static attributes and methods are created when their class is loaded by the classLoader, 
and are only destroyed when the classLoader is itself picked up by the GC, for these reasons you need to use static fields/methods only when it’s convenient.



## Java Heap Memory
![img](https://miro.medium.com/max/1400/1*iQmXAWwi1ddSa8mZhruRGA.png)
This picture illustrates how JVM internal memory looks like. 
Any newly created object first get into **Young Gen** and slowly moved to **Old Gen**. 
We can customize the size dynamically using-XX command as mentioned in the image.




