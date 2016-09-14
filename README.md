# MazeSolver
CS 210 Mase Solver Project

##Packages
Right now there are two packages.
* Graphics
* GUI
    * Maze
    * Windows
* MazeLogic

These packages contain classes for their respective purposes. The graphics package
contains no code, its simply holds all images needed to compose the map and the application
icon.The GUI package contains 
all of the classes relevant to the GUI (Main, Controller, and the GUI.fxml, etc.). It 
also contains sub packages for some popup windows and special classes needed for drawing
the maze.The MazeLogic package contains the solving algorithm and any data type that is 
needed to do so. 

Why is it organized like this and not just all placed in one package? Because this follows
the scheme set forth by the Maven build system for organization purpose.


## Branches
Right now there are two branches.
* master
* dev

Dev is obviously for developement code, master if for stable static code. That way we can
just pull a clean code from master in the event that we really screw things up.

## Current Issues
As of right now the breadCrumbs method of the MazeSolver Class is breaking 
part of the GUI. Map levels are being over written with  other levels. This does not effect the logic
however, so the program will effectively solve the maze. The GUI is just having issues representing this properly when we are forced to pop back to the 
next poin that can be explored.
