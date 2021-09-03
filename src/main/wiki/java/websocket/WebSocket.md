# WebSocket
Реализовать можно на Java, glassfish, Spring, Jetty и другие...

> Правильное указание адреса вебсокета:
> __ws://hostname:port/endpointname__


## WebSockets by glassfish.tyrus

### Configuration
1. В первую очередь необходимо добавить зависимость `javax.websocket`.
> __javax.websocket api__ - это только спецификация не имеет полной реализации.
    
    
Если нужен сервер и клиент - добавить зависимость:
```xml
<dependency>
    <groupId>javax.websocket</groupId>
    <artifactId>javax.websocket-api</artifactId>
    <version>1.1</version>
</dependency>
```
    
Если нужен только клиент, а сервер с WebSocket есть по API - достаточно добавить зависимость попроще:

```xml
<dependency>
    <groupId>javax.websocket</groupId>
    <artifactId>javax.websocket-client-api</artifactId>
    <version>1.1</version>
</dependency>
```
***

2. Далее добавляем зависимости с реализациями:
```xml
<dependency>
    <groupId>org.glassfish.tyrus</groupId>
    <artifactId>tyrus-client</artifactId>
    <version>1.17</version>
</dependency>
<dependency>
    <groupId>org.glassfish.tyrus</groupId>
    <artifactId>tyrus-container-grizzly-client</artifactId>
    <version>1.17</version>
</dependency>
```

### Example
Пример веб-сокетов основан на [Binance API](https://binance-docs.github.io/apidocs/spot/en/#trade-streams).
```java
import javax.websocket.*;
import javax.websocket.WebSocketContainer;
import javax.websocket.ContainerProvider;
import java.net.URI;

@ClientEndpoint
public class WebSocketClientEndpoint {

    Session userSession;
    private MessageHandler messageHandler;

    public WebSocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {
        void handleMessage(String message);
    }
}
```
```java
import java.net.URI;
import java.net.URISyntaxException;

public class TestApp {

    public static void main(String[] args) {
        try {
            // open websocket
            WebSocketClientEndpoint clientEndpoint = new WebSocketClientEndpoint(new URI("wss://stream.binance.com:9443/ws/btcusdt@aggTrade"));

            // add listener
            clientEndpoint.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
                @Override
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndpoint.sendMessage(
                    "{\n" +
                        "\"method\": \"SUBSCRIBE\",\n" +
                        "\"params\":\n" +
                        "[\n" +
                        "\"btcusdt@aggTrade\",\n" +
                        "\"btcusdt@depth\"\n" +
                        "],\n" +
                        "\"id\": 1\n" +
                    "}"
            );

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
```
