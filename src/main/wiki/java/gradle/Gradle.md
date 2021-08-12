# Gradle

## Gradle Wrapper
Место расположение в папке `gradle` в корне проекта.

Запуск Gradle с настройками из файла `gradle-wrapper.properties` осуществляется командой `./gradlew`.

## `Build` - Сборка проекта
Получение `jar`-файла проекта. Необходимо использовать коданду `build`.

> Если используется SpringBoot, тогда необходимо указать главный файл приложения.

```properties
springBoot {
	mainClass = "com.catchshop.PriceParser.apibot.telegram.PriceParserBotApplication"
}
```

**By default, Gradle is using the standard Maven project structure.**

- ${Project}/src/main/java/
- ${Project}/src/main/resources/
- ${Project}/src/test/java/
