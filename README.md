# ThoughtControlledDrawing
Summer 2018 QUIP-RS research project at Quinnipiac University.

------Getting Started---------------
1.) The Idek file and jna.jar file are needed to run the program
Download from: https://github.com/Emotiv/community-sdk

2.) Import the ThoughtControlledDrawing project folder into EclipseIDE

3.) An Emotiv account is needed. Go to: https://www.emotiv.com/

4.) Download the Emotiv Control Panel and train the LIFT, DROP, LEFT, and RIGHT mental commands

5.) Go into the thoughtReader package and open the CommandReader.java class.
	On lines 47 through 49, change the userName and password strings to your Emotiv log in.
	The profileName is for profiles made using the Emotiv Control Panel

6.) Run the CommandReader.java class(it will take a few moments to start up and connect)

7.) Run the Main.java class in the imageEditor package to start the simple image editor program

8.) If correctly installed, the program will be able to recognize(with some inaccuracy) the commands 
that will control the mouse cursor.
