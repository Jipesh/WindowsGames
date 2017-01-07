# WindowsGames

#### Current Projects
- Arrow Game (realesed)
- Bomberman (under-development)
- PingPong (realesed)
- GameEngine2D (under-development)

## [Project: Arrow Game](https://github.com/Jipesh/WindowsGames/tree/master/Arrow%20Game)

_An action game, the aim is to survive as long as you can by avoiding the falling squares. The velocity of the squares increases over time_

#### Instructions
_Your aim is to dodge as many squares as you can, for each square that reaches the bottom of the game screen you earn 2 points_

#### Arrow Game Controls

- Left arrow - _move left_
- Right arrow - _move right_

#### Arrow Game Screenshots


![gameplay_screenshot] (https://github.com/Jipesh/WindowsGames/raw/master/Arrow%20Game/screenshot/Arrow%20Game.png) 
![test] (https://github.com/Jipesh/WindowsGames/raw/master/Arrow%20Game/screenshot/gameover.png)

## <a href="https://github.com/Jipesh/WindowsGames/tree/master/PingPong">Project: PingPong</a>

#### Instructions
_A simple ping pong game where you can either play **player vs player**, **player vs computer** or watch two computer play against each other_

#### Ping Pong Controls

##### Player 1

- W - <i>move up</i>
- S - <i>move down</i>

##### Player 2

- Up arrow - <i>move up</i>
- Down arrow - <i>move down</i>

#### Game Controls

- D - <i>dark theme</i>
- L - <i>light theme</i>
- P - <i>pause</i>

#### Ping Pong Screenshots


![main_menu_screenshot] (https://github.com/Jipesh/WindowsGames/raw/master/PingPong/screenshot/main_menu.png)
![dark_theme_screenshot] (https://github.com/Jipesh/WindowsGames/raw/master/PingPong/screenshot/dark_theme.png)
![light_theme_screenshot] (https://github.com/Jipesh/WindowsGames/raw/master/PingPong/screenshot/light_theme.png)

## [Project: Bomberman] (https://github.com/Jipesh/WindowsGames/tree/master/Bomberman)

_A two player bomberman game, the game is a remake of the modern bomberman games, where you have the ability to pick up Power-Ups to upgrade your current skills and your aim is to be the last man standing. The game uses multi threading therefore can also be translated to a server sided game with ease._

[game controls] (https://github.com/Jipesh/WindowsGames/blob/master/Bomberman/README)

#### Bomberman Screenshots

![bomberman_screenshot] (https://github.com/Jipesh/WindowsGames/raw/master/Bomberman/screenshot/bomberman.png)
![action_gameplay_screenshot] (https://github.com/Jipesh/WindowsGames/raw/master/Bomberman/screenshot/action_gameplay.png)

## [Project: GameEngine2D] (https://github.com/Jipesh/WindowsGames/tree/master/GameEngine2D)

_A simple game engine which can be used to make 2D games easily_

[change log] (https://github.com/Jipesh/WindowsGames/blob/master/GameEngine2D/CHANGE_LOG)

## How to setup on eclipse

1. make a new project
2. open git shell on the directory e.g `cd C:\Users\<username>\workspace\<eclipse project>`
3. clone repository `git clone https://github.com/Jipesh/WindowsGames.git`
4. open eclipse and refresh the project
5. navigate to one of the project e.g. `Bomberman` right click on it's src folder
6. click on Build Path -> Use as Source Folder

![screenshot for "6"] (https://s24.postimg.org/xzf7jffg5/Screenshot_from_2017_01_07_17_47_17.png)

#### Setup GameEngine for source code

1. right click on the project and click on properties
2. Java Build Path -> Libraries -> Add Jar
3. navigate to the lib folder of the project: `<eclipseproject> -> WindowsGames -> <project> -> lib -> <latest game engine>.jar`
4. click apply and ok
5. Now the project should be clean with no errors

![screenshot for "3"] (https://s27.postimg.org/n5cgviagj/Screenshot_from_2017_01_07_17_50_22.png)
