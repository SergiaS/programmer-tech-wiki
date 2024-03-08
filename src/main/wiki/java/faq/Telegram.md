# Telegram
Here I note - how I can create a telegram bot.

* [Java library to create bots using Telegram Bots API](https://github.com/rubenlagus/TelegramBots) ДОКа на вкладці Wiki
  * [telegrambots-spring-boot-starter](https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter) 
* [GitHub - Демонстрирует многих возможностей телеграмм бота](https://github.com/kobyzau/mjc-sandbox)
    || [YouTube - Телеграм-бот на Java протягом 1 години](https://www.youtube.com/watch?v=IX3B87hqF4Y)

> **username** бота завжди повинен закінчуватися на `Bot`, наприклад `@TelegaJavaTestBot`.

> **Note**
> 
> За натиск по кнопці відповідає метод `hasCallbackQuery`.

## Створення свого бота
При створенні бота необхідно ввести ім'я (не обов'язково унікальне) і username/аліас (обов'язково унікальне).

В пошуку Телеграма шукаємо бота `@BotFather`. Подивитися команди можна набравши `/help`.
Також там присутні багато інших команд (видалити чи зміни ім'я бота) - їх можна подивитися набравши `/`.

Після чого ми отримаємо токен для доступу HTTP API:
```html
Use this token to access the HTTP API:
5294223061:AAEGqg38dtOogYYAYitmUxN48bgHCMtBm_U
```

### [Керування токенами бота](https://github.com/rubenlagus/TelegramBots/wiki/Handling-Bot-Tokens)
Опис стосується роботи змінних середовища ОС та IntelliJ.


## Перевірка роботи бота
* [API - Making requests](https://core.telegram.org/bots/api#making-requests)

Приклад, як подати `POST`-запит до свого бота через **Postman**.

Скористаємося посиланням з токеном свого бота, використовується метод `/getMe`:
```html
https://api.telegram.org/bot<token>/METHOD_NAME
```
Замість `<token>` вставляємо свій токен:
```html
https://api.telegram.org/bot5294223061:AAEGqg38dtOogYYAYitmUxN48bgHCMtBm_U/getMe
```
В результаті повинна прийти відповідь у `.json`:
```json
{
  "ok": true,
  "result": {
    "id": 5294223061,
    "is_bot": true,
    "first_name": "tgTutorialBot",
    "username": "TelegaJavaTestBot",
    "can_join_groups": true,
    "can_read_all_group_messages": false,
    "supports_inline_queries": false
  }
}
```

## Отримати інфу чату та повідомлень
Для початку потрібно надіслати повідомлення своєму боту, потім скористатися посиланням, щоб подивитися що там:

Для цього треба використовувати метод `getUpdates`:
```html
https://api.telegram.org/bot5294223061:AAEGqg38dtOogYYAYitmUxN48bgHCMtBm_U/getUpdates
```
Отримаємо подібну відповідь:
```json
{
  "ok": true,
  "result": [
    {
      "update_id": 226331964,
      "message": {
        "message_id": 69,
        "from": {
          "id": 564108458,
          "is_bot": false,
          "first_name": "Serhiy",
          "last_name": "KRUKOWSKI",
          "username": "sergias88",
          "language_code": "uk"
        },
        "chat": {
          "id": 564108458,
          "first_name": "Serhiy",
          "last_name": "KRUKOWSKI",
          "username": "sergias88",
          "type": "private"
        },
        "date": 1661932325,
        "text": "ttt"
      }
    }
  ]
}
```
Тепер ми знаємо `chatId`, і можемо відповісти через **POST** запит з командою `sendMessage`.

За цим посиланням...
```html
https://api.telegram.org/bot5294223061:AAEGqg38dtOogYYAYitmUxN48bgHCMtBm_U/sendMessage
```
...передати відповідь у форматі JSON:
```json
{
  "chat_id": 564108458,
  "text": "this is an answer"
}
```
...відповідь сервера після повідомлення:
```json
{
    "ok": true,
    "result": {
        "message_id": 70,
        "from": {
            "id": 5294223061,
            "is_bot": true,
            "first_name": "tgTutorialBot",
            "username": "TelegaJavaTestBot"
        },
        "chat": {
            "id": 564108458,
            "first_name": "Serhiy",
            "last_name": "KRUKOWSKI",
            "username": "sergias88",
            "type": "private"
        },
        "date": 1661933346,
        "text": "this is an answer"
    }
}
```

## Bot settings
Для налаштування дефолтного повідомлення ідемо до `@BotFather` і набираємо команду `/setdescription`.

Для налаштування повідомлення в профілі бота ідемо до `@BotFather` і набираємо команду `/setabouttext`.

Для налаштування іконки в профілі бота ідемо до `@BotFather` і набираємо команду `/setuserpic`.




## WebHook and Long Pooling
> **Note**
> 
> There are two mutually exclusive ways of receiving updates for your bot - 
> the getUpdates method on one hand and webhooks on the other.
> Incoming updates are stored on the server until the bot receives them either way, 
> but they will not be kept longer than 24 hours.
> 
> [Source - DOCUMENTATION](https://core.telegram.org/bots/api#getting-updates)

* `getUpdates` - Use this method to receive incoming updates using long polling.
* `setWebhook` - Use this method to specify a URL and receive incoming updates via an outgoing webhook.

Головна фішка **вебхука** у тому, що він буде надавати відповідь тільки коли йому подали запит, 
а **long pooling** - буде постійно робити запити (наприклад кожні 30 секунд). 
**long pooling** вважається більш простішим, а **webhook** - більш дешевим з точки зору ресурсів.


### Клас TelegramLongPollingBot
Використовуючи клас `TelegramLongPollingBot` наш бот стає свого роду слухачем (listener), і коли він отримує повідомлення, 
то уся необхідна інфа прийде у об'єкті `Update` методу `onUpdateReceived`. 
У даному прикладі бот відповість лише на текстове повідомлення:
```java
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TestBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "TelegaJavaTestBot";
    }

    @Override
    public String getBotToken() {
        return "5294223061:AAEGqg38dtOogYYAYitmUxN48bgHCMtBm_U";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                try {
                    execute(SendMessage.builder()
                            .chatId(message.getChatId().toString())
                            .text("You sent: \n\n" + message.getText())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TestBot bot = new TestBot();
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```


## Як додати команди до свого бота

### Спосіб 1:
В `BotFather` пишемо команду `/setcommands` і вибираємо нашого бота в форматі `command1 - Description`, наприклад:
`set_currency - Set original and target currency`.
Тепер ця команда є у нашого бота - `/set_currency`.

#### Приклад
Спочатку, в **BotFather** треба встановити цю команду - переходимо до нього і набираємо `/setcommands`.
Далі нам необхідно ввести команду або лист з різних команд за наданим прикладом:
```txt
command1 - Description
command2 - Another description
```
типу так:
```txt
set_currency - Set Original and Target Currency
```

Як розпізнати команду надіслану команду ботом, наприклад `/set_currency`? 
Для цього в повідомлені (`Update`) буде текст (`text`) і сутність (`entities`), окрім іншої інфи:
```json
"text": "/set_currency",
"entities": [
    {
        "offset": 0,
        "length": 13,
        "type": "bot_command"
    }
]
```
```java
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Optional;

public class TestBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "TelegaJavaTestBot";
    }

    @Override
    public String getBotToken() {
        return "5294223061:AAEGqg38dtOogYYAYitmUxN48bgHCMtBm_U";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleMessage(Message message) {
        // handle command
        if (message.hasText() && message.hasEntities()) {
            // search out command
            Optional<MessageEntity> commandEntity = message.getEntities().stream()
                    .filter(e -> "bot_command".equals(e.getType()))
                    .findFirst();
            // cut off all another info
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/set_currency":
                        try {
                            execute(
                                    SendMessage.builder()
                                            .text("Please choose Original and Target currencies")
                                            .chatId(message.getChatId().toString())
                                            .build());
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        TestBot bot = new TestBot();
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

***
### Спосіб 2:
Через створення команд наслідуючи клас `BotCommand` - [приклад ](https://github.com/SergiaS/t_telegrambot/blob/tg_test_bot/src/main/java/dev/sk/testbot/bot/commands/StartCommandHandler.java)

> **Пояснення по прикладу:** якщо юзер зробить запит `/start`, тоді бот відповість тим що вказано в методі `execute`.
> Також всі команди, як от [IBotCommand](https://github.com/SergiaS/t_telegrambot/blob/tg_test_bot/src/main/java/dev/sk/testbot/bot/commands/BaseTextCommand.java)
> треба зареєструвати через [InitializingBean](https://github.com/SergiaS/t_telegrambot/blob/tg_test_bot/src/main/java/dev/sk/testbot/bot/configuration/TelegramBotCommandInitializer.java),
> де `IBotCommand... botCommands` приймає усі створені нами команди.

Тут насправді є дві реалізації:

<details>
<summary>Використовуючи @EventListener({ContextRefreshedEvent.class})</summary>

* [ПРИКЛАД](https://github.com/DmitrijsFinaskins/simple-telegram-bot/blob/main/src/main/java/io/proj3ct/SpringDemoBot/config/BotInitializer.java)
```java
@Component
public class TelegramBotCommandInitializer implements InitializingBean {
  private final ICommandRegistry commandBot;
  private final IBotCommand[] botCommands;

  public TelegramBotCommandInitializer(ICommandRegistry commandBot, IBotCommand... botCommands) {
    this.commandBot = commandBot;
    this.botCommands = botCommands;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.commandBot.registerAll(botCommands);
  }
}
```
</details>


<details>
<summary>Використовуючи InitializingBean</summary>

```java
@Component
public class TelegramBotCommandInitializer implements InitializingBean {
  private final ICommandRegistry commandBot;
  private final IBotCommand[] botCommands;

  public TelegramBotCommandInitializer(ICommandRegistry commandBot, IBotCommand... botCommands) {
    this.commandBot = commandBot;
    this.botCommands = botCommands;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.commandBot.registerAll(botCommands);
  }
}
```
</details>


> Вибір між @EventListener({ContextRefreshedEvent.class}) та InitializingBean залежить від того, коли вам потрібно зареєструвати команди бота.
> 
> Якщо вам потрібно зареєструвати команди одразу після того, як всі залежності бота були встановлені, ви можете використовувати InitializingBean. 
> Це корисно, якщо команди бота не залежать від інших бінів або якщо ви хочете зареєструвати команди якомога швидше.
> 
> З іншого боку, якщо вам потрібно зареєструвати команди після того, як всі біни були створені та ініціалізовані, ви можете використовувати @EventListener({ContextRefreshedEvent.class}). 
> Це корисно, якщо команди бота залежать від інших бінів, які можуть бути ініціалізовані пізніше.
> 
> У вашому випадку, якщо команди бота залежать від інших бінів, я б рекомендував використовувати @EventListener({ContextRefreshedEvent.class}). 
> Якщо команди бота не залежать від інших бінів, ви можете використовувати InitializingBean. Обидва підходи дозволять вам автоматично зареєструвати команди бота після ініціалізації.
> 
> **SOURCE: Bing/ChatGPT**

***
### Спосіб 3:
Найпростіший варіант просто перевіряючи строку. Наприклад: `if(message.getText().equals("/get_data"))`. 


## MessageSender
Дозволяє відправляти будь-яку кількість повідомлень на будь-якому етапі.

### Add HTML to message
Щоб повідомлення було в якості HTML, додаємо `.parseMode("HTML")`.

На смартфоні, текст який обернений у тег `<code>`, при натиску буде автоматично скопійований.

```java
SendMessage testMsg = SendMessage.builder()
        .text("""
              <b>Bold</b> 
              <i>Italic</i> 
              <code>mono</code> 
              <a href=\"google.com\">Google</a>
            """)
        .parseMode("HTML")
        .chatId(message.getChatId())
        .build();
```


## MarkUp
Телеграм має два типи клавіатур: **Reply Markup** та **Inline Markup**.

* Reply Markup - відправляє тільки текст (кнопки-заготовки).
* Inline Markup - під капотом відправляє якісь дані.

### ReplyKeyboardMarkup

* `setResizeKeyboard(true)` - метод розтягує клавіатуру по ширині екрану телефона/монітора;
* `setOneTimeKeyboard(true)` - метод ховає клавіатуру після натискання (на практиці не дуже працює);

## SendMessage

### MessageEntity
Приклад відправки повідомлення з хештегом
```java
SendMessage sendMessage = SendMessage.builder()
    .chatId(userProfile.getId())
    .text("Here are some hashtags: #новости #спорт #погода")
    .entities(List.of(
        MessageEntity.builder()
            .type("hashtag")
            .text("#news")
            .offset(0)
            .length(7)
            .build(),
        MessageEntity.builder()
            .type("hashtag")
            .text("#sport")
            .offset(8)
            .length(5)
            .build(),
        MessageEntity.builder()
            .type("hashtag")
            .text("#weather")
            .offset(13)
            .length(6)
            .build()
    ))
    .build();

```