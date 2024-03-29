# Input Output Java Streams
> Входящие и выходящие потоки делятся на байтовые и символьные.

> По сути, потоки оборачиваются друг в друга - передаются в конструктор другого. 
> __Basic setup of decorators__:
> ```java
> InputStream fileStream = new FileInputStream(filename);
> InputStream gzipStream = new GZIPInputStream(fileStream);
> Reader decoder = new InputStreamReader(gzipStream, encoding);
> BufferedReader buffered = new BufferedReader(decoder);
> ```


## Для роботи з файлами
Краще це знати - полегшить життя.

> Чтобы узнать, куда смотрит файл (его адрес) вбей в Evaluate -> `file.getAbsolutePath()`. 

> Очень часто во время написания проверочного кода, данные могут не записываться в файл. 
> Для записи - всегда закрывай поток/-и, даже если это просто кусок проверочного кода, ибо это может влиять на результат!

> [InputStream](https://betacode.net/13527/java-inputstream) - класс в package java.io, который является базовым классом, 
> представляющим поток bytes (stream of bytes), полученный при чтении определенного источника данных, например файла.

> Нельзя использовать стримы предназначенные для текстовых файлов при работе с бинарными файлами.

> `FileWriter` и `FileReader` используются для работы с текстовыми файлами.

> `FileInputStream` и `FileOutputStream` используются для работы с бинарными файлами - получаем поток bytes (stream of bytes).

> Convert InputStream to Reader:
> ```java
> InputStream inputStream = new FileInputStream("c:\\data\\input.txt");
> Reader inputStreamReader = new InputStreamReader(inputStream);
> ```


### Закриття файлів
> Никогда не забывай закрывать стримы ввода/вывода после использования!

Чтобы не забывать это делать, лучше использовать конструкцию `try-with-resources` - в скобках `try` указываются ТОЛЬКО объекты 
имплементирующие интерфейс `AutoCloseable` - ресурсы используемые в скобках `try` будут автоматически закрыты. 
В `try-with-resources` может быть один блок `try` без `catch` если нужно. 

Другой вариант - использования блока `finally`.


### Шлях файлу
> Внимание на слеш! `/` или `\\` - два слеша!
> В java, в файлах с кодом, внутри строк символ `'\'` тоже нужно экранировать, т.к. он является управляющим как в регулярных выражениях.

В конструкторе `FileReader` и `FileWriter` указывается относительный адрес файла - относительно папки проекта, X:
```text
src/main/java/
```

Также можно указать абсолютный адрес файла, т.е. начиная с имени диска:
```text
C:\\java\\projects\\
```

***

Стосовно об'єкту `File()`:
- якщо до конструктора передати шлях який починається на `'\'`, наприклад `\\myApp\\files` - тоді це буде шлях відносно системи
  `C:\myApp\files\data.txt` :
    > <details>
    > <summary>EXAMPLE</summary>
    > 
    > ```java
    > public static void recordData(String data) {
    >   String pathToSave = System.getenv("PATH_TO_SAVE"); // \\myApp\\files
    >   String fileName = System.getenv("RECORDER_FILE_NAME"); // data.txt
    > 
    >   File file;
    >   if (pathToSave != null) {
    >     File dir = new File(pathToSave);
    >     dir.mkdirs();
    > 
    >     file = new File(dir, fileName == null ? "forecast.txt" : fileName + ".txt");
    >   } else {
    >     file = new File(fileName == null ? "forecast.txt" : fileName + ".txt");
    >   }
    > 
    >   System.err.println("PATH >>> " + file.getAbsolutePath());
    >   try (FileWriter writer = new FileWriter(file)) {
    >     System.out.println(data);
    >     writer.write(data);
    >     System.out.println("DONE!");
    >     System.out.println();
    >   } catch (IOException e) {
    >     throw new RuntimeException(e);
    >   }
    > }
    > ```
    > </details>

- якщо до конструктора передати шлях який НЕ починається з `'\'`, наприклад `myApp\\files` - тоді це буде шлях відносно програми 
  `C:\idea_projects\tt_modules-writer-reader-docker\myApp\files\data.txt` :
    > <details>
    > <summary>EXAMPLE</summary>
    > 
    > ```java
    > // той самий код з іншим шляхом
    > public static void recordData(String data) {
    >   String pathToSave = System.getenv("PATH_TO_SAVE"); // myApp\\files
    >   String fileName = System.getenv("RECORDER_FILE_NAME"); // data.txt
    > 
    >   File file;
    >   if (pathToSave != null) {
    >     File dir = new File(pathToSave);
    >     dir.mkdirs();
    > 
    >     file = new File(dir, fileName == null ? "forecast.txt" : fileName + ".txt");
    >   } else {
    >     file = new File(fileName == null ? "forecast.txt" : fileName + ".txt");
    >   }
    > 
    >   System.err.println("PATH >>> " + file.getAbsolutePath());
    >   try (FileWriter writer = new FileWriter(file)) {
    >     System.out.println(data);
    >     writer.write(data);
    >     System.out.println("DONE!");
    >     System.out.println();
    >   } catch (IOException e) {
    >     throw new RuntimeException(e);
    >   }
    > }
    > ```
    > </details>


## FileWriter та FileReader
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
В `FileWriter` есть перегруженный конструктор, один из которых второй параметр с названием `append` типа `boolean`. 
При установке данного флага будет добавление в файл, а не перезапись.

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
Класс `OutputStreamWriter` преобразует байтовые потоки в символьные, или, другими словами, он позволяет преобразовать `OutputStream` в `Writer`.

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
`InputStreamReader` - это подкласс `Reader`, который представляет собой мост, позволяющий преобразовать байтовый поток (byte stream) в символьный (character stream).
Другими словами, он позволяет преобразовать `InputStream` в `Reader`.


## Перемотка стримов (I/O поток)
> __Не все потоки поддерживают перемотку стримов.__

Используется, например, при определении чтения архива несколькими разными алгоритмами, чтобы узнать каким алгоритмом нужно расшифровать данный архив.

Принцип работы - сначала идёт чтение файла и курсор перемещается побайтово, в случае появления ошибки (не тот формат), 
`catch` должен обработать данную ошибку - отматать стрим (переставить курсор в начало потока).


### Как перемотать стрим?
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
