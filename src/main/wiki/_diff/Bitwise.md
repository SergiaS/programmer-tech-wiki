# Bitwise

__LINKS:__
* [Побитовые операции - JavaRush](https://javarush.ru/groups/posts/1925-pobitovihe-operacii)
* [Побитовое отрицание — почему так](https://javarush.ru/groups/posts/3148-pobitovoe-otricanie---pochemu-tak)

Любое число в Java можно сконвертировать в его двоичную форму. 
Для этого нужно использовать классы-обертки:
```java
// Пример конвертации в его двоичную форму
int x = 342;
System.out.println(Integer.toBinaryString(x));
```

***


`~` — побитовый оператор “НЕ”, называемый также побитовым дополнением. 

Работает очень просто: проходится по каждому биту нашего числа и меняет его значение на противоположное: нули — на единицы, единицы — на нули.

Но интересно то, что результат будет неожиданностью, например:
```java
int a = 342;
System.out.println(Integer.toBinaryString(a));
// 101010110
System.out.println(Integer.toBinaryString(~a));
// 11111111111111111111111010101001
```
Всё дело в том, что в переменной не может лежать просто 101010110, на самом деле оно хранится как
00000000000000000000000101010110. Ведь переменная типа int занимает 4 байта, т.е. 32 бита — 32 ячейки памяти.

__Самое интересное:__ Так как в бинарном представлении знак `+` или `-` перед числом сохранить не получается, то существует одна хитрость: самый 1й бит на самом деле отвечает за знак и как раз является тем самым знаковым разрядом. А все числа хранятся по такой логике:

Числа от 00...000 до 01...111 — положительные, начинающиеся с 0 (т.е. от 0 до 2147483647), а начиная с 10...000 до 11...111 — это отрицательные, начиная с самого минимального и заканчивая -1 (т.е. от -2147483648 до -1).

Для большего понимания читай [Побитовое отрицание — почему так](https://javarush.ru/groups/posts/3148-pobitovoe-otricanie---pochemu-tak)


***


`&` — побитовый оператор “И”.

Сравнивает два числа по битам.
Результатом этого сравнения является третье число.


***


`^` — побитовое исключающее “ИЛИ” (также известно как `XOR`). 

Похож на обычное “или”. Разница в одном: обычное “или” возвращает `true`, если хотя бы один операнд является истинным. 
Но не обязательно один — если оба будут `true` — то и результат `true`.

```java
100010101
^
110110000
_________
010100101 — результат работы ^
```
Те биты, которые были в обоих числах одинаковыми, вернули 0 (не сработала формула “один из”).
А вот те, которые образовывали пару 0-1 или 1-0, в итоге превратились в единицу.


***

