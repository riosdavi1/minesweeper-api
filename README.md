# Minesweeper-API

## REQUIREMENTS

See: https://github.com/deviget/minesweeper-API/blob/master/README.md

## RUNNING INSTANCE

Try the API (see sample requests below): https://spring-boot-minesweeper-api.herokuapp.com/minesweeper

Try the Angular client: https://angular-minesweeper-app.herokuapp.com/

## ASUMPTIONS

1. DB-based, single role, user registration.
2. Token based user authentication.
3. ~~For new games, the API will initialize each cell of the game board with one of: mined / covered / number of adjacent mines.~~ The game will be initialized in the client. The API will only provide CRUD operations.
4. New games will be associated to the authenticated user.
5. Timer and game board will be updated on client side, possibly allowing the user to pause the game.
6. A game will have one of three possible statuses: IN_GAME, LOST, WINNED.
7. ~~The API will allow to update the following properties of a game: timer, mines left, status, game board.~~ The API will provide a single POST endpoint to create/update games.
8. The API will allow to retrieve an individual game, or all the games, as long as the current user is the owner.
9. The API will support removal of an individual game, or all of them, as long as the current user is the owner.
10. The web application will require registration and login.
11. After login, the home page will show the list of saved games.
12. For each game, the user will have the option to play it, or delete it.
13. Below the list, the user will have the option to create a new game, or delete all games.
14. In the game board, the user will have the option to save the game, or reset it.


## GAMES RULES

See: https://en.wikipedia.org/wiki/Microsoft_Minesweeper#Gameplay

## NOTES

1. The game board will be initialized in the web client as a two dimensional array of Cell objects (`Cell[][]`) for easy manipulation and rendering.
2. The API will convert the 2-dimension array board received in the DTO object, to a list of rows (`List<Row>`), where each row contains a list of cells (`List<Cell>`), to allow persistance in the database.
3. When the game is recovered from the db, the oposite process will take place to return a two dimensional array in the DTO.
4. Since the rows' and cells' Ids will be lost in the conversion, current rows and cells will be removed from the game before processing the DTO array.
5. The API will be implemented in Spring-Boot.
6. The web client will be implemented in Angular.


## API SPECS

| Method | Path                    | Parameters                 | Description
|:------:|-------------------------|----------------------------|--------------------------------------------------------
| POST   | minesweeper/user/signup | username, email, password  | User registration.
| POST   | minesweeper/user/signin | username, password         | User login. Returns auth token.
| POST   | minesweeper/game        | game DTO                   | Create/Save a game.
| GET    | minesweeper/game        | --                         | Retrieve all games associated to current user.
| GET    | minesweeper/game/{id}   | game id                    | Retrieve an individual game associated to current user.
| DELETE | minesweeper/game        | --                         | Delete all games associated to current user.
| DELETE | minesweeper/game/{id}   | game id                    | Delete an individual game associated to current user.

## PERSISTENCE

### Database

Postgresql JDBC

### Entities & Models

User (Entity):
* Long id
* String email
* String username
* String password
* String role

GameStatus (Enum):
* IN_GAME
* WINNED
* LOST

Game (Entity):
* Long id
* String username
* Integer size
* Integer mines
* Integer remainingCells
* List<Row> rows
* Long timer
* GameStatus status
* LocalDateTime lastUpdated

Row (Entity):
* Long id
* Game game
* List<Cell> cells

Cell (Entity):
* Integer x
* Integer y
* String status
* Boolean mine
* Integer proximityMines


## SAMPLE REQUESTS

### Sign up

```bash
curl -H 'Content-type: application/json' -d '{ "username":"riosdavi", "email":"riosdavi@gmail.com", "password":"123456"}' 'http://localhost:8080/minesweeper/user/signup'
```

### Sign in

```bash
curl -H 'Content-type: application/json' -d '{ "username":"riosdavi", "password":"123456"}' 'http://localhost:8080/minesweeper/user/signin'
```

### Save game

```bash
curl -H 'Authorization: Bearer ...' -H 'Content-type: application/json' -d '{"size":10, "mines":5, "remainingCells":3, "status":"IN_GAME", "cells":[[0, 1, "open", false, ...], ...]}' 'http://localhost:8080/minesweeper/game'
```

### Get game with id 1

```bash
curl -H 'Authorization: Bearer ...' 'http://localhost:8080/minesweeper/game/1'
```

### Delete all games for current user

```bash
curl -XDELETE -H 'Authorization: Bearer ...' 'http://localhost:8080/minesweeper/game'
```
