# Enum

Enum может использовать только `private` и `default` (по умолчанию) конструктор.

***

> Enum может имплементировать интерфейс, а каждое его значение может перезаписывать методы (полиморфизм):
```java
public class TestNote {
    public static void main(String[] args) {
        System.out.println(Currency.DIME.color());   // silver
        System.out.println(Currency.NICKLE.color()); // bronze
        System.out.println(Currency.PENNY.color());  // copper
    }

    interface MyInter{
        String color();
    }

    enum Currency implements MyInter {
        PENNY(1) {
            @Override
            public String color() {
                return "copper";
            }
        },
        NICKLE(5) {
            @Override
            public String color() {
                return "bronze";
            }
        },
        DIME(10) {
            @Override
            public String color() {
                return "silver";
            }
        },
        QUARTER(25) {
            @Override
            public String color() {
                return "silver";
            }
        };

        private int value;

        Currency(int value) {
            this.value = value;
        }
    }
}
```

***



***



***

