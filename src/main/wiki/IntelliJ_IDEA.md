# IntelliJ IDEA

> Системный проперти: `-D`, например `-Dconsole.encoding=UTF-8 -Dfile.encoding=UTF-8`

> При збірці **classpath** знаходиться `target/classes`.


## Combinations

* Натискаємо двічі `Shift` - пошук класів, методів в файлах Java та бібліотеках.
* `Ctrl + Alt + L` - code formatting.
* `Ctrl + Alt + V` - на основе метода создает и присваивает переменной результат.
* `Ctrl + Alt + N` - убирает все переменные на указанном объекте.
* `Alt + F8` - Evaluate values in DEBUG. For the end need to click "Resume program" (`F9`).
* `Ctrl + Tab` - переключение между открытыми файлами.
* `Ctrl + Alt + T` - обернуть/засунуть код в что-то, например, в `if`.
* `Ctrl + F12` - поиск метода в классе.
* `Ctrl + H` - показывает где задействуется класс/интерфейс.
* `Ctrl + Alt + O` - убирает не используемые импорты.
* `Ctrl + Shift + F12` - сворачивает все вкладки.
* `Shift + F1` - открывает документацию в браузере (при подключенном сорце).
* `Ctrl + Shift` - позволяет перемещать строку на которой стоит курсор.
* `Alt + Shift` - мульти курсоры.
* `Alt + Insert` - сгерировать конструктор, геттер, сеттер...
* `Alt + J`, `Shift + Alt + J`, `Ctrl + Shift + Alt + J` - сначало выделяем необходимый элемент на замену - потом комбинация.
* `Ctrl + Shift + F` - Поиск кода по всему проекту.
* `Ctrl + Shift + Alt + S` - Подключение модулей к проекту.
* `Ctrl + F12` - Всплывающее окно структуры.
* `Ctrl + N` - Переход к классу.
* `Ctrl + Shift + V` - Вставка с выбором по истории копирования.
* `Ctrl + Alt + M` - Вынести код в отдельный метод.
* `Ctrl + Alt + U` - Графическое отображение наследования.
* `Ctrl + Alt + F7` - Show you all usages of the property, method, class... First of all you need to click on it then use key combination.
* `Ctrl + R` - заміна.
* `Ctrl + Shift + F9` - restart JVM HotSpot. Горячая перезагрузка.
* `Ctrl + Shift + I` - To preview a referenced image in a popup instead of in a separate editor tab, press.
* `Ctrl + Alt + Shift + U` - отображение UML диаграммы класса.


### Debug
* `Ctrl + F8` - Установить/снять точку останова.
* `F11` - Если вы хотите легко видеть какой-то элемент во время отладки, вы можете добавить к нему цветную метку, 
* нажав или выбрав соответствующий пункт в меню вкладки _Variables_ и _Watches_.
* `F9` - Возобновить выполнение программы.
* `F8` - Перейти к следующей инструкции.
* `F7` - Перейти внутрь функции.
* `Ctrl + F2` - Приостановить выполнение.
* `Shift + Ctrl + F8` - Переключить между просмотром списка точек останова и подробной информацией о выбранной точке.
* `Shift + Ctrl + F9` (если это внутри метода `main()`) - запустить отладку кода с точки на которой стоит курсор.
* `Alt + F9` - Переход к курсору.
* `Alt + F8` - В режиме отладки вы можете вычислить любое выражение, с помощью очень мощного инструмента вызываемого нажатием.


# Reset IntelliJ IDEA trial
1. cd:
```text
C:%HOMEPATH%\.IntelliJIdea*\config

example:
C:\Users\SK88\AppData\Roaming\JetBrains\IntelliJIdea2021.2
```

2. del dir `eval /s /q`:

```text
del options\other.xml
```

3. regdel dir `idea`:
```text
HKEY_CURRENT_USER\Software\JavaSoft\Prefs\jetbrains\idea
```

## Change Theme
There 2 places where you can change colors of program UI.
Go to IDEA settings:
1. Change all UI: `Appearance & Behavior` > `Appearance`
Обери іншу тему **Theme**.
2. Change only for file type `Editor` > `Color Schema`.

## Settings
* [Configuring JavaScript debugger](https://www.jetbrains.com/help/idea/configuring-javascript-debugger.html)
* [Debugging TypeScript (Node.js) in IntelliJ IDEA](https://dev.to/yutro/debugging-typescript-node-js-in-intellij-idea-3e6d)

> **Note**<br>
> **Hibernate** щоб писати запити в консолі на HQL/JPA - треба встановити плагін `JpaBuddy` і виставити чекбокс для вікна `Persistence`
> (**View** > **Tool Windows** > **Persistence**) - з'явиться вкладка/вікно на лівій частині екрана.
> Далі в цьому вікні на **entityManagerFactory** буде доступне контекстне меню **Console**.