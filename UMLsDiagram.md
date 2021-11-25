
## Class Diagram
![BattleshipUML-Page-1 drawio (2)](https://user-images.githubusercontent.com/47299145/142631684-8e71e843-a1a0-4976-8560-ddc3861b15ce.png)

## Sequence Diagram
The Application class is the entry point of the BattleShip Game. 
The main method in this class will use Java SwingUtilities to invoke a new Window class which uses the GUI. 
The window class uses a GUI to display four buttons, which are host game, join game, settings, and exit.
This sequence diagram illustrates the more commonly used features, like hosting and joining a game. 
If a player clicks host game, they will be prompted with a new ServerDialog GUI class. 
If the player clicks join game, they will be prompted with the ClientDialog class which allows the player to enter an IP address and a port number to connect to and play the game.


Once a player has chosen whether they want to host or join a game, they will be prompted with the ShipPlanner GUI class. 
This GUI class allows the player to place their ships on the board which they will use for the game. 
Players have the option of setting vertical ships, horizontal ships, or random placement of ships. 
Once all the ships have been placed, the user can click ok to start playing the game. 
There is an event listener attached to the ok button which will invoke the next step of the game, the GameBoard object.


The main GUI class, GameBoard is where the actual BattleShip game is played. 
There is a separate thread, NetworkConnection, that is created and used for communication between the server and the client. 
We bind a method in the GameBoard class to the thread in NetworkConnection, and every time data is received, this callback method is executed. 
There are also chat messages that can be sent and they are handled by the same network thread and callback method. 
Once the game is over, the players have the option of playing again, and in this case, the program will just call ShipPlanner to plan a new board and use the same network parameters for a TCP connection.


![BattleshipUML-Sequence-Diagram](https://github.com/CIS3296SoftwareDesignF21/prj-03-battleship_game/blob/sequence-diagram/battleship_sequence_diagram_v2.png)

## Game State Diagram
![BattleshipUML-Page-2 drawio](https://user-images.githubusercontent.com/47299145/142564601-65e02266-5398-46e6-b54d-4095b27a3ae8.png)