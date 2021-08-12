# [Flutter documentation](https://api.flutter.dev/index.html)

# Flutter Configuration
В основном с Flutter работают на Android Studio, но его можно установить на Intellij IDEA (как плагин) - результат тот же.

## Settings
Необходимо будет произвести ряд настроек - установить Android SDK (в настройках IDEA - SDK Platforms).
В SDK Tools должны быть выбраны __NDK, HAXM, Android Emulator__.
Далее в Project Settings должен быть добавлен Android нужной версии.
Версии скачиваются и удаляются достаточно долго.

## Эмулятор
Требует много памяти - крайне ресурсоемкое!

### Ошибка при запуске
> The emulator process for AVD Pixel_XL_API_31 was killed

Не зависит от версии Android API. Необходимо файл `vulkan-1.dll` из __Visual Studio Code__ перенести в каталог `C:\Users\_USER_NAME_\AppData\Local\Android\Sdk\emulator\lib64`.


### Ошибка загрузки фоток с инета
> ImageCodec exception failed to load network image

Случисась с кодом:
```dart
body: Center(
  child: Image(
    image: NetworkImage('https://i.pinimg.com/originals/ee/a3/f2/eea3f205c36113297c430200d22941a8.jpg'),
  ),
),
```

Try to use additional run args like:
```shell
-d chrome --no-sound-null-safety --web-renderer=html
```


## Приложение
Базовый шаблон приложения:
```dart
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(
  home: Text('BobSinger App'),
));
```
где `home` говорит использовать на домашней странице класс `Text` и в конструктор передает текст для вывода.

