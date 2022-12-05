## Text-Based Dungeon Game

### Overview

This is a model of a 2-D dungeon, composed by caves and tunnels. It needs to represent different kinds of location, treasures, players, otyughs(monsters) and other attributes. I designed a hierachy sturcture and enumeration denotion to help construct this model.

This project is developed upon Project 3, adding several features to it, and providing a text-based console interface.

### Features
* Calculator. 
    There is a `GameCalculator` interface that does all the 'dirty' work of calculating, and setting the Players & DungeonMap interfaces free from tedious processes. All changes to the model (except initilization) should go through its implementation. In addition to the Project3, I added important actions into it like shooting an arrow or picking up arrows.
* Grid Generator
    To seperate the Kruskal Algorithm and other issues with the map design with its own methods, I introduced the `GridGenerator` interface to do these dirty work. It's extendable if you have new algorithm to do so. This generator will generate a list of important arrays, denoting the smell of otyughs, the steps taken to reach the start, and other key information.
* Clear Structure
    The structure has been re-designed so it's very clear and readable. You can tell that the controller really has no idea how the model is working.
* Good Extension. 
    In future, it's very easy to extend with other Calculators / Game implementations. Just implement new Classes and replace it in the Game Model. The controller will only consume the interfaces.
* Independent Random Helper
    All random processes are summed into the RandomHelper and easy for extending or modifying. I gave an example in the testing, where we have a fake random helper to make sure the testing goes smooth every where.

### How to run

* Run the driver:

    Right-click the `controller.DungeonGameDriver` and run the application. We've set the input & output in the console, so you can use the system in/out to interact with it. <br />

* Run the JAR file:

    If you have JRE installed on your environment, Double-Click the runnable jar`./res/p4-text-game.jar` is fine enough.<br/>
    Or if you havn't installed, then in the project root file's terminal, put in command
    `java -jar ./res/runnable/p4-text-game-runnable.jar` <br />
    *Note*: Don't delete the `./res/runnable/res` folder! It's critical.

### Useage

You are encouraged to import the public classes and discover the extendable features in it. After you initialize the controller with essential reader and writer, you can call the controller to do all the rest work. It will run and consume input and give output. <br />
The game will continue with abundant information hints given.<br />

### Examples

Because the game is complicated, we provided 3 example runs.
Under the folder : `./res/example/` is `p4run1.txt`, `p4run2.txt`, and `p4run3.txt`. <br />
The Example basically initiated the whole game with the attributes given by the user. Then we can started the game by walking around, and print the situations we met. we can conduct certain actions and the running result is then printed. <br />
The 3 examples have different focuses, the first one shows that we successfully hurt an otyugh, and then enter its cave, and survived for the first time, but got eaten the second time.<br />
The second one shows that we can shoot the otyugh through a curved tunnel and hurt it.<br />
The third one shows that we killed the otyugh and made it to the end destination, and won the game by end.<br />

### Design

One BIG change I made was I put many functions into the Grid Generator because it can keep information in a simple way. It will do the calculation of the 'smell' attributes and give it out in `O(1)` time, which is faster than putting the process in the calculator and do the `O(n^2)` work every time. What's more, it did not interfere with the Map model itself. By doing so, our DungeonMap implementations are seperated from the calculation work or caring about connectivity or smells, only maintaining the model datas.

### Assumptions

* Picking up treasures means that the player picking up all the treasures in the location.
* We pick up both the treasures and arrows together, you don't need to pick up twice.
* Player can pause at a tunnel.
* We have twice amount of locations with arrows against caves with treasures.

### Limitations

* If had more time, I would do more work on the arrows. It should use a visitor model or strategy pattern to provide its travel() method so we can have different types of arrows here. Currently we bend the crooked arrows into the calculator for simplicity.

### Citations

Ruidi Huang's representation of the map mentioned in the note : [piazzaLink](https://piazza.com/class/l7polvh6ntw4hj/post/52). I referred to this for my data representation when initializing the map with connectivity.