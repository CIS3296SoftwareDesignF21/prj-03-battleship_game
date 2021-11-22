# BattleShip 
A simple battleship game written in Java with a Graphical User Interface and optional online playing.


## Table of Contents
* [Technologies](#technologies)
* [Features](#features)
* [Status](#status)
* [Contributing](#contributing)

## Technologies

This project was initially built using:
+ Maven
+ JDK 14
+ JUnit 5 for testing
+ IntelliJ form creator for .form files ( GUI components )

## Features

+ Easily configure your player settings and distinguish yourself with a username and an avatar
+ Self-host your game and play with a friend*

*_When hosting the game you may be required to open the port or modify the firewall to play over the Internet_

## Status

This project is currently in early development

## Contributing

You're free to fork the repository and work on addition, improvements and bug fixing or [open an issue to leave a feedback or a suggestion.](https://github.com/Sokom141/BattleShip/issues)

When you're done open a pull request and discuss!

## ADDDED UPGRADE IMPROVEMENT PROPOSAL:

Upgraded Battleships Game:
https://github.com/Sokom141/BattleShip/issues
https://awesomeopensource.com/project/Sokom141/BattleShip

## Description:
Two players get connected through open port over internet or locally hosted port, a board is generated x by y square where players are given a set number of ships of random length. Placement of ships in any part inside the given board of their choice. Once both sides are ready, whoever is lucky to get the direct shot is able to make another hit, if the player misses, the next turn is their opponent. A hit or misses on the opponent board will mark the square, making it unavailable to be hit again. With that, the game ended when one of the players no longer had any ship alive.

## Educational Goals:

Meant to guide contributors to improve upon TDD method of development and especially groups development setting. With that, the project is played using networks hence multithreading will be part of the learning experience. TO which should not be a problem for CIS3296 students with 3207 previous experience.
The project also uses networking technology which is an extremely important and exciting  software topic that group members can work on.


## Technology:
+ Maven
+ JDK 14
+ JUnit 5 for testing
+ IntelliJ form creator for .form files ( GUI components )
+ Window 10
+ Github for version controls

## Proposed Contribution:

Project on implementing a new feature of power ups, single-player, clearing lines and setting up mines, scanning location of ships or defense against shots from an opponent on a square or lines. This improvement meant to improve upon basic trading on random squares in battleship games. Such power up will have a particular cost and implementation along with points earned, a maximum stack on power ups available for usage per game. As to balance out against continuous losses, losing players may receive bonus points earned per hit on opponents. This will guarantee fair play and fun experience for all participants on the new and improved battleship.
Players will also have the ability to play solo against the computer if they do not have a connection to the internet.

[Link to slide](https://tuprd-my.sharepoint.com/:p:/g/personal/tuk78874_temple_edu/EWLyduh_F0pLkRBBWUFylA4BhgY-E80-k729pD_YhWAOSA?e=6Frigg)

## How to Run

### *From JAR file*
+ The java version on your machine must be 14 or greater for this game to work. 
+ Download the BattleShip.jar file from the releases tab.
+ Navigate to where the file is saved and double click it to run.
+ When the game menu is displayed, click "host game" to begin a game.
+ Then to join a game, you you must run another instance of the BattleShip game and select "join game".
+ If you are connecting from a different machine, you must allow java programs through your firewall.
+ The correct IP and port will have to be supplied to join the game.

### *From your IDE*
+ Fire up your favorite ide and make sure you have java 14 installed on your machine.
+ If you are using Intellij, you will have to follow a few steps to get the code to compilesuccessfully.
+ From Intellij, hit File->Settings->Editor->GUI Designer.
+ Once this window is opened up, click the button that says generate GUI into java source code.
+ This will allow you to develop, test, and run the BattleShip program from your IDE.

### *Local Network Only*
The game works with no issues when using it on a local network.
More testing and development is to come with using the public internet to run the game.

## Vision Statement
This game is being developed for people of all ages who enjoy playing the classic game, BattleShip. People who
love to play battleship, but may not have the time to set up a classic board and play
in person, or who want to play with their friends over the internet, can all benefit from playing
this new and improved battleship game. The battleship game is a lightweight and fun
videogame that benefits players of all ages because of it's simplicity. It is also open source,
so enthusiasts of the game can go under-the-hood and see how the game operates. Unlink other BattlehShip games,
like BATTLESHIP - Multiplayer Game, which is $4.99, our game is completely free and tons of fun.
## Personas
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/Personas.md)

## Week 1 Tasks
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/Week1.md)

## Week 2 Tasks
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/Week2.md)

## Week 3 Tasks
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/Week3.md)

## UMLs Diagram
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/UMLsDiagram.md)



