# RSS
RSS is dialect of XML.

## [RSS Specification](https://validator.w3.org/feed/docs/rss2.html)
**Required channel elements:**
* title
* link
* description


**Optional channel elements:**
* language
* copyright
* managingEditor
* webMaster
* pubDate
* lastBuildDate
* category
* generator
* docs
* cloud
* ttl
* image
* textInput
* skipHours
* skipDays

## [CDATA в XML](https://ru.wikipedia.org/wiki/CDATA)
В XML документах фрагмент, помещённый внутрь CDATA, — это часть содержания элемента, которая помечена для парсера как содержащая только символьные данные, а не разметку.

Раздел CDATA начинается со следующей последовательности символов:

`<![CDATA[`

и заканчивается с первым появлением последовательности:

`]]>`

Все символы, заключённые между этими двумя последовательностями, интерпретируются как символы, а не как разметка или ссылки на объект.


