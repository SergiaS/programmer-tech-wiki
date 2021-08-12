# Configuration

## [Unsound null safety](https://dart.dev/null-safety/unsound-null-safety)

Ошибки типа:
> Non-nullable instance field 'name' must be initialized.

> Error: Field 'name' should be initialized because its type 'String' doesn't allow null.

Насколько понимаю, на 10 августа 2021 года нужно перед таким полями ставить спец-слово `late`:
```dart
class User {
  late String name;
  late int age;
  late bool isHappy;
  late List<String> hobbies;
}
```

A Dart program can contain some libraries that are null safe and some that aren’t. 
__These mixed-version programs execute with unsound null safety.__

Dart tools support two modes:

* Mixed-version programs run with __unsound null safety__. 
  It’s possible for null reference errors to occur at runtime, but only because a null or nullable type escaped from some null-unsafe library and got into null-safe code.

* When a program is fully migrated and all its libraries are null safe, then it runs with __sound null safety__, with all of the guarantees and compiler optimizations that soundness enables.

Dart tools automatically run your program in sound mode if the main entrypoint library of your program has opted into null safety. 
If you import a null-unsafe library, the tools print a warning to let you know that they can only run with unsound null safety.
