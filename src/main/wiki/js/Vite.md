# [Vite](https://vitejs.dev/guide/)

## Install
Встановлювати React краще з **Vite** - він працює набагато швидше за **WebPack**.

1. Встановлюємо командою `npm create vite@latest` і обираємо назву проєкту і на основі чого він буде (JS/TS).
2. Не забуваємо встановити залежності `npm install`.
3. Старт додатку за командою `npm run dev`.


## Міграція з **WebPack** на **Vite**:
> **Warning** Під час змін можливі глюки! Краще робити усе поступово, щоб бачити що Vite підхватує зміни, 
> коли будеш перетягувати файли проєкту на шаблоний код Vite.

1. Простіше за усе - створити проєкт **Vite** - `npm create vite@latest`
2. Перенести створені файли у корінь проєкту: `vite.config.js` та `index.html`.
3. Змінити `package.json`:
```json
// замінюємо
"scripts": {
    "dev": "vite",
    "build": "tsc && vite build",
    "preview": "vite preview"
},
// додаємо
"type": "module",
"devDependencies": {
    "@vitejs/plugin-react": "^4.0.0",
    "vite": "^4.3.9"
},
```
Головним файлом виступає `index.html` який приймає скрипт `index.jsx`.
Усі файли повинні бути `.jsx` або `.tsx`.


## [vite.config](https://vitejs.dev/config/)
Багато налаштувань для Vite є у цьому файлі.

### Робимо проксі:
```js
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})
```
