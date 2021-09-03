# Gson
Google library for working with Json.<br>
[GitHub Documentation](https://github.com/google/gson/blob/master/UserGuide.md)

## Object to json
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

## Exclude field from model for json
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

