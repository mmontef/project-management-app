
Project Management App

login details

username: mmontef
pass: 123

Alternate login:

username: davedm
pass: abc

This program includes the following libraries. They should be added automatically but if there are any errors, you can find them in the working directory.

jgrapht-ext-0.9.2-uber

sqlite-jdbc-3.8.11.2


******

Note the following folder structure:

driver: Contains the main driver ClientLauncher.java 

graph_components: Contains components used to draw a physical graph. Not currently used in this iteration.

grapview_components: Contains various classes used to make up the GUI. Includes some components used to draw a physical graph.
These are not used in the current iteration.

listview_components: Similarly to above, this class contains GUI components used to draw the Projects and Activities lists.

resources: Contains the main objects used for the application. Users, Projects and Activities. See below for more info.

saver_loader: Contains class used for saving/loading/editing the database, as well as holding the current instance of the database when the project is loaded.

******

Current Functionalities:

The program allows a user to log in, and have all projects associated with this user displayed.

The only user type currently supported is a MANAGER, and each Manager can have a number of Projects associated to them.

Projects only support having a single manager associated with them.

Projects are displayed in a list, and upon clicking a Project, a list of Activities associated with the project is displayed.

The user is able to Create new Projects from the menu bar. 

Upon clicking a Project, the user can choose to Edit a Project from the menu bar, which will display editable fields containing Project attributes. From this screen, the user also has an option to delete the Project completely.

Changes made are saved automatically to the Database. When a Project is Deleted, all Activities associated with the Project are deleted as well.

Similarly, upon clicking a Project, the user is able to then Add new Activities to the Project from the menu bar. A form appears with editable text boxes to set the Activity attributes.

When adding new Activities, the user can set Dependencies from a populated list of other Activities. The user can select no dependencies, or can select mutliple dependencies by holding SHIFT and clicking multiple options in the list.

Upon clicking an Activity in the list, the user is able to Edit an Activity from the menu bar. This will display the current values associated with this Activity, and can be changed. The user also has an option to delete the Activity entirely.

Changes made are reflected immediately in the database. If an Activity is deleted, all dependency associations that involve this Activity are deleted as well.

All additions, changes and deletions are made displayed immediately in the list.

******

Here is a breakdown of the main object structure:

NOTE: The following classes all contain Javadoc documentation that detail the functionality of all methods.

Users

For now we have one type of user: a Manager.
Users have a userName, firstName, lastName, password, ID and type (an enum, either ADMIN, MANAGER or MEMBER).
The primary key in the database for users is ID.

Activities

Activities have an id, label, description, duration, xpos, ypos, earliestStart, earliestFinish, lastestStart, lastestFinish, activityFloat and maxDuration. All attributes after duration are not used in this iteration, but we kept them in for future use.
Primary key in the database for activities is ID.
They are linked to Projects in the database through a project_activities_relationship table, where each activity ID is in a tuple with a Project ID.
Activity dependencies are set through a similar relationship table, that contains IDs of other activities. A tuple in this table (X,Y) means Activity X preceeds Activity Y.
Activity ID's are set by automatically incrementing a static variable upon creation of a new Activity object.
This variable is set to the current max Activity ID stored in the database, to ensure it is always a new number.

Projects

This class is where all the magic happens
Projects have an ID, projectName, ArrayList of users, date, Connected Directed Graph of Activities, an ArrayList of Activities, managerID, budget and description.
Projects hold a DefaultDirectedGraph of Activities connected by DefaultEdges. This structure holds all the Activities associated with a project, as well as the relationships between each activity.
There are a number of methods to add Activities, add Edges between them, and navigate the graph. Some examples including getActivitySet(), getArrowSet(), getIncomingArraysOfActivity(A), getOutgoingArrowsOfActivity(A), getActivityBefore(Edge), getActivityAfter(Edge), which are all pretty self explanatory I think.
The arrayList of Activities is used in parallel to be able to modify attributes like ES, EF, etc... There is currently an algorithm the correctly calculates all attributes in the forward pass of the activity graph, although it is not needed for this iteration.
It's not the most efficient method but it works well, since we can't pass by reference and modify the activities directly in the graph. The alternativve is to make an iterator and do a traversal to find the nodes we want to modify.

The Graph is created using jGraphT, a free 3rd party library for making graphs. It gives us all the methods for add/removing Vertices and Edges, and much more.




