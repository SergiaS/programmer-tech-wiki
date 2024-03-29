# Interview / Собеседование
* [Resume builder online](https://app.flowcv.com/resume/customization)
* [Список видео: Собеседование Java разработчик](https://www.youtube.com/playlist?list=PLlsMRoVt5sTMMCwd_gLaaZMkQhzVh9hLA)

## Примеры вопросов и ответов

### Расскажи о себе
Меня зовут Сергей, мне 33 года.
С Java познакомился лет 5 назад, это был курс JavaRush.
Уделял обучению тогда около 5 часов в неделю. 
Так продлилось около полугода, после чего забил на года полтора.

Почти последние 3 года стабильно занимаюсь. Участвовал в обучающем проекте академии Binary Studio - проект фулстек, 
дважды проходил практику в проекте TopJava - создание веб-приложения с широким стеком технологий на основе ролей и 
хранения в БД. Из своих разработок - приложение по продажам с учётом финансовой статистики, CRUD-операции выполняет JAVA,
а всю статистику считает JS. 

Также разрабатывал телеграмм бота на SpringBoot, который парсил определенные сайты - у каждого цвета и размера была своя цена, 
если цена на товар менялась - бот уведомил меня.
Сразу здесь увидел проблему - необходимо было обрабатывать огромное кол-во кейсов на каждому сайту - локали, регионы, цены в разных странах, валюты. 
Например, из общего - при поиске рез-ты могли быть очень большие - приходилось разбивать ответ на пару сообщений + не точность совпадений (шум) - 
на мобилке это не очень красиво выглядело.
А как один из сайтов обновился - приходилось делать всё сначала... 
Нужно было где-то хранить бота, чтобы он мог делать запросы каждые 10 минут, но тут и триал для облака Mongo DB истёк - 
хранил интересные позиции (избранное).

Сейчас понемногу разрабатываю вторую версию приложения по продажам, далее на его основе хочу сделать интернет-магазин.

Мне интересна идея веб приложения, где на основе REST, часть обработки данных ложится на клиента, с целью разгрузки сервера.
С этой целью я начал учить JS. Решаю задачи на LeetCode на Java, простые могу и на JS, иногда и на Python. 

***
### Как ты понимаешь ООП?
Объектно-ориентированное программирование. 
Здесь строится всё на объектах. Где дан объект определённого типа, если возможно, то его можно кастить. 
Но Integer'у присвоить String не получится.
Например, в динамическом языке JS - это сделать можно.

***
### Расскажи про принципы ООП
**Инкапсуляция (Encapsulation):**
Сокрытие данных, защита их от внешнего нежелательного вмешательства, помещение их в "капсулу".
* Характеризуется `private` переменными и `public` методами `get` и `set`, которые нередко проверяют какие-либо условия.
* Если возвращаемый тип метода `get` - это **mutable** (изменяемый) тип данных, то лучше возвращать его копию, например для `StringBuilder`.

**Наследование (Inheritance):**
В JAVA у дочернего класса может быть только один родитель. Зачем нужно наследование:
- Более короткое написание.
- Легкость в изменении/добавлении общих элементов.
- Расширяемость (extensibility).
- Более легкое тестирование классов.
- Группировка классов под общим типом.

**Абстракция:**
Абстракцию в JAVA представляют интерфейсы и абстрактные классы:
- Если в классе есть `abstract` метод, то этот класс тоже должен быть `abstract`;
- `abstract` класс может содержать, а может не содержать конструкторы и `abstract` методы;
- Дочерний класс должен перезаписать все `abstract` методы родительского класса или тоже быть `abstract`;

**Полиморфизм:**
Полиморфизм - способность объекта принимать несколько форм.
- Объект в JAVA считается полиморфным, если он имеет более одной связи **is-a**.
- Полиморфизм - это способность метода вести себя по-разному в зависимости от того, объект какого класса вызывает этот метод.
- Перезаписанные методы также часто называют полиморфными.
- Оператор `instanceof` проверяет, есть ли между объектом и классом/интерфейсом связь **is-a**. Если связь **is-a** невозможна, то компилятор выдаёт ошибку.

***
### Что такое инкапсуляция?
Это сокрытие данных при помощи модификаторов доступа и геттеров и сеттеров, 
это ограничивает доступ к данным где считает нужным программист, + в геттерах и сеттерах можно дописать ещё код.
Пример из жизни - двигатель автомобиля - где мы не имеет прямого доступа к работе двигателя, 
но знаем как его использовать. Вмешательство в работу двигателя может привести к непредсказуемой ситуации, 
из-за которой можно и машину сломать, и себе навредить. Ровно то же самое происходит и в программировании.

***
### Какие различия между ссылочными и примитивными типами данных?
Значение копируется у примитивов, а ссылочные типы данных передаются по ссылке.
Примитивы хранятся в стеке. Но примитив является частью объекта, тогда - как и ссылочные типы данных, хранятся в хипе.
Примитивы не могут быть `null`.

***
### Какое математическое представление int
2 в степени 31.

***
### Массив - это примитив или объект?
Это объект. Это статическая структура данных.

***
### Почему размер массива нельзя увеличить? Мог бы добавлять в конец сколько нужно.
Потому что, для массива выделяется определенное кол-во ячеек подряд. 
И если не будет нужного кол-ва ячеек рядом, придётся пересоздавать массив, а это ресурсоёмкий процесс.

***
### Когда мы делаем оценку сложности алгоритма, мы учитываем общий или худший случай?
Худший. Когда строишь мост - учитывать нужно максимальный вес с запасом.

***
### Какой максимально эффективный способ копирования массива?
Есть специальные вспомогательные методы в классе `Arrays`. 
Самым эффективным считается `System.arraycopy`.

***
### Как устроена структура данных HashMap
HashMap состоит из «корзин» (bucket`ов). «корзины» — это элементы массива, которые хранят ссылки на списки элементов.

При добавлении новой пары ключ-значение, вычисляет хеш-код ключа, на основании которого вычисляется номер корзины (номер ячейки массива), 
в которую попадет новый элемент. 
Если корзина пустая, то в нее сохраняется ссылка на вновь добавляемый элемент, если же там уже есть элемент, 
то происходит последовательный переход по ссылкам между элементами в цепочке, в поисках последнего элемента, 
от которого и ставится ссылка на вновь добавленный элемент. Если в списке был найден элемент с таким же ключом, то он заменяется.

Добавление, поиск и удаление элементов выполняется за константное время. Хеш-функций должны равномерно распределять элементы по корзинам, 
в этом случае временная сложность для этих 3 операций будет не ниже O(log(n)), а в среднем случае как раз константное время.

***
### Что такое коллизия хеш кода с точки зрения HashMap?
Это когда объект с данным хеш кодом уже существует в мапе.
Происходит это из-за плохо реализованного метода `hashCode` (например, возвращает всегда 1), значения будут складываться в одну и туже корзину.

***
### Расскажи про TREEIFY_THRESHOLD в HashMap
Это статическая переменная, которая указывает ограничение на кол-во элементов в одной корзине, при достижении которого, 
внутренний связный список будет преобразован в древовидную структуру (красно-черное дерево).
Дефолтное значение = 8.

***
### Чем отличается коллекция от стрима?
Это способ управления данными в функциональном стиле, работая с данными как бы одновременно, а не перебором. 

***
### Что такое Parallel Stream?
Это метод у стрима - даёт возможность обработки задачи в несколько потоков.
Сначала задача делится на части, которые обрабатываются несколькими потоками, после чего результат потоков собирается, и мы получаем ответ.
Скорость работы с однопоточной машиной может быть дольше из-за большего кол-ва инструкций, но работа этих подзадач будет последовательна.

***
### Для чего нужен ExecutorService?
ExecutorService облегчает жизнь разработчику тем, что он предоставляет методы для завершения работы потоков и 
методы для создания объектов Future для отслеживания выполнения задач.

***
### БД. Что такое индексы?
Это объект БД, который повышает производительность поиска данных. Это равносильно указателю в книге.

***
### [Чем отличается HTTP от HTTPS?](https://hostiq.ua/wiki/ukr/http-https/)
* HTTP это протокол передачи данных. HTTPS является продолжением HTTP - он имеет шифрование.
* Передача данных через HTTP является не защищенной, а через HTTPS - шифруются.
* HTTP использует порт 80, а HTTPS - 443.
* HTTPS используется на сайтах, где используются важные данные.
* Для реализации HTTPS на веб-сервере должен быть установлен специальный SSL сертификат.

***
### Каков контракт между equals и hashcode?
Если у объектов разный хеш код, тогда объекты будут считаться разными и дело до сравнения по equals не дойдёт.

***
### Что такое бутылочное горлышко?
Это ограничение системы при котором теряется часть производительности. Например, метод помечен `synchronized`, 
и это значит что в него попадёт только один поток, а остальные будут ждать. 

***
### Расскажи про кеш в Hibernate
Есть пару видов кеша:

1. Это первый уровень кеша (кеш сессии), он всегда включен и его нельзя отключить. Он доступен у текущей сессии.

2. Второй уровень кеша - в основном отвечает за кэширование объектов, доступен у фабрики сессий. Он отключен и не имеет реализации.
Не хранит сами объекты ваших классов. Он хранит информацию в виде массивов строк, чисел и т.д. 
И идентификатор объекта выступает указателем на эту информацию. Концептуально это нечто вроде Map.

3. Кэш запросов - по умолчанию отключен, интегрирован с кэшем второго уровня.

***
### Чем отличается интерфейс от абстрактного класса?
* Можно наследовать только один класс, а имплементировать интерфейсов сколько угодно.
* У интерфейсов нет полей и конструкторов.

***
### Когда лучше использовать абстрактный класс?
Абстрактные классы используются, когда есть отношение "is-a", то есть класс-наследник расширяет базовый абстрактный класс, 
а интерфейсы могут быть реализованы разными классами, вовсе не связанными друг с другом.

***
### Расскажи про отношения is-a и has-a
1. A "uses" B = **Aggregation** : B exists independently (conceptually) from A
2. A "owns" B = **Composition** : B has no meaning or purpose in the system without A

**is-a (есть наследственность)**<br>
Это типы ассоциативного взаимоотношения между классами.
Разница между ними в том, что один объект также может являться родительским классом - **is-a**. Наследование описывает связь «является» (или по-английски «IS A»). 
Лев является Животным.

Пример **is-a**, **Mouse is an Animal** (аналогично `Dentist is-a Doctor`, `Driver is-a Worker`...):
```java
class Animal {}
class Mouse extends Animal {}
```
* Оператор `instanceof` проверяет, есть ли между объектом и классом/интерфейсом связь **is-a**. 
  Если связь **is-a** невозможна, то компилятор выдаёт ошибку.
* Объект в JAVA считается полиморфным, если он имеет более одной связи **is-a**.

**has-a (нет наследственности)**<br>
**has-a** - взаимоотношение между классами - имеет, а не является. Например, клавиатура определенно как-то связана с компьютером, но она не является компьютером. 
Руки как-то связаны с человеком, но они не являются человеком.
В этих случаях в его основе лежит другой тип отношения: не «является», а «является частью» («HAS A»). 
Рука не является человеком, но является частью человека. Клавиатура не является компьютером, но является частью компьютера.

Например, **House has a Window**:
```java
class Window {}
class House {
    Window w = new Window();
}
```

***
### Расскажи про отношение Агрегация и Композиция
**Агрегация** - это когда объект не зависит от жизненного цикла другого объекта. 
Например, инжектится бин-синглтон. Т.е. после удаления основного объекта, ссылка на внедренный бин останется.

А в композиции - удалится вместе с основным объектом, поскольку ссылок на него не будет.

**Композиция** - более строгое отношение между классами, когда конкретный объект зависит от жизненного цикла другого объекта. 
Например, инжектится бин-prototype.

***
### Расскажи про Binding. Какие типы есть?
Binding - определение вызываемого метода, основываясь на объекте, который производит вызов или типе данных reference variable.

Есть 2 типа Binding:
1. **Compile time binding** (называется статичным байдингом или early). 
Осуществляется во время компиляции. Нужно <u>смотреть на левую часть объекта</u>: `Worker w = new Driver();` 
Сюда относятся методы, которые нельзя перезаписать - _private_, _static_, _final_, а также _все переменные_.
2. **Run time binding** (dynamic or late binding). 
Работает когда компилятор не может осуществить проверку к какому типу относится объект, какой метод вызвать - из саб-класса или родительского. 
Нужно <u>смотреть на правую часть объекта</u>: `Worker w = new Driver();` Относится ко всем остальным методам.

***
### Почему нельзя использовать массивы в качестве ключа в HashMap?
Потому что хеш-код массива вычисляется при его создании и не зависит от кол-ва элемента в нем (метод вычисления хэш-кода массива 
не переопределен, и вычисляется по-стандартному `Object.hashCode()` на основании адреса массива).

Так же у массивов не переопределен `equals` и выполняется сравнение указателей. 
Это приводит к тому, что обратится к сохраненному с ключом-массивом элементу не получится при использовании другого массива такого же размера и 
с такими же элементами, доступ можно осуществить лишь в одном случае — при использовании той же самой ссылки на массив, что использовалась для сохранения элемента.

***
### Какие есть требования к транзакции?
По сути транзакции характеризуются следующими четырьмя свойствами (также известными как ACID):

1. **Атомарность** (Atomicity). Атомарность позволяет объединить единые и независимые операции в «единицу работы», которая работает по принципу «все-или-ничего», 
  успешно выполняющаяся только если все содержащиеся операции успешно выполнятся. Транзакция может инкапсулировать изменение состояний. 
  Транзакция должна всегда оставлять систему в консистентном состоянии, независимо от того сколько параллельных транзакций выполняются в любой промежуток времени.

2. **Консистентность** (согласованность или целостность, Consistency). Консистентность означает что все требования уникальности были соблюдены для каждой совершенной транзакции. 
  Это подразумевает, что требования по всем ключам (primary и foreign key), типам данных, триггерам успешно пройдены и не было найдено нарушений требования уникальности.
  Понимаю как: должны быть подтверждены все (целостные) данные в рамках транзакции, а не часть - если у одного списало деньги, тогда другому пополнило.

3. **Изоляция** (Isolation). Во время выполнения транзакции параллельные транзакции не должны оказывать влияние на её результат. 
  Изоляция дает нам преимущество сокрытия не финальных изменений состояния от внешнего мира, и так же неуспешные транзакции не должны никогда порушить состояние системы. 
  Изоляция достигается через управление параллельным выполнением операций используя пессимистический или оптимистический механизм блокировки.

4. **Долговечность** (Durability). Успешная транзакция должна всегда изменять состояние системы и прежде чем закончить ее изменения состояния сохраняются в лог транзакций. 
  Если Ваша система внезапно выключится или возникнет перебой с электричеством, тогда все незавершенные транзакции можно воспроизвести.

***
### Как работают индексы БД?
Я себе представляю как библиотеку в которой всё организовано - какая и на какой полке книга.
Или стеллажи в супермаркете - в каком ряду находится нужный товар.

***
### Какие противопоказания индексации?
Если данные будут часто перезаписываться, тогда будет переиндексация, и таким образом, индексы будут работать дольше.

***
### Зачем использовать обычный стрим, если есть parallelStream, который выполнится задачу быстрее?
Задача необязательно выполнится быстрее. 
Если машина одноядерная, задача всё равно выполнится последовательно.

Не каждый функционал поддерживает многопоточность, из-за чего могут быть проблемы с данными.

***
### Является ли первичный ключ проиндексированным?
Да, является.

***
### Какая сложность поиска в БД по индексу?
Есть разные формы строения по индексу, чаще всего используются деревья, тогда `O(log n)`. 

***
### Какие аннотации Spring относятся к компонентам?
Стереотипы — это аннотации, обозначающие специальную функциональность. Все стереотипы включают в себя аннотацию `@Component`.

Аннотации-стереотипы (stereotypes): `@Component`, `@Controller`, `@RestController`, `@Service`, `@Repository` и `@Configuration`.

***
### Что такое ApplicationContext?
`ApplicationContext` загружает бины, связывает их вместе и конфигурирует их определённым образом.
Но кроме этого, `ApplicationContext` обладает дополнительной функциональностью: распознание текстовых сообщений из файлов настройки и отображение событий, 
которые происходят в приложении различными способами.
Этот контейнер определяется интерфейсом `org.springframework.context.ApplicationContext`.

Сразу создает экземпляры всех бинов, даже если бин ещё не используется!

***
### Каковы типы IoC?
Контейнер `BeanFactory`: 
Этот заводской класс содержит предварительно упакованную коллекцию компонентов, которые создаются при вызове клиентами.

Контейнер `ApplicationContext`: 
Построенный поверх контейнера `BeanFactory`, этот контейнер обеспечивает дополнительные функциональные возможности, ориентированные на предприятие. 
Например, контейнеры `ApplicationContext` предоставляют возможность разрешать текстовые сообщения и публиковать события приложения.


