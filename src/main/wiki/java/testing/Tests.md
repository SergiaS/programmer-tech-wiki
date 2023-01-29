# Tests

> Модульне тестування (англ. Unit testing) — це метод тестування програмного забезпечення, який полягає в окремому тестуванні кожного модуля коду програми.
> Модулем називають найменшу частину програми, яка може бути протестованою.
> В об'єктно-орієнтованому програмуванні це метод.

> Інтеграційне тестування виконується, коли об'єднано два модулі, для того, щоб перевірити поведінку та функціональність обох модулів після інтеграції.
> 
> Інтеграційне тестування виконується після модульного тестування та перед верифікацією та валідацією ПЗ.

 


## JUnit
JUnit - бібліотека для модульного тестування ПО.

JUnit - найпопулярніша бібліотека для юніт-тестування у мові Java. 
Він надає певні методи підтвердження для всіх примітивних типів і об'єктів і масивів (примітивів або об'єктів).
Порядок параметрів - очікувана величина, а потім - фактична величина.

***

JUnit надає текстовий командний рядок, а також засновані на AWT і Swing графічні механізми складання звітів з виконанними тестами. 
Однак для тих розробників, які хочуть проводити модульне тестування в контейнерах сервера додатків і відображати результати в форматі HTML або XML, 
середа JUnit виявиться обмеженою і неефективною. 
Тому співтовариство розробників відкритого вихідного коду розширило можливості JUnit, реалізувавши інтегроване середовище тестування JUnitEE, 
яка дозволяє запускати модульні JUnit-тести всередині контейнерів сервера додатків.

Також, JUnit не надає можливості запускати модульні тести паралельно. 
Саме ця проблема лягла в основу завдання цієї роботи - 
а саме розробити та запропонувати розширення, яке надасть можливість одночасного запуску модульних тестів.

## Рівні тестування:
- модульне тестування (unit testing)
- інтеграційне (integration testing)
- системне (system testing)
- приймальне тестування (UAT testing)

**Модульні тести** пишуть розробники, щоб переконатися, що їх код працює нормально та відповідає специфікаціям користувачів.
Вони перевіряють свої шматочки коду, такі, наприклад, як класи, функції, інтерфейси та процедури. 
Ще до готовності програми можна виявити помилки та дефекти в коді. 
І саме для цього розробники використовують модульне тестування.

Для порівняння, **інтеграційне тестування** виконується, коли об'єднано два модулі, для того, щоб перевірити поведінку та функціональність обох модулів після інтеграції.
Іноді можуть виділяти декілька рівнів інтеграційного тестування:
- _інтеграційне тестування модулів (компонентів)_: коли інтегруються модулі, виконується тестування, яке називається тестуванням інтеграції компонентів.
Це тестування в основному зроблено для забезпечення того, щоб код не зламався після інтеграції двох модулів;
- _тестування системної інтеграції (System integration testing - SIT)_ - тестування, де тестувальники в основному перевіряють,
що в одному середовищі всі пов'язані системи повинні підтримувати цілісність даних і можуть працювати в координації з іншими системами.

А от системне тестування передбачає перевірку всієї системи на наявність помилок та недоліків. 
Цей тест здійснюється шляхом взаємодії між апаратними та програмними компонентами всієї системи (які раніше були перевірені спочатку модульними, а потім інтеграційними тестами), а потім тестують її в цілому.
Цей метод можна віднести до метода тестування “чорного ящику", де програмне забезпечення перевіряється на очікувані користувачем умови роботи, 
а також на потенційне виключення та граничні умови.


## Поняття методології розробки програмного забезпечення

> Виділяють два підходи до розробки через тестування - _Test Driven Development_ (**TDD**) i _Behavior Driven Development_ (**BDD**).

## Пояснення

***

Mock (мок-об’єкт) - це фіктивний об’єкт.

Об'єкт _Mock_ використовується для модульного тестування. 
Якщо у вас є об'єкт, методи якого ви хочете протестувати, і ці методи залежать від будь-якого іншого об'єкта, 
ви створюєте макет залежності, а не фактичний екземпляр цієї залежності.
Це дозволяє вам ізолювати свій об'єкт.

Mocking - це спосіб перевірки функціональності класу в умовах відокремленості.
Він не вимагає з'єднання з базою даних або властивостей читання файлів чи файлового сервера для перевірки функціональності. 
Mock-об'єкти виконують емуляцію справжньої служби.

***

Модульний тест - це тест, пов'язаний з єдиною відповідальністю одного класу, який часто називається "System Under Test" (SUT).
Метою одиничних тестів є перевірка того, що код працює в SUT.

***