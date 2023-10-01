# Visual Studio Code (VSC)
> Від **Microsoft** є програми **Visual Studio Code** та IDE **Visual Studio 2022 Community**.

## Hot key
`CTRL+F` - форматування коду.
`CTRL+~` - відкриває вікно терміналу.
`CTRL+SHIFT+SPACE` - показує доступні сигнатури методів (варіанти перевантаженого метода).



## Settings:

Встановлюємо [Code Runner](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner) та налаштовуємо:

Вибираємо вкладку **View** > **CommandPalette**:
* `File: Toggle AutoSave` - керує автозберіганням.
* `View: Toggle Minimap` - керує мінімапою.
* `Preferences: Open Settings (JSON)` - треба додати строку:
  * `"code-runner.showExecutionMessage": false` - вона прибире зайву інфу в терміналі при виводі;
  * `"code-runner.clearPreviousOutput": true` - прибирає увесь минулий вивід в терміналі;
  * [`"editor.mouseWheelZoom": true`](https://stackoverflow.com/a/38360205) - дозволяє **ZoomIn/ZoomOut** код комбінацією `CTRL+WHEEL_SCROLL UP/DOWN` 


## How to change theme colors
> Всі необхідні поля для зміни потрібно прописувати у файлі `settings.json`.

> Великий список полів є на офі [Theme Color](https://code.visualstudio.com/api/references/theme-color)