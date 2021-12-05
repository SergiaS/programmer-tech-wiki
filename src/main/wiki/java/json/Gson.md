## Gson
Google library for working with Json.<br>
[GitHub Documentation](https://github.com/google/gson/blob/master/UserGuide.md)

### [Parsing JSON from URL](https://stackoverflow.com/a/7467629)
1. First you need to download the URL (as text):
```java
private static String readUrl(String urlString) throws Exception {
    BufferedReader reader = null;
    try {
        URL url = new URL(urlString);
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer buffer = new StringBuffer();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read); 

        return buffer.toString();
    } finally {
        if (reader != null)
            reader.close();
    }
}
```

2. Then you need to parse it (and here you have some options).
```java
static class Item {
    String title;
    String link;
    String description;
}

static class Page {
    String title;
    String link;
    String description;
    String language;
    List<Item> items;
}

public static void main(String[] args) throws Exception {

    String json = readUrl("http://www.javascriptkit.com/"
                          + "dhtmltutors/javascriptkit.json");

    Gson gson = new Gson();        
    Page page = gson.fromJson(json, Page.class);

    System.out.println(page.title);
    for (Item item : page.items)
        System.out.println("    " + item.title);
}
```


### Object to json
To generate from object:
```java
// some model
JsonStreamRequest jsonStrReq = new JsonStreamRequest(method, param);
        
// library class 
Gson gson = new Gson();
        
// generate json from your model to string
String message = gson.toJson(jsonStrReq);

// and now you can use it
clientEndpoint.sendMessage(message);
```

### Exclude field from model for json
Here you need add `transient` word for your field.

```java
public class JsonStreamRequest {
    private final String method;
    private final String[] params;
    private final int id;

    private transient final int ID_DEFAULT_VALUE = 47;
    
    // ...
}
```

### Setup property's names
```java
@SerializedName("r030")
private final int id;
@SerializedName("txt")
private final String currencyText;
@SerializedName("rate")
private final BigDecimal currencyRate;
@SerializedName("cc")
private final String currencyCode;
@SerializedName("exchangedate")
private final String exchangeDate;
```

### Errors

> No serializer found for class `YourClass.class` and no properties discovered to create BeanSerializer...

Добавление геттеров может решить данную проблему.


