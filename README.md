
Project Management App

login details
username: mmontef
pass: 123

If you are having errors due to missing libraries, go to "build path" and make sure that the following libraries are linked:

jgrapht-ext-0.9.2-uber

sqlite-jdbc-3.8.11.2


******
Note the following folder structure:

driver: Contains the main driver ClientLauncher.java and another driver we used for testing (currently commented out)

graph_components: Contains components used to draw a physical graph. As per the majority decision during Tuesday's meeting, we are no longer drawing a physical graph and will be saving this for the next iteration. 

grapview_components: Contains various classes used to make up the GUI

listview_components: Same as above. We will consolidate these packages in the final iteration.

resources: Contains the main objects used for the application. Users, Projects and Activities. See below for more info.

saver_loader: Contains methods for saving and loading from the database.

swing_Components: Contains swing classes used to make GUI objects.
******

Where we currently stand:

As per Tuesday's meeting and the majority's decision, we have scrapped the physical graph representation of project activities in lieu of a table-like view.

Currently the database is set up correctly and loading from the database is working as it should.

Display the data in the GUI is something we are working on now.

Currently, the user that logs in will have all projects associated with their userID displayed. Upon clicking any project, a table with Activity names, durations and interdependencies is displayed. Right now they are displaying twice, which is something we are working on.

The user is able to create new projects and have them added to the list by selecing new project from the menu bar. User is prompted to enter some project details.

We have created similar forms for adding/editing new activities, and editing projects. These will be integrated tomorrow. The user, upon choosing to create new activities, is given a list of all other activities in the project from which they can set inter-dependencies.

Saving to the database is almost completed.

All the data is being loaded and stored properly. The challenge is displaying it in the GUI. There are some errors we are working on. We had to modify a lot of things due to changing to a table view.


Here is a breakdown of the main object structure:

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




