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
+ Download the BattleShip.jar file from the releases tab.
+ Once downloaded, navigate to where the file is saved and double click it to run.
+ When the game menu is displayed, click "host game" to begin a game.
+ Then to join a game, you can either use the same BattleShip instance, or run another instance of the BattleShip and select "join game".
+ The correct IP and port will have to be supplied. (Online Only)

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

## John Glatts

Lauren is a 31 year old nurse who works the night shift at the Temple
University Hospital in Philadelphia Pa. She graduated with a nursing degree from
La Salle University and enjoys playing videogames in her free time. Often times, when
she is done her shift at the hospiatal, she is too tired to play high-effort games.
She enjoys playing BattleShip since it is easy to use and does not take long to play.
She enjoys playing with others on the internet, but often finds herself
playing solo because of her hectic work schedule. She finds hers technical ability
to be average and can navigate the BattleShip UI very easily. She likes the simple controls
and ability to play with her friends over the internet. However, since the game uses networking,
she only recommends this game to her friends that she thinks are technologically competent.

## Juan-Carlo Villamor Mercado

Tanaka is a 21 year old gaming enthusiast and gamer who works at a PNC Bank's IT department. He enjoys playing video games regularly but is unable to play the games he plays at home due to his job. Since he enjoys multiplayer games online, he decides he can play the Battleship game with his coworkers locally and if need be online with others. Tanaka is interested in this version of battleship because of the different features implemented. Such features are power ups, the auto placement for the ships, as well as the online multiplayer aspect. Compared to the traditional version of Battleship, he prefers to play this version because each game is also very short due to the power ups. Due to the power ups, it can be an easy win or easy loss which enables him to play with another opponent faster than the traditional battleship game. He is able to navigate the Battleship UI very easily because of his technological background and is able to understand the game with the added featuress.

## John Crane

Amanda, age 33, is an elementary school teacher at Jarrettown Elementary School
in Upper Dublin, PA. She graduated from Millersville University. She’s been teaching fifth
grade for just over 3 years now and she absolutely loves her job and her students.
She also has a two year old daughter and a five year old son, so she's no stranger
to working with little kids. In fact, she's so good with her students that she’s
essentially solidified her status as the cool teacher.
grade for just over 3 years now and she absolutely loves her job and her students.
She also has a two year old daughter and a five year old son, so she's no stranger
to working with little kids. In fact, she's so good with her students that she’s
essentially solidified her status as the cool teacher.

During the rainy autumn months, recess for the students is often held indoors.
While Amanda likes to watch her students play amongst themselves in the classroom,
it's clear that some of them get pretty fidgety during indoor recess since
there are only a few board games they can play with. Amanda is thinking about
introducing a virtual BattlesShip game to her students since
they could play it on their school issued laptops. She's as confident with
technology as the typical millenial so she should be able to get the kids setup
with the game fairly easily. Some of the students play computer games at home as well, so Amanda
thinks this could be a great way to keep the kids occupied. Ideally for her, the
game would be easy enough for the students to play on their own so she can
enjoy a break while the kids have indoor recess.

## Felix Chen
Raymond Scott is a 34 years old port crane operator who works at the port of Philadelphia. Ever since he was a kid, he enjoyed playing all kind of board gam with his family, especially battleship. Now he is a enthusiastic gamer, and he still very much enjoys battleship. During his break he will play the battleship game with his co-op friend. He can also play the battleship game over the weekend with friends over the internet. He thinks the battleship UI is very clean and simple, and easy to navigate.

## Dat Nguyen
![Elon Fisher (Persona)-1](https://user-images.githubusercontent.com/47299145/141406497-40dfe06e-bcce-4e53-860c-2cfa158998bd.png)

## Week 1 Tasks
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/Week1.md)

## Week 2 Tasks
[Click Here](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/Week2.md)

## Class Diagram
![BattleshipUML-Page-1 drawio (1)](https://user-images.githubusercontent.com/47299145/142564180-52e90d5e-b324-4f4c-8c3c-de45eed01415.png)

## Sequence Diagram 
![BattleshipUML-Sequence-Diagram](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/main/battleship_sequence_diagram_v2.png)

## Game State Diagram
![BattleshipUML-Page-2 drawio](https://user-images.githubusercontent.com/47299145/142564601-65e02266-5398-46e6-b54d-4095b27a3ae8.png)



