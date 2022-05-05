# Git
* [Documentation](https://git-scm.com/doc)
* [BinaryStudio - Learning git (Level 3)](https://binary-studio-academy.github.io/stage-2/lectures/how-developers-work/)
* [Интерактивная обучалка](https://learngitbranching.js.org/)
    Here you can pass up to rampup4 level - **Just these concepts are enough to leverage 90%**.

Колись доведеться писати команди в консолі, і тут стає вибір - чим користуватися (Bash, cmd, PowerShell...)
Також консольні термінали використовують консольний текстовий редактор, наприклад, Vim.
Його можна змінити командою `git config --global core.editor "nano"`.


## GitHub
* [What is GitHub](https://guides.github.com/activities/hello-world/)
* [Mastering Markdown](https://guides.github.com/features/mastering-markdown/)

> To manage access and invite a collaborator go to **Settings — Manage access**.

> To set default branch Go to **Settings — Branches**.

> Різниця між `issue` і `task` - в тому, що **issue** це робота з кодом.

> Для автозакриття **issue** використовуй спец-слово `closes #` і номер **issue**.  


### [GitHub Pages](https://guides.github.com/features/pages/)
Разрешает размещать простые-сайт/веб-страницы, которые будут читать и отрисовывать браузеры.

Сначало нужно включить опцию GitHub Pages - указываем источник расположения страницы, настраиваем тут: **Settings — Pages**.
После чего GitHub создаст адрес, например:
```
Your site is published at https://sergias.github.io/resume/
```
Далее на главной странице своего репозитория нажимаем **Edit** (иконка шестеренки), и вставляем адрес своей страницы в поле **Website**.



## Git commands
> За замовчуванням `git` не пушить теги, тому ключ `--tags` корисний для того, щоб запушити теги, якщо вони є. 
> У випадку, якщо їх немає, можна просто використовувати `git push`.

> `git stash` аналог у **IntelliJ IDEA** `Shelve Changes...`.

> Можно ссылаться, относительно коммита или `HEAD`, к другим коммитам без знания их хеша.
> Переместиться на `1` коммит назад: `^` (на `2` назад: `^^`). Переместиться на `n` коммитов: `~n`.
> ```shell
> git checkout bugFix^ 
> git checkout HEAD~3 
> ```
> VISUALIZATION: [Relative Refs](https://learngitbranching.js.org/), type to console: `rampup2`

> While resetting works great for local branches on your own machine, its method of "rewriting history" doesn't work for remote branches that others are using.
> In order to reverse changes and share those reversed changes with others, we need to use `git revert`.

| command                           | description                                                                   |
|:------------------------------    |:----------------------------------------------------------------------------  |
| `git init`                        | Инициализация репозитория                                                     |
| `git status`                      | Статус коммитов, нахождение в ветке, инфа по файлам для коммита               |
| `git add .`                       | Добавить все файлы для коммита, где `.` - все файлы                           |
| `git restore <file>`              | Убрать файл с коммита                                                         |
| `git commit -m "init commit"`     | Создание коммита с именем "init commit", где `-m` - message                   |
| `git stash`                       | Якщо треба тимчасово запам'ятати "брудне" робоче дерево (тобто, зміни у файлах зроблені і збережені, але ще не коммітнуті)       |
| `git tag abracadabra`             | Добавление тега к коммиту       |
| `git remote add topjava https://github.com/SergiaS/topjava.git`     | Добавляет удаленный репозиторий             |
| `git remote`                      | Отображает имя удаленного репозитория                                         |
| `git pull`                        | Под капотом выполняются 2 команды: сначала `fetch` (получает все новые данные с удаленного сервера), а потом `merge` |
| `git branch`                      | Отображает список доступных веток                                             |
| `git branch testClass`            | Создает ветку testClass                                                       |
| `git checkout testClass`          | Переключается на ветку testClass                                              |
| `git checkout -b testClass`       | Создает и переключается на ветку testClass                                              |
| `git checkout test^`              | `^ `- ссылка/переключение на родителя ветки test                              |
| `git checkout HEAD~4`             | `~` - относительная ссылка, переход сразу на несколько уровней                |
| `git branch -f test HEAD~3`       | Перемещение ветки (`branch forcing`). Переместит (принудительно) ветку test на три родителя назад от HEAD    |
| `git merge personClassBranch`     | Слияние веток - объединяем текущую ветку (в которой находимся) с personClassBranch    |
| `git brancn -D personClassBranch` | Удаляет ветку personClassBranch                                               |
| `git push -f origin HEAD^:master` | Удаляет последний запушенный коммит                                           |
| `git reset`                       | Отменяет изменения, перенося ссылку на ветку назад, на более старый коммит. `reset` отлично работает на локальных ветках, в локальных репозиториях. Но этот метод переписывания истории не сработает на удалённых ветках, которые используют другие пользователи. |
| `git revert`                      | Отменяет изменения и делиться отменёнными изменениями с остальными            |
| `git fetch`                       | Получение изменений с сервера - новые коммиты, ветки...                       |
| `git log`                         | Отображает историю коммитов |
| `git show`                        | Отображает подробную инфу по коммиту |
| `git rebase -i HEAD~2`            | Позволяет изменить коммиты, даже удалить |
| `git rebase --abort`              | Виходить з гілки `Rebasing...` |


## Как восстановить коммиты которые откатил
Например, ты откатился на каком-то коммите - сделал hard reset, и все коммиты после этого исчезли.
И тебе нужно восстановить какой-то коммит.

С помощью `git reflog` можно посмотреть список изменений и их хешы. Далее мы просто откатываемся до какого-то действия, для этого нам нужен хеш этого действия.

Пишем команду `git reset --hard b888fe4` и будут восстановлены коммиты до конкретного действия.


## Пример удаления лишних коммитов
Например, исправление ошибки, которые можно сделать в одном коммите, а не пару последних.

Сначало на последнем коммите пишем:
```shell
git rebase --interactive HEAD~2
```

Открывается файл с двумя последними коммитами, второму ставим `squash`, сохраняем и закрываем.

Далее открывается `.txt` файл для изменения, где можно указать сообщения которое будет у финального коммита.
Также паралельно можно изменять данные в коде.


## Як внести зміни у файл старого commit'а
Пишемо команду в терміналі `git rebase -i --root`.

Якщо все добре git відкриє свій текстовий редактор для змін.

В консолі замість `pick` пишемо `e`, тобто edit.
Також вибираємо потрібний коміт командою типу `^O` (це `CTRL+O` - Write Out).

Далі можемо змінювати файли (гілка буде `Rebasing...`).

Всі зміни потрібно додати `git add .`. Якщо потрібно, перевіряємо статус - `git status`.

Після всіх змін `git rebase --continue` для продовження.

І знову в нашому редакторі тиснимо `^O` (це `CTRL+O` - Write Out).

Вийти з редактора `CTRL+X`.

