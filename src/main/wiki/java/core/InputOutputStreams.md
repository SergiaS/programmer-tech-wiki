# Input Output Java Streams / Потоки ввода вывода
Входящие и выходящие потоки делятся на байтовые и символьные.

Посути, потоки оборачиваются в друг друга - передаются в коструктор другого. 

__Basic setup of decorators__:
```java
InputStream fileStream = new FileInputStream(filename);
InputStream gzipStream = new GZIPInputStream(fileStream);
Reader decoder = new InputStreamReader(gzipStream, encoding);
BufferedReader buffered = new BufferedReader(decoder);
```


<details>
<summary>SHOW MENU</summary>

- [Для работы с файлами](#для-работы-с-файлами)
  - [Закрытие файлов](#закрытие-файлов)
  - [Адрес файла](#адрес-файла)
- [FileWriter и FileReader](#filewriter-и-filereader)
  - [FileWriter](#filewriter)
    - [Добавление к данным в файле - Append](#добавление-к-данным-в-файле---append)
  - [FileReader](#filereader)
- [BufferedReader и BufferedWriter](#bufferedreader-и-bufferedwriter)
  - [Пример чтения и записи 1 - посимвольно](#пример-чтения-и-записи-1---посимвольно) 
  - [Пример чтения и записи 2 - построчно](#пример-чтения-и-записи-2---построчно) 
- [FileInputStream и FileOutputStream](#fileinputstream-и-fileoutputstream)
  - [Пример копирования картинки](#пример-копирования-картинки)
- [OutputStreamWriter](#outputstreamwriter)
- [InputStreamReader](#inputstreamreader)
- [Перемотка стримов (I/O поток)](#перемотка-стримов-io-поток)
  - [Пример копирования картинки](#как-перематать-стрим)

</details>


## Для работы с файлами
Лучше это знать, облегчит жизнь.

> Очень часто во время написания проверочного кода, данные могут не записываться в файл. 
> Для записи - всегда закрывай поток/-и, даже если это просто кусок проверочного кода, ибо это может влиять на рузельтат!

> [InputStream](https://betacode.net/13527/java-inputstream) - класс в package java.io, который является базовым классом, представляющим поток bytes (stream of bytes), полученный при чтении определенного источника данных, например файла.

> Нельзя использовать стримы предназначеные для текстовых файлов при работе с бинарными файлами.

> `FileWriter` и `FileReader` используются для работы с текстовыми файлами.

> `FileInputStream` и `FileOutputStream` используются для работы с бинарными файлами - получаем поток bytes (stream of bytes).

> Convert InputStream to Reader:
> ```java
> InputStream inputStream = new FileInputStream("c:\\data\\input.txt");
> Reader inputStreamReader = new InputStreamReader(inputStream);
> ```

### Закрытие файлов
> Никогда не забывай закрывать стримы ввода/вывода после использования!

Чтобы не забывать это делать, лучше использовать конструкцию `try-with-resources` - в скобках `try` указываются ТОЛЬКО объекты имплементирующие интерфейс `AutoCloseable` - ресурсы используемые в скобках `try` будут автоматически закрыты. 
В `try-with-resources` может быть один блок `try` без `catch` если нужно. 

Другой вариант - использования блока `finally`.


### Адрес файла
> Внимание на слеш! `/` или `\\` - два слеша!
> В java, в файлах с кодом, внутри строк символ `\` тоже нужно экранировать, т.к. он является управляющим как в регулярных выражениях.

В конструкторе `FileReader` и `FileWriter` указывается относительный адрес файла - относительно папки проекта, X:<br>
`src/main/java/`

Также можно указать абсолютный адрес файла, т.е. начиная с имени диска:<br>
`C:\\java\\projects\\`



## FileWriter и FileReader
`FileWriter` и `FileReader` используются для работы с текстовыми файлами.

### FileWriter
2 способа записи в файл:
```java
// В данном примере второй вариант перезапишет весь файл
public class FileWriterEx {
    public static void main(String[] args) throws IOException {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно! —\n" +
                "Вот последняя правда, открытая мной.\n";

        FileWriter writer = null;
        try {
            writer = new FileWriter("C:\\someFilePath\\ex.txt");

            // 1 way
            for (int i = 0; i < rubai.length(); i++) {
                writer.write(rubai.charAt(i));
            }

            // 2 way
            writer.write(rubai);

            System.out.println("DONE!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
```

#### Добавление к данным в файле - Append
В `FileWriter` есть перегруженный конструктор, один из которых второй параметр с названием `append` типа `boolean`. При установке данного флага будет добавление в файл, а не перезапись.

```java
// В данном примере второй вариант добавится к первому в файл
public class FileWriterEx {
    public static void main(String[] args) throws IOException {
        String rubai = "Много лет размышлял я над жизнью земной.\n" +
                "Непонятного нет для меня под луной.\n" +
                "Мне известно, что мне ничего не известно! —\n" +
                "Вот последняя правда, открытая мной.\n";

        FileWriter writer = null;
        try {
            writer = new FileWriter("C:\\someFilePath\\ex.txt", true); // <= append flag 

            // 1 way
            for (int i = 0; i < rubai.length(); i++) {
                writer.write(rubai.charAt(i));
            }

            // 2 way
            writer.write(rubai);

            System.out.println("DONE!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
```

### FileReader

```java
public class FileReaderEx {
    public static void main(String[] args) throws IOException {
        FileReader reader = null;
        try {
            reader = new FileReader("src/main/java/examples/zaur/work_with_files/ex.txt");
            int character;
            while ((character = reader.read()) != -1) {
                System.out.print((char) character);
            }
            System.out.println();
            System.out.println("Done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }
}
```


## BufferedReader и BufferedWriter
Использование буферизации в стримах позволяет достичь большей эффективности при чтении файла или записи в него.

#### Пример чтения и записи 1 - посимвольно
```java
public class CopyText1 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/java/work_with_files/ex.txt"));
             BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/main/java/work_with_files/ex2.txt"))
        ) {
            int character;
            while ((character = reader.read()) != -1) {
                writer.write(character);
            }
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### Пример чтения и записи 2 - построчно
```java
public class CopyText2 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/java/work_with_files/ex.txt"));
             BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/main/java/work_with_files/ex2.txt"))
        ) {
            String line;
            while ((line = reader.readLine()) != null){
                writer.write(line);
                writer.write('\n');
            }
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## FileInputStream и FileOutputStream
`FileInputStream` и `FileOutputStream` используются для работы с бинарными файлами.

Также можно использовать буферизацию - за это отвечают классы `BufferedInputStream` и `BufferedOutputStream`.

#### Пример копирования картинки
```java
public class CopyPicture {
    public static void main(String[] args) {
        try (FileInputStream inputStream = 
                     new FileInputStream("C:\\Users\\SK88\\Desktop\\ex_pic.jpg");
             FileOutputStream outputStream = 
                     new FileOutputStream("src/main/java/copied_pic.jpg")
        ) {
            int i;
            while ((i = inputStream.read()) != -1) {
                outputStream.write(i);
            }
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## OutputStreamWriter
Класс `Java.io.OutputStreamWriter` является мостом между символьными потоками и байтовыми потоками. 
Записанные в него символы кодируются в байты с использованием указанной кодировки.
```java
// Пример из файла с кодировкой windows-1251 в файл с кодировкой UTF-8
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConvertFromWindows1251ToUTF8 {
    public static void main(String[] args) throws IOException {
        String fromFileName = "src/main/java/task/JavaRush/task22/task2211/windows-1251.txt";
        String toFileName = "src/main/java/task/JavaRush/task22/task2211/utf-8.txt";

        try (
                BufferedReader reader = 
                        new BufferedReader(
                                new FileReader(fromFileName, Charset.forName("Windows-1251")));
                OutputStreamWriter outputStreamWriter = 
                        new OutputStreamWriter(
                                new FileOutputStream(toFileName), StandardCharsets.UTF_8);
        ) {
            int character;
            while ((character = reader.read()) != -1) {
                outputStreamWriter.write(character);
            }
        }
    }
}
```

## InputStreamReader
Класс преобразует байтовые потоки в символьные. Он позаботится о переводе с копьютерного языка на человеческий.


## Перемотка стримов (I/O поток)
> __Не все потоки поддерживают перемотку стримов.__

Используется, например, при определении чтения архива несколькими разными алгоритмами, чтобы узнать каким алгоритмом нужно расшифровать данный архив.

Принцип работы - сначало идёт чтение файла и курсор перемещается побайтово, в случае появления ошибки (не тот формат), `catch` должен обработь данную ошибку - отматать стрим (переставить курсор в начало потока).


### Как перематать стрим?
У абстрактного класса `InputStream` есть методы для перемотки стримов.
Для перемотки используется связка методов `mark()` и `reset()`,
где метод `mark()` запоминает место на которое необходимо переместить курсор,
а `reset()` - откатывает курсор до этого места когда нужно.
```java
public static void rewindStream() {
    // Create input stream 'test.txt' for reading containing text "abcdefg"
    FileInputStream inputStream = new FileInputStream("test.txt");

    // Convert inputStream to bufferedInputStream
    BufferedInputStream buffInputStr = new BufferedInputStream(inputStream);

    // Read and print characters one by one
    System.out.println("Char : " + (char)buffInputStr.read());

    // Mark is set on the input stream
    System.out.println("mark() called");
    buffInputStr.mark(0);

    // Read and print characters
    System.out.println("Char : " + (char)buffInputStr.read());
    System.out.println("Char : " + (char)buffInputStr.read());
    System.out.println("Char : " + (char)buffInputStr.read());

    // Reset() is invoked
    System.out.println("reset() called");
    buffInputStr.reset();

    // Read and print characters
    System.out.println("Char : " + (char)buffInputStr.read());
    System.out.println("Char : " + (char)buffInputStr.read());

    // Reset() is invoked
    System.out.println("reset() called");
    buffInputStr.reset();

    // Read and print characters
    System.out.println("Char : " + (char)buffInputStr.read());
    System.out.println("Char : " + (char)buffInputStr.read());
}
```
