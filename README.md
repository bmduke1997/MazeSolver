# MazeSolver
CS 210 Mase Solver Project

##Packages
Right now there are two packages.
* GUI
* MazeClasses

These packages contain classes for their respective purposes. The GUI class contains 
all of the classes relevant to the GUI (Main, Controller, and the GUI.fxml). The MazeClasses
package will contain any additional classes needed. 

Why is it organized like this and not just all placed in one package? Because this follows
the scheme set forth by the Maven build system for organization purpose.

I do not know if we will need the MazeClasses package, if we don't need any additional classes
then we can delete it. But for now, its just a place holder.

## Branches
Right now there are two branches.
* master
* dev

Dev is obviously for developement code, master if for stable static code. That way we can
just pull a clean code from master in the event that we really screw things up.