### [MaterialApp](https://api.flutter.dev/flutter/material/MaterialApp-class.html)
[Material components widgets](https://flutter.dev/docs/development/ui/widgets/material#Buttons)

`MaterialApp` это настройки/компоненты для твоего приложение - то что будет использоваться в нём - темы, стили, виджеты...

```dart
class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(primaryColor: Colors.deepOrangeAccent),
      home: Scaffold(
        appBar: AppBar(
          title: Text('someFlutter App'),
          centerTitle: true,
        ),
        body: Center(
            child: Text('someBody name',
              style: TextStyle(
                  fontSize: 20,
                  color: Colors.red,
                  fontFamily: 'Times New Roman'
              ),)
        ),
        floatingActionButton: FloatingActionButton(
          child: Text('Click'),
          backgroundColor: Colors.deepOrangeAccent,
          onPressed: () {
            print('Clicked');
          },
        ),
      )
    );
  }
}
```

* Наследуясь от `StatelessWidget` необходимо реализовать один абстрактный метод `Widget build(BuildContext context)`. 
  Данный метод ожидает объект на возврат `Widget`, а `MaterialApp` является наследником.

* В `MaterialApp` указываем тему приложения (свойство `theme`), что будет на основной странице.

    * В качестве объекта, который будет показываться на главной странице (т.е. свойство `home`), у нас всегда будет один объект (`Scaffold`, который может содержать в себе много других настроек - как контейнер).

        * Свойство `appBar` - шапка для нашего приложения.

            * Если необходимо использовать текст - используй класс `Text()`.

    * `Center()` - объект который будет распологать другие объекты по центру (по X & Y). Внутрение объекты нужно помечать как дочерний объект.

* Свойство `floatingActionButton` - объект кнопка независящий от других со своими настройками.
    * `onPressed` - обязательное свойство. Код который будет обработан при нажатии на кнопку в `{}`.
      
    * Нажатие на кнопку отобразится в консоли:
        ```dart
        onPressed: () {
          print('Clicked');
        },
        ```

### [Icons](https://api.flutter.dev/flutter/material/Icons-class.html)

```dart
body: Center(
    child: Icon(Icons.settings, size: 50, color: Colors.amber),
),
```


## Регистрация файлов
После всех настроек в файле .dart вылезет шапка - сначало нажимаем __Get Dependencies__, потом __Upgrade Dependencies__. 

### Шрифты
1. Необходимо создать папку (название любое - fonts) и перенести туда файл шрифта `.ttf`. 
   Если шрифт нужен только для Android - тогда создаем папку в каталоге android. Если только для iOS - в каталоге iOS.
   Если нужен для всех ОС - создаем в корне проекта.
   
2. В файле `pubspec.yaml` нужно добавить настройки шрифту. В файле есть пример:
    ```yaml
      fonts:
        - family: Schyler
          fonts:
            - asset: fonts/Schyler-Regular.ttf
            - asset: fonts/Schyler-Italic.ttf
              style: italic
    ```
3. Указать адрес в dart-файле где нужно:
    ```dart
    body: Center(
        child: Text('someBody name',
          style: TextStyle(
              fontSize: 20,
              color: Colors.red,
              fontFamily: 'Schyler-Regular'
          ),)
    ),
    ```
4. Для отображение в эмуляторе/вебе нужно остановить проект и заного его запустить.


### Фотки
Аналогично шрифтам. В `pubspec.yaml` указать по примеру:
```yaml
assets:
    - assets/track_bike.jpg
```

## Отступы
Задаются с помощью свойств `margin` (внешний отступ) и `padding` (внутрений отступ), а также их методов:
```dart
body: Container(
  color: Colors.deepOrangeAccent,
  child: Text('brbbBrbb'),
  margin: EdgeInsets.symmetric(horizontal: 10.0, vertical: 50.0),
  padding: EdgeInsets.all(20.5),
),
```
Методы отступов используются и для `margin` и для `padding`:
* метод `symmetric` задает отступы по горизонтали и вертикали;
* метод `all` задает отступы сразу всем сторонам;
* метод `fromLTRB(left, top, right, bottom)` задает отступы для каждой стороны отдельно.


## Grid (Система сеток)
Для вывода нескольких объектов на одной странице, нужно использовать систему сеток.
Здесь нужно использовать объекты `Row` и `Column`.
```dart
body: Row(
  mainAxisAlignment: MainAxisAlignment.end,
  children: [
    Column(
      children: [
        Text('hello'),
        TextButton(onPressed: () {}, child: Text('hello1'))
      ],
    ),
    Column(
      children: [
        Text('hello'),
        TextButton(onPressed: () {}, child: Text('hello1'))
      ],
    ),
  ],
)
```

* Свойство `mainAxisAlignment` предназначено для выравнивания элемента относительно края.


## State (Состояние)
Для динамического видоизменения значения данных (т.е. нажать и отобразить рез-т, например, кол-во нажатий на кнопку) нужен класс, который использует состояние - `State`.

Изменять значение переменной нужно через метод `setState`:
```dart
onPressed: () {
  setState(() {
    _count++;
  });
},
```

## Simple App Example
```dart
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(
  home: UserPanel(),
));

class UserPanel extends StatefulWidget {
  const UserPanel({Key? key}) : super(key: key);

  @override
  _UserPanelState createState() => _UserPanelState();
}

class _UserPanelState extends State<UserPanel> {

  int _count = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.redAccent,
      appBar: AppBar(
        title: Text('cukaracha User'),
        centerTitle: true,
        backgroundColor: Colors.black45,
      ),
      body: SafeArea(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Column(
              children: [
                Padding(padding: EdgeInsets.only(top: 20),),
                Text('John Doe', style: TextStyle(
                  fontSize: 25,
                  color: Colors.white,
                ),),
                Padding(padding: EdgeInsets.only(top: 10),),
                CircleAvatar(
                  backgroundImage: AssetImage('assets/track_bike.jpg'),
                  radius: 50,
                ),
                Padding(padding: EdgeInsets.only(top: 10),),
                Row(
                  children: [
                    Icon(Icons.mail_outline, size: 25),
                    Padding(padding: EdgeInsets.only(left: 10),),
                    Text('admin@moo.com', style: TextStyle(color: Colors.white),),
                  ],
                ),
                Padding(padding: EdgeInsets.only(top: 10),),
                Text('Count: $_count', style: TextStyle(color: Colors.white),),
              ],
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.ac_unit_rounded),
        backgroundColor: Colors.amber,
        onPressed: () {
          setState(() {
            _count++;
          });
        },
      ),
    );
  }
}

```

## Комбинации клавиш
* `stless` - создает класс на основе `StatelessWidget`.

## Инициализация через метод
Метод `initState()` запускается первым и позволяет инициализировать переменные.
```dart
List toDoList = [];

@override
void initState() {
  super.initState();
  toDoList.addAll(['Buy milk', 'Wash dishes','Купить картошку']);
}
```


## Navigator
Класс `Navigator` служит для перехода между страницами, открытия разных меню и позволяет скрывать всплывающие окна.
```dart
ElevatedButton(
  onPressed: () {
    Navigator.pushReplacementNamed(context, '/todo');
  },
  child: Text('go next')
),
```
```dart
ElevatedButton(
  onPressed: () {
    setState(() {
      toDoList.add(_userToDo);
    });
    Navigator.of(context).pop(); // close all pop windows
  },
  child: Text('Добавить'),
)
```

