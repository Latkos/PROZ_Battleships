# PROZ_Battleships

## ABOUT THE GAME
Battleships game for university PROZ classes

A 2-player battleships game using SQLite, JSON-Simple, JavaFX and JaSypt. 

It requires Java version 14 or higher.

Two players can play it on a local network.

## HOW  TO START

To start a game, one instance of Server.jar (in /Server/target directory) has to be started. Using Powershell/Linux terminal enter the Server/target directory and then type:

``` java -jar Server.jar ```

If you open it in Powershell, it will be easier to stop the server and you'll be able to view some server messages in case of errors.
If you do not open it in Powershell, the game will work, but the server will run in background.

Then each player needs to open an instance of Client.jar (in /Client/target directory) (again by Powershell or just by double clicking, it does not matter)

To play a next game you just need to open a next pair of Client jars.

The documentation is available in the /docs folder.

## SCREENSHOTS

### SAMPLE SHIP SETUP
![image](https://user-images.githubusercontent.com/48084189/119651735-9783d980-be25-11eb-96c9-c31d578cfc6b.png)

### GAME AFTER A FEW MOVES
![image](https://user-images.githubusercontent.com/48084189/119651929-cd28c280-be25-11eb-9255-0598c75da707.png)
