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

## Як додати команди до свого бота
В `BotFather` пишемо команду `/setcommands` і вибираємо нашого бота в форматі `command1 - Description`, наприклад:
`set_currency - Set original and target currency`.
Тепер ця команда є у нашого бота - `/set_currency`.


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


## Клас TelegramLongPollingBot
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

### Додавання команд
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
