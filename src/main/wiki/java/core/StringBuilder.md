# StringBuilder
`StringBuilder` is mutable object - you will work with the same object!

## Methods
* `setCharAt(int index, char ch)` - will replace char at index;
* `insert(...)` - will insert input at index. Also, you can set subsequence of *array* or *CharSequence*.
    ```java
    StringBuilder sb = new StringBuilder("abcde");
    sb.setCharAt(1, 'z');
    System.out.println(sb); // azcde
    ```


