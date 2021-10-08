# jQuery

* Способы создания объекта jQuery:
    ```js
    let listItems = jQuery("'li'");
    let listItems = $("li");
    ```
  Эквивалентные записи:
    ```js
    $(function () {
        console.log("ready ept 1");
    });
    
    $(document).ready(function() {
        console.log("ready ept 2");
    });
  ```
* Меняет текст на странице на новый:
    ```js
    $( 'li' ).html( 'Новый HTML' );
    ```

* Меняет текст на странице - старый + новый:
    ```js
    $("li").html(function (index, oldHtml) {
        return oldHtml + "!!!"
    });
    ```
* Вешаем событие - смена цвета пунктов при клике:
    ```js
    $("li")
        .click(function () {
            $(this).addClass("clicked");
        });
    
    let listItems = $( 'li' );
    listItems
      .click(function() {
        $( this ).addClass( 'clicked' );
      });
    ```



