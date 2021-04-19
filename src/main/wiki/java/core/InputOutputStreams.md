# Input Output Java Streams / Потоки ввода вывода


## Для работы с файлами
Лучше это знать, облегчит жизнь.

> Нельзя использовать стримы предназначеные для текстовых файлов при работе с бинарными файлами.

> `FileWriter` и `FileReader` используются для работы с текстовыми файлами.

> `FileInputStream` и `FileOutputStream` используются для работы с бинарными файлами. 

### Закрытие файлов
> Никогда не забывай закрывать стримы ввода/вывода после использования!

Чтобы не забывать это делать, лучше использовать конструкцию `try-with-resources` - в скобках `try` указываются ТОЛЬКО объекты имплементирующие интерфейс `AutoCloseable` - ресурсы используемые в скобках `try` будут автоматически закрыты. В `try-with-resources` может быть один блок `try` без `catch` если нужно. 

Другой вариант - использования блока `finally`.


### Адрес файла
> Внимание на слеш! `/` или `\\`.

В конструкторе `FileReader` и `FileWriter` указывается относительный адрес файла - относительно папки проекта, X:<br>
`src/main/java/`

Также можно указать абсолютный адрес файла, т.е. начиная с имени диска:<br>
`C:\java\projects\`



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


## BufferedReader & BufferedWriter
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
