# [Мапа в JS - `new Map()`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map)
* [Приклад використання Мапи](https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/733533/Simple-javascript-solution)
* [Ітерація в мапі - StackOverFlow](https://stackoverflow.com/a/66890111)
  ```js
  const map = new Map([
    ['key one', 'value one'],
    ['key two', 'value two'],
  ]);
  
  // Loop through key-value-pairs
  Array.from(map.entries()).map(([key, val]) => console.log(key, val));
  
  // Loop through map keys
  Array.from(map.keys()).map(key => console.log(key));
  
  // Loop through values
  Array.from(map.values()).map(value => console.log(value));
  ```


> **Note!**<br>
> The Map object holds key-value pairs and remembers the original insertion order of the keys.

**Map**'а немає методу сортування.
Для сортування необхідно мапу обернути в масив, і на ньому викликати метод `sort`:
```js
let myJson = {
    "12": {
        "id": 12,
        "currencyRate": 0.1942,
        "currencyCode": "DZD",
    },
    "36": {
        "id": 36,
        "currencyRate": 19.2436,
        "currencyCode": "ZAC",
    },
    "50": {
        "id": 50,
        "currencyRate": 0.31132,
        "currencyCode": "BDT",
    },
    "51": {
        "id": 51,
        "currencyRate": 0.054972,
        "currencyCode": "AMD",
    }
};

let myMap = new Map([...Object.entries(myJson)].sort(([,a], [,b]) => {
    if (a["currencyCode"] > b["currencyCode"]) return 1;
    else if (a["currencyCode"] < b["currencyCode"]) return -1;
    return 0;
}));
```

***

Сортування полів за типом `string` слід робити через метод `localeCompare()`:
```jsx
<select>
  {Array.from(sortedMapByKey.entries(), ([key, value]) => {
    return <optgroup label={key}>
      {[...value]
        .sort((a, b) => a.generation.localeCompare(b.generation)) // 'generation' is a string type
        .map(car => <option key={car.id}>{car.generation} - {car.bodyType}</option>)
      }
    </optgroup>
  })}
</select>
```

***

Як дістати масив за певним ключем, відсортувати за певним полем і показати дані:
```js
let bikes = new Map();
bikes.set("road", [
  {
    "brand": "Wilier",
    "model": "Cento",
  },
  {
    "brand": "Trek",
    "model": "Madone",
  },
  {
    "brand": "Pinarello",
    "model": "Dogma",
  },
  {
    "brand": "Argon 18",
    "model": "Sum Pro",
  }]);
bikes.set("mtb", [
  {
    "brand": "Bergamont",
    "model": "Vitox",
  },
  {
    "brand": "Author",
    "model": "Sector",
  }]);

bikes.get("road")
  .sort((a, b) => a.brand.localeCompare(b.brand))
  .forEach(b => console.log(b));
```

***