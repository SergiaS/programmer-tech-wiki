# Visual Studio Code (VSC)


## Hot key
`CTRL+F` - форматування коду.
`CTRL+~` - відкриває вікно терміналу.


## Settings:

Встановлюємо [Code Runner](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner), і налаштовуваємо:

Вибираємо вкладку **View** > **CommandPalette**:
* `File: Toggle AutoSave` - керує автозберіганням.
* `View: Toggle Minimap` - керує мінімапою.
* `Preferences: Open Settings (JSON)` - треба додати строку:
  * `"code-runner.showExecutionMessage": false` - вона прибире зайву інфу в терміналі при виводі;
  * `"code-runner.clearPreviousOutput": true` - прибирає увесь минулий вивід в терміналі;
  * [`"editor.mouseWheelZoom": true`](https://stackoverflow.com/a/38360205) - дозволяє **ZoomIn/ZoomOut** код комбінацією `CTRL+WHEEL_SCROLL UP/DOWN` 



