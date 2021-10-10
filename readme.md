Документация к игре __Поле Чудес__ через вызов REST-запросов:

Единстенный ендпоин `/move`, на котором игрок делает JSON-запрос вида:
```json
{
    "askedLetter":"n",
    "playerName":"Bob"
}
```
где:
* `askedLetter` - угадываемая буква
* `playerName` - игрок, пробрасывающий букву

после чего клиент получает JSON-ответ:
```json
{
    "targetWord": "cocaine",
    "guessedWord": "_ _ _ _ _ n _",
    "askedLettersSet": [
        "n"
    ],
    "players": [
        "Bob",
        "Carl",
        "Tina"
    ],
    "playerOnAir": "Bob",
    "playing": true
}
```
где:
* `targetWord` - слово, которое нужно угадать (всегда рандомное)
* `guessedWord` - статус слова, где "_" означает не угаданная буква
* `askedLettersSet` - список букв, проброшеные клиентами
* `players` - список играющих игроков
* `playerOnAir` - указывает игрока, который должен ходить
* `playing` - статус игры, где "true" - слово не удагодано, "false" - игра окончена.
