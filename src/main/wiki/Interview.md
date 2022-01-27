# Interview / Собеседование
* [Список видео: Собеседование Java разработчик](https://www.youtube.com/playlist?list=PLlsMRoVt5sTMMCwd_gLaaZMkQhzVh9hLA)

## Примеры вопросов и ответов

### Расскажи о себе
Меня зовут Сергей, мне 33 года.
С Java познакомился лет 5 назад, это был курс JavaRush.
Уделял обучению тогда около 5 часов в неделю. Так продлилось около полугода, после чего забил на года полтора.

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

### Как ты понимаешь ООП?
Объектно-ориентированное программирование. 
Здесь строится всё на объектах. Где дан объект определённого типа, если возможно, то его можно кастить. Но Integer'у присвоить String не получится.
Например, в динамическом языке JS - это сделать можно.

### Что такое инкапсуляция?
Это сокрытие данных при помощи модификаторов доступа и геттеров и сеттеров, 
это ограничивает доступ к данным где считает нужным программист, + в геттерах и сеттерах можно дописать ещё код.
Пример из жизни - двигатель автомобиля - где мы не имеет прямого доступа к работе двигателя, 
но знаем как его использовать. Вмешательство в работу двигателя может привести к непредсказуемой ситуации, 
из-за которой можно и машину сломать, и себе навредить. Ровно то же самое происходит и в программировании.

### Какие различия между ссылочными и примитивными типами данных?
Значение копируется у примитивов, а ссылочные типы данных передаются по ссылке.
Примитивы хранятся в стеке. Но примитив является частью объекта, тогда - как и ссылочные типы данных, хранятся в хипе.
Примитивы не могут быть `null`.

### Какое математическое представление int
2 в степени 31.

### Массив - это примитив или объект?
Это объект. Это статическая структура данных.

### Почему размер массива нельзя увеличить? Мог бы добавлять в конец сколько нужно.
Потому что, для массива выделяется определенное кол-во ячеек подряд. 
И если не будет нужного кол-ва ячеек рядом, придётся пересоздавать массив, а это ресурсоёмкий процесс.

### Когда мы делаем оценку сложности алгоритма, мы учитываем общий или худший случай?
Худший. Когда строишь мост - учитывать нужно максимальный вес с запасом.

### Какой максимально эффективный способ копирования массива?
Есть специальные вспомогательные методы в классе `Arrays`. 
Самым эффективным считается `System.arraycopy`.

### Как устроена структура данных HashMap
HashMap состоит из «корзин» (bucket`ов). «корзины» — это элементы массива, которые хранят ссылки на списки элементов.

При добавлении новой пары ключ-значение, вычисляет хеш-код ключа, на основании которого вычисляется номер корзины (номер ячейки массива), 
в которую попадет новый элемент. 
Если корзина пустая, то в нее сохраняется ссылка на вновь добавляемый элемент, если же там уже есть элемент, 
то происходит последовательный переход по ссылкам между элементами в цепочке, в поисках последнего элемента, 
от которого и ставится ссылка на вновь добавленный элемент. Если в списке был найден элемент с таким же ключом, то он заменяется.

Добавление, поиск и удаление элементов выполняется за константное время. Хеш-функций должны равномерно распределять элементы по корзинам, 
в этом случае временная сложность для этих 3 операций будет не ниже O(log(n)), а в среднем случае как раз константное время.

### Что такое коллизия хеш кода с точки зрения HashMap
Это когда объект с данным хеш кодом уже существует в мапе.
Происходит это из-за плохо реализованного метода `hashCode` (например, возвращает всегда 1), значения будут складываться в одну и туже корзину.

### Расскажи про TREEIFY_THRESHOLD в HashMap
Это статическая переменная, которая указывает ограничение на кол-во элементов в одной корзине, при достижении которого, 
внутренний связный список будет преобразован в древовидную структуру (красно-черное дерево).
Дефолтное значение = 8.

### Чем отличается коллекция от стрима
Это способ управления данными в функциональном стиле, работая с данными как бы одновременно, а не перебором. 

### Что такое Parallel Stream
Это возможность обработки данных в несколько потоков.

### Для чего нужен ExecutorService?
ExecutorService облегчает жизнь разработчику тем, что он предоставляет методы для завершения работы потоков и 
методы для создания объектов Future для отслеживания выполнения задач.

### БД. Что такое индексы?
Это объект БД, который повышает производительность поиска данных. Это равносильно указателю в книге. 

### [Чем отличается HTTP от HTTPS?](https://hostiq.ua/wiki/ukr/http-https/)
* HTTP это протокол передачи данных. HTTPS является продолжением HTTP - он имеет шифрование.
* Передача данных через HTTP является не защищенной, а через HTTPS - шифруются.
* HTTP использует порт 80, а HTTPS - 443.
* HTTPS используется на сайтах, где используются важные данные.
* Для реализации HTTPS на веб-сервере должен быть установлен специальный SSL сертификат. 

### Каков контракт между equals и hashcode?
Если у объектов разный хеш код, тогда объекты будут считаться разными и дело до сравнения по equals не дойдёт.

### Что такое бутылочное горлышко?
Это ограничение системы при котором теряется часть производительности. Например, метод помечен `synchronized`, 
и это значит что в него попадёт только один метод, а остальные будут ждать. 

### Расскажи про кеш в Hibernate
Есть пару видов кеша:

* Это первый уровень кеша (кеш сессии), он всегда включен и его нельзя отключить. Он доступен у текущей сессии.

* Второй уровень кеша - в основном отвечает за кэширование объектов, доступен у фабрики сессий. Он отключен и не имеет реализации.
Не хранит сами объекты ваших классов. Он хранит информацию в виде массивов строк, чисел и т.д. 
И идентификатор объекта выступает указателем на эту информацию. Концептуально это нечто вроде Map.

*  Кэш запросов - по умолчанию отключен, интегрирован с кэшем второго уровня.

### Чем отличается интерфейс от абстрактного класса?
* Можно наследовать только один класс, а имплементировать интерфейсов сколько угодно.
* У интерфейсов нет полей и конструкторов.

### Когда лучше использовать абстрактный класс?
* Абстрактные классы используются, когда есть отношение "is-a", то есть класс-наследник расширяет базовый абстрактный класс, 
* а интерфейсы могут быть реализованы разными классами, вовсе не связанными друг с другом.


