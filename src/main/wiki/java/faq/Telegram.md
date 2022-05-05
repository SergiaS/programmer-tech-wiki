# Telegram
Here I note - how I can create a telegram bot.

* [Telegram Bot Java Library](https://github.com/rubenlagus/TelegramBots)
* [GitHub - Демонстрирует многих возможностей телеграмм бота](https://github.com/kobyzau/mjc-sandbox)
    || [YouTube - Телеграм-бот на Java протягом 1 години](https://www.youtube.com/watch?v=IX3B87hqF4Y)


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


## Як додати команди до свого бота
В `BotFather` пишемо команду `/setcommands` і вибираємо нашого бота в форматі `command1 - Description`, наприклад:
`set_currency - Set original and target currency`.
Тепер ця команда є у нашого бота - `/set_currency`.



