# Class
* [Theory: Defining classes](https://hyperskill.org/learn/step/3618)

> Все классы по умолчанию наследуются от класса `Object`.

> В одном файле может быть только один `public` класс.

## Конструкторы (Constructors)

### Перегрузка конструкторов и this
- В перегруженном конструкторе можно передавать аргументы другому конструктору используя слово `this`.
- Перегруженные конструкторы имеют разный список параметров - аналогично перегруженным методам.
- Access modifier (модификатор доступа) может быть одинаковый и различный - аналогично перегруженным методам.
- Конструкторы, отличающиеся только access modifier не являются перегруженными.
- Конструкторы не имеют возвращающего типа.
- Внутри тела конструктора нельзя вызывать его перегруженный конструктор по имени класса (COMPILE ERROR), 
для этого используется слово `this` (должен быть первым выражением (statement) внутри конструктора).
- Конструктор не может быть `final` и `static`.

### Слово super в конструкторе
- Обращение к предку/родителю - `super`.
- Выражение `super` вызывает конструктор super-класса (родительского), который заканчивает свою работу всегда раньше конструктора дочернего класса.
- Выражение `super` если есть, то должно стоять на 1-ой строке конструктора.
- Если мы сами не пишем выражение `super`, то компилятор дописывает его сам, обращаясь к конструктору без параметров super-класса.
- `super` нельзя употреблять в static-методах и static-переменных.
- Выражение `super` и `this` не могут одновременно находиться в теле конструктора.
- Если компилятор подсвечивает ошибку в конструкторе, нужно проверить какой по умолчанию вызывается конструктор super-класса (пример ошибки):
  ```java
  class A {
     public A(String a, String b) {
        System.out.println(a + " " + b);
     }
  }
  
  class B extends A {
     public B(String str) { // error - у родителя нет конструктора с 1 аргументом
        // super(str, "test"); // если явно передать аргументы - всё будет ок
        System.out.println(str);
     }
  }
  ```



## Методы

### Перегрузка методов (Methods Overloading)
- Перегруженные методы имеют одинаковые имена и разный список параметров (разный по типу данных, разный по кол-ву и по порядку)
- Return type (возвращаемый тип) может быть одинаковый и различный.
- Access modifier (модификатор доступа) может быть одинаковый и различный.
- Методы, отличающиеся только Return type или Access modifier не являются перегруженными.

### Приоритетность перегруженных методов:
1. Точное совпадение типов данных.
2. Поглощающие типы данных (большие типы данных для primitive (`long` больше `int`), и parent классы для reference типов (`Object` для `String`)).
3. Autoboxed типы данных. Если на вход передают 10 (т.е. `int`) и если нет метода с аргументом `int`, но есть `Integer`, то его примет `Integer`.
4. Самый низкий приоритет имеет varargs.

```java
abc(2);
static void abc(int i)  {   System.out.println("int");   }   // приоритет 1
static void abc(byte i) {   System.out.println("byte");   } // error, need cast: abc((byte) 2);
static void abc(long i) {   System.out.println("long");   } // приоритет 2

test(5,3);
static void test(int a, int b) {   System.out.println("test1");   }        // priority 1
static void test(long a, long b) {   System.out.println("test2");   }      // priority 2
static void test(Integer a, Integer b) {  System.out.println("test3");   } // priority 3
static void test(int ... a) {   System.out.println("test4");   }           // priority 4
```

> Конвертация типов данных для соответствия параметров метода не может происходить в 2 этапа (х: конвертация, а потом autoboxing).

Ниже, метод `t2` с параметром `int` не существует. 
Самый близкий доступный тип данных здесь `long` (`int` конвертируется в `long`); 
Потом вызов будет с помощью autoboxing - `Integer` (`int` превращается в `Integer`); 
Далее - `Object`. 
При первых двух методах с `Long` будет **compile time error**, т.к. невозможно чтобы `int` сначала конвертировался в `long` (примитивный тип данных), 
а потом ещё происходил бы autoboxing - это уже два этапа конвертации.
```java
t2(50);
static void t2(Long a) {   System.out.println("test1");   }      // error
static void t2(Long ... a) {   System.out.println("test2");   }  // error
static void t2(long a) {   System.out.println("test3");   }      // priority 1
static void t2(Object a) {   System.out.println("test4");   }    // priority 3
static void t2(Integer a) {   System.out.println("test5");   }   // priority 2
```

### Сравнение объектов
- В классах `StringBuffer` и `StringBuilder` не перезаписаны методы `equals()` и `hashCode()`. `equals` работает как `==`.


#### HashCode
* Java хеширование, это преобразование с помощью метода `hashCode`, любого объекта в `int`.
* Некоторые коллекции используют хеширование при поиске и сравнении. Коллекции **Hash** относятся сюда.
* Сравнение в `HashMap` сначала идет по `hashCode`, а потом по `equals`.
* Если вы переопределили `equals`, то переопределите и `hashCode`.
* Результат нескольких выполнений метода `hashCode` для одного и того же объекта должен быть одинаковым.
* Если, согласно методу `equals`, два объекта равны, то и hashcode данных объектов обязательно должен быть одинаковым.
* Ситуация, когда результат метода `hashCode` для разных объектов одинаков, называется коллизией. Чем её меньше, тем лучше.


#### Equals
- При `==` всегда сравниваются только адреса объектов (ссылки), а не их внутренности.
- Для сравнения внутренностей - метод `equals()`.
  ```java
  String s1 = "Hello world!";
  String s2 = new String("Hello world!");
  String s3 = "Hello world!";
  System.out.println(s1 == s2);      // false
  System.out.println(s1.equals(s2)); // true
  System.out.println(s1 == s3);      // true
  System.out.println(s1.equals(s3)); // true
  ```

**Правильно и логично перезаписанный метод `equals` должен обладать следующими свойствами:**

1. Симметричность - для **non-null** (не пустых) ссылочных переменных `a` и `b`, `a.equals(b)` возвращает `true` тогда и только тогда, когда `b.equals(a)` возвращает `true`. 
   Т.е. если `a` и `b`, то `b` и `a` также должны быть равны.
2. Рефлексивность - для **non-null** (не пустой) ссылочной переменной `a`, `a.equals(a)` всегда должно возвращать `true`. Т.е. объект равен сам себе всегда.
3. Транзитивность - для **non-null** (не пустых) ссылочных переменных `a`, `b` и `c`, если `a.equals(b)` и `b.equals(c)` возвращает `true`, то `a.equals(c)` тоже должно возвращать `true`.
4. Постоянство - для **non-null** (не пустых) ссылочных переменных `a` и `b`, неоднократный вызов `a.equals(b)` должен возвращать или только `true`, или только `false`. 
   Т.е. сравнивания 2 объекта или всегда равны друг другу, или нет.
5. Для **non-null** (не пустой) ссылочной переменной `a`, `a.equals(null)` всегда должно возвращать `false`. Т.е. сравнение объекта с ничем всегда должен возвращать `false`.


### varargs = Variable Arguments
- В листе параметров метода может быть только 1 **varargs**.
- В листе параметров метода **varargs** должен стоять самым последним.
- Java переводит **varargs** в массив, поэтому если будет два перегруженных метода с **varargs** и с массивом на вход - будет **Compile time error**:
```java
void abc(int array[]){}
void abc(int ... a){}
```
- Но если вместо перегруженного метода с массивом будет указан на вход один аргумент `int`, то будет выполняться именно этот метод как более точный, например `abc(5)`:
```java
static void abc(int a){} // выполнится этот метод
static void abc(int ... a){}
```
Метод `void abc(int ... a)` может принять массив интов - `int[]`!

- **varargs** НЕ ОБЯЗАТЕЛЬНО указывать - метод заработает и без аргументов, если внутри метода они не используются!


## [static](https://hyperskill.org/learn/step/3534)
Static methods have several special features:

* a static method can access only static fields, it cannot access non-static fields;
* a static method can invoke another static method, but it cannot invoke an instance method;
* a static method cannot refer to this keyword because there is no instance in the static context. BUT, in compile-time it's OK.


## Статические и не статические инициализаторы (Static & non-static initializers)
**Initializer block** - это блок, где не задаются какие-либо параметры и нет return type.

* Initializer block срабатывает каждый раз, когда создаётся новый объект соответствующего класса.
* Static initializer block срабатывает один раз, когда класс загружается в память.
* Равнозначные initializer block выполняются в той последовательности, в которой они описаны в классе.

Последовательность инициализации initializer блоков и переменных:
1. Статические блоки и переменные родительского класса;
2. Статические блоки и переменные дочернего класса;
3. Не статические блоки и переменные родительского класса;
4. Конструктор родительского класса;
5. Не статические блоки и переменные дочернего класса;
6. Конструктор дочернего класса;

   > Инициализация пунктов 3-6 происходит только при каждом создании объекта!


## Классы обертки (Wrapper classes)
* У wrapper классов метод `equals()` перезаписан.

Классы `Byte`, `Short`, `Integer`, `Long`, `Double` и `Float` являются потомками класса `Number`.
```java
Number b = new Integer(10);
int c = (Integer) b; // можно (int) b;
Byte b10 = new Byte((byte)5);
```
Метод `parse()` позволяет конвертировать подходящее значение типа данных `String` в соответствующий примитивный тип данных.
```java
int i1 = Integer.parseInt(new String("54"));
Boolean b1 = Boolean.parseBoolean(new String("test")); // false
Boolean b2 = Boolean.parseBoolean(new String("true")); // true
```
Метод `valueOf()` позволяет нам создавать новый объект wrapper класса того типа, на котором данный метод был вызван.
```java
Byte b10 = Byte.valueOf(10) // error
Byte b11 = Byte.valueOf((byte)10) // ok
```



## Вложенные классы (Nested Classes)
![Nested Classes Schema](https://cdn.javarush.ru/images/article/10ac2dc4-b564-4868-83cf-77792005a358/800.webp)

Сначала создается новый экземпляр `new` класса (необязательно абстрактного), потом переопределяются методы или создаются новые методы/переменные внутри фигурных скобок `{` `}`.

В примере ниже в анонимном классе создается метод `printName` и в конце вызывается:
```java
new Solution("The Darkside Hacker") {
    void printName() { // создаем новый метод
        System.out.println(getName());
    }
}.printName(); // вызываем созданный новый метод
```

### [Вложенные внутренние классы - JavaRush](https://javarush.ru/groups/posts/2181-vlozhennihe-vnutrennie-klassih)
1. Объект внутреннего класса не может существовать без объекта «внешнего» класса.
2. У объекта внутреннего класса есть доступ к переменным «внешнего» класса.
3. Объект внутреннего класса нельзя создать в статическом методе «внешнего» класса.
    * Логика здесь та же: статические методы и переменные могут существовать и вызваться даже при отсутствии объекта.
      Но без объекта «внешнего» класса доступа к внутреннему классу у нас не будет. Явное противоречие!
      Поэтому наличие статических переменных и методов во внутренних классах запрещено.
4. Внутренний класс не может содержать статические переменные и методы.
5. При создании объекта внутреннего класса важную роль играет его модификатор доступа.
    * Внутренний класс можно обозначить стандартными модификаторами доступа — `public`, `private`, `protected` и `package private`.
      Модификаторы доступа для внутренних классов работают так же, как и для обычных переменных.

### [Статические вложенные классы - JavaRush](https://javarush.ru/groups/posts/2183-staticheskie-vlozhennihe-klassih)
В чем же отличие между статическим и нестатическим вложенными классами?
1. Объект статического класса не хранит ссылку на конкретный экземпляр внешнего класса.
    * Объект статического вложенного класса вполне может существовать сам по себе.
    * При этом неважно, какой модификатор доступа имеет статическая переменная во внешнем классе.
      Даже если это `private`, доступ из статического вложенного класса все равно будет.
      Это касается не только доступа к статическим переменным, но и к статическим методам.
2. Разный доступ к переменным и методам внешнего класса.
    * Статический вложенный класс может обращаться только к статическим полям внешнего класса.

### [Анонимные классы - JavaRush](https://javarush.ru/groups/posts/2193-anonimnihe-klassih)
> **Анонимный класс не может содержать статические переменные и методы.**

> **У анонимных внутренних классов не может быть конструктора.**

Анонимный класс — это полноценный внутренний класс. Поэтому у него есть доступ к переменным внешнего класса, в том числе к статическим и приватным.


### Пример анонимного класса - JavaRush
```java
public class Solution {
    private String name;

    Solution(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    private void sout() {
        new Solution("The Darkside Hacker") {
            void printName() {
                System.out.println(getName());
            }
        }.printName();
    }

    public static void main(String[] args) {
        new Solution("Риша").sout();
    }
}
```
Если у метода `getName()` модификатор доступа будет `protected` - тогда вывод будет:
```java
// console output:
The Darkside Hacker
```
Если у метода `getName()` модификатор доступа будет `privet` - тогда вывод будет:
```java
// console output:
Риша
```

### [Сложный пример наследования от внутреннего класса - JavaRush](https://javarush.ru/tasks/com.javarush.task.task24.task2406)
TASK: Внутри класса `Solution` создай 2 внутренних public класса `Apt3Bedroom`, `BigHall`.
Унаследуй их от `Apartments` и `Hall`.

```java
import java.math.BigDecimal;

public class Solution {

    public class Building {

        public class Hall {
            private BigDecimal square;

            public Hall(BigDecimal square) {
                this.square = square;
            }
        }

        public class Apartments {

        }
    }

    public static void main(String[] args) {

    }
}
```

<details>
<summary>SHOW SOLUTION (task2406) = |</summary>

```java
import java.math.BigDecimal;

public class Solution {

    public class Building {

        public class Hall {
            private BigDecimal square;

            public Hall(BigDecimal square) {
                this.square = square;
            }
        }

        public class Apartments {

        }
    }

|   public class Apt3Bedroom extends Building.Apartments {
|       public Apt3Bedroom(Building building) {
|           building.super();
|       }
|   }

|   public class BigHall extends Building.Hall {
|       public BigHall(Building building) {
|           building.super(new BigDecimal(2000));
|       }
|   }

    public static void main(String[] args) {

    }
}
```

</details>


### Локальные классы
Локальные классы — классы внутри методов.
Они работают как обычные внутренние классы, но их можно использовать в пределах того метода, где их объявили.<br>

Класс, объявленный внутри метода, может использовать локальные переменные этого метода.
Но есть одно ограничение – переменные можно только «читать», изменять их нельзя.<br>
Поэтому классы, объявленные внутри метода, могут иметь доступ только к тем переменным метода, которые помечены ключевым словом final.

Пример, использования добавления к объекту `Car` локальной переменной метода `policeNumber`.
```java
class Car {
   public ArrayList<Car> createPoliceCars(int count) {
      ArrayList<Car> result = new ArrayList<Car>();
      for (int i = 0; i < count; i++) {
|        final int number = i;
|        result.add(new Car() {
|           int policeNumber = number;
|        });
      }
      return result;
   }
}
```




## Binding, hiding and overriding
> Что можно делать override: Все методы, которые не являются `static`, `final` или `private`.

> Что можно делать hide: static методы, не приватные переменные.

> Private и final методы и переменные нельзя делать ни override, ни hide!

> Почему static методы нельзя сделать override? Само понятие static не подразумевает под собой процесс создания объектов.

```java
class Animal {
   void getName() {
      System.out.println("Animal");
   }
}

class Mouse extends Animal {
   void getName() {
      System.out.println("Mouse");
   }
}

public class Test {
    void abc(Animal a) {  
        System.out.println("A");  
    }
    
    void abc(Mouse m) {  
        System.out.println("M");  
    }

    public static void main(String[] args) {
        Test t = new Test();
        Animal an = new Mouse();
        t.abc(an); // "A" - для всех переменных
        an.getName(); // "Mouse" - т.к. он перезаписан
    }
}
```


**Binding (связывание)**

Binding - определение вызываемого метода, основываясь на объекте, который производит вызов или типе данных ссылочной переменной.

1. **Compile time binding** - используется для всех переменных и `private`, `static`, `final` методов. Происходит тогда, когда методы в sub классе не могут перезаписываться (`@Override`). 
Компилятор смотрит на тип переменной - на левую часть (`Employee emp = new Employee();`), и вызывает метод именно по этому типу.
- При хайдинге (скрытии), к примеру для статичных методов, происходит **Compile time binding** и в зависимости от класса ссылочной переменной определяется какой метод будет использоваться.

2. **Run time binding** - для всех других методов. Компилятор не знает на какой тип ссылается переменная. Компилятор смотрит на объект, на который ссылается - правая часть ('Employee emp = new Employee();').
- При оверрайдинге происходит **Run time binding** и в зависимости реально созданного объекта определяется какой метод будет вызываться.


**Method hiding (перекрытие/скрытие метода)**

Method hiding - это перекрытие static-методов из parent-класса в sub-классе.


## Отношения объектов
1. A "uses" B = **Aggregation** : B exists independently (conceptually) from A
2. A "owns" B = **Composition** : B has no meaning or purpose in the system without A

**is-a (есть наследственность)**<br>
Это типы ассоциативного взаимоотношения между классами.
Разница между ними в том, что один объект также может являться родительским классом - **is-a**. Наследование описывает связь «является» (или по-английски «IS A»).
Лев является Животным.

Пример **is-a**, **Mouse is an Animal** (аналогично Dentist is-a Doctor, Driver is-a Worker...):
```java
class Animal {}
class Mouse extends Animal {}
```
* Оператор `instanceof` проверяет, есть ли между объектом и классом/интерфейсом связь **is-a**. Если связь **is-a** невозможна, то компилятор выдаёт ошибку.
* Объект в JAVA считается полиморфным, если он имеет более одной связи **is-a**.

**has-a (нет наследственности)**<br>
**has-a** - взаимоотношение между классами - имеет, а не является. Например, клавиатура определенно как-то связана с компьютером, но она не является компьютером.
Руки как-то связаны с человеком, но они не являются человеком. В этих случаях в его основе лежит другой тип отношения: не «является», а «является частью» («HAS A»).
Рука не является человеком, но является частью человека. Клавиатура не является компьютером, но является частью компьютера.

Например, **House has a Window**:
```java
class Window {}
class House {
    Window w = new Window();
}
```


## Casting, widening and narrowing

### Reference data types casting:
- Casting - это процесс когда вы заставляете переменную одного типа данных вести себя как переменная другого типа данных.
- Casting возможен только тогда, когда между классами/интерфейсами существует **IS-A** взаимоотношение.
- Casting из sub класса в super класс происходит автоматически - Upcasting.
- Casting из super класса в sub класс НЕ происходит автоматически - Downcasting.
- Если между классами/интерфейсами нет IS-A взаимоотношения, компилятор не допустит casting.
- Даже если компилятор допустил casting, выскочит runtime exception, если объект который мы кастим, на самом деле не принадлежит классу на который мы его делаем кастим.

### Primitive data types casting
```java
Employee x = (Doctor)new Employee(); // Run Time Exception
Doctor x = (Doctor)new Employee(); // Run Time Exception
```

**19 forms of widening - расширение типа:**
- `byte` to `short`, `int`, `long`, `float` or `double`;
- `short` to `int`, `long`, `float` or `double`;
- `char` to `int`, `long`, `float` or `double`;
- `int` to `long`, `float` or `double`;
- `long` to `float` or `double`;
- `float` to `double`.

**Narrowing without casting происходит, если выполняются 3 условия:**
- Если `int` cast-ится в `byte`, `short` или `char`.
- Если значение `int` - это константа.
- Если значение `int` помещается в соответствующий тип данных.

**22 forms of narrowing - сужение типа:**
- `short` to `byte` or `char`;
- `char` to `byte` or `short`;
- `int` to `byte`, `short`, or `char`;
- `long` to `byte`, `short`, `char`, or `int`;
- `float` to `byte`, `short`, `char`, `int` or `long`;
- `double` to `byte`, `short`, `char`, `int`, `long` or `float`.

**Numeric promotion**
```java
int i = (int)3.64; // 3 - округляет в меньшую сторону
```
Если значения данных `byte`, `short` и `char` участвуют в арифметических операциях, то они перед этим конвертируются в `int`, 
даже если в этой операции сам `int` не участвует. Исключение: не конвертирует в `int`. Унарные операторы (++).
```java
byte b = 3;
short s = 4;
char c = 5;
System.out.println(b+s+c); // 12
System.out.println(b++);   // 3
```

## Тесты
* [Тесты JAVA CORE](https://docs.google.com/spreadsheets/d/1Ma6WfnWgadmxUChkm7LUn5GVUi5D45RjvEpYvZv2XjU/edit#gid=210490946)

***

1. Каков будет результат?
```java
public class Test {
    public static void main(String[] args) {
        A c1 = new C();
        c1.abc(new B()); // "A" т.к. он объект C перезаписан
    }
}

class A {
    void abc(A a) {  System.out.println("A");  }
}

class B extends A {
    void abc(B b1) {  System.out.println("B");  }
}

class C extends B {
    void abc(B b2) {  System.out.println("C");  }
}
```

> <details>
> <summary>ПОКАЗАТЬ ОТВЕТ</summary>
> 
> Вызовется `A`, объяснение:
> - класс `A` содержит `1` метод `abc(A a)`;
> - класс `B` наследуется от `A`, содержит `2` метода: `abc(A a)` класса `A` (родителя) и свой `abc(B b)`;
> - класс `C` наследуется от `B`, содержит `2` метода: `abc(A a)` класса `B` (доступный родителю из класса `A`) и переопределенный `abc(B b)` класса `B` (родителя);
> - тип переменной `c1` класс `A`, следовательно, вызывать можем только элементы этого parent класса - поэтому вызывается `abc(A a)`, т.к. только он есть у класса (передаваемое значение `new B` является наследником `A`);
> 
> </details>

***



