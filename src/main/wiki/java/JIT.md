# JIT Java | Just In Time compiler

## [SOURCE on MEDIUM](https://medium.com/nerd-for-tech/jit-java-just-in-time-jit-compiler-af1cc86fe53b)

It’s vital to understand how a program runs on our computer. 
There are two types which we can classify how a program runs on our computer:

1. Execute the program directly on our operating system.
2. Execute the program on a virtual machine that is run on our Operating system.

A compiler is a software program that translates computer code written in one programming language into another language. 
A compiler is used for programs that translate source code from a high-level programming language to a lower-level language.

### How does a program run on a Virtual Machine?
There are 3 steps in compilation time.
1. Writing the Java code(abc.java as in diagram)
2. Compilation
3. Getting the byte code after compilation (abc. class file as in diagram)

![img](https://miro.medium.com/max/2640/1*ZAzYxBXnTKl6RGZ9jvZmvg.jpeg)

In this process, we will first write our java code and save it as a **.java** file (abc.java). 
So with command `javac abc.java`, next, we will invoke the javac compiler which converts java code into byte code.
Here we can get the **.class** file (abc.class) after compilation. 

Actually, this bytecode cannot that understand by our OS directly. This only can understand by the Java Virtual Machine (JVM) on our RAM.

Виртуальная машина - это программа, которая позволяет запускать программу (байт-код) на этой машине, а не непосредственно в операционной системе.
Когда мы запускаем наш код непосредственно в операционной системе, мы называем его native кодом, а когда мы запускаем его на виртуальной машине, мы называем его byte кодом.

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
1. Classloader
2. Runtime memory
3. Execution Engine

![img](https://miro.medium.com/max/2640/1*jr9u4kZidlDWg4Q0FrILrw.jpeg)


<hr>



