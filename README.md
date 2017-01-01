SLogo
=====
**Team 13**  
Michael Schroeder  
Will Long  
Filip Mazurek  
James Marlotte

* Project Dates: Thursday, October 13 - Tuesday, November 1
* Hours
  * Michael - 60
  * Will - 60
  * Filip-40
  * James - 45
* Roles:
    * Michael Schroeder  
    Michael worked on the parsing of commands, the creation of the expression tree, and the abstract command hierarchies. Additionally, Michael worked on implementing some of the data storage classes, and the custom exceptions for command parsing. At the end, Michael worked on implementing all of the extension commands, and the tab to display the current palette for a workspace.
    * Filip Mazurek  
    Filip worked on the implementation of the Turtle class, as well as all of the basic command implementations. Filip also worked on the DataStorageManager, CommandStorage, VariableStorage, and Notifications classes. For the extensions, Filip implemented saving and loading workspaces using XML files.
    * Will Long  
    Will worked on designing the front-end, including the view classes and most of the controller classes. Will implemented some of the front-end extensions, including animation, clicking on turtles, multiple workspaces, and the debugger.
    * James Marlotte
    James worked on designing and developing the front-end. This included implementing some of the controller classes, many view class, most of the elements in the View and Help Menus, and almost all of the functionality pertaining to changing visual properties through the UI (e.g. language changing, turtle image changing, background changing, etc) .
* Resources: 
  * JavaFX documentation was extremely helpful in this project, especially the various tutorials for creating specific elements (e.g. color pickers, tab views, list views). 
  * Documentation on how to use Java reflection, both from online resources and from Professor Duvall's examples, was used heavily.
  * Professor Duvall's animation code was used as a start point for our TurtleAnimator
* Starting the project: Run the AppLauncher class (located in controller package).
* Testing the project: Use the sample SLogo commands in the data folder, and use the XML files in the resources/xmlData folder.
* Data or resource files: The resources folder contains a few resource files that are extremely important to the program, the most important of which is the commandPaths properties file, which is what allows the ExpressionTree to create the commands using reflection.
* Using the program: We implemented many features that are hard to see at a first glance. First of all, you can click on turtles to change them from acitve to inactive. Second of all, if you double click on a custom command, you can simply enter values for its parameters, and press enter. This will execute the command with those values. You can double click variables in the stored variable window and enter a new value to set their new value. You can also click on the colors in the palette to change the background, and you can use the animation menu to speed up, slow down, pause, or step the animation. Within the File submenu, it is possible to switch between various workspaces, create and remove workspaces, load workspaces with default settings (try Dracula), or load/save variables and commands into/from the current workspace.
