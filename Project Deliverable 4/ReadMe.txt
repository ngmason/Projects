README:

- Steps for creating the database (create table statements and dummy data insert statements):
	(Step 1) Located in the project folder is the updated CREATE SQL queries that provide instructions for UPDATE and DELETE, and also the INSERT statements from Deliverable 3.
	(Step 2) In order to build the database, run the CREATE and INSERT scripts in MySQL.
	(Step 2) Please make sure you have an instance of MySQL with the updated Project Deliverable 3 database entries running before running the application.

- Information on the environment for running the project and steps for executing (compile/deploy/run) your application:
	(Step 1) The MySQL driver used is found in the lib folder of this project.
	(Step 2) The javac command line that worked for us was running: cd <PATH TO>/Project Deliverable 4/src
	(Step 3) Then run: javac -cp "<PATH TO>\Project Deliverable 4\lib\mysql-connector-j-9.0.0.jar" *.java
		-Note: This should allow java virtual machine to compile both classes.
	(Step 4) After compilation, the command line command to run the program is: java -cp "<PATH TO>\Project Deliverable 4\lib\mysql-connector-j-9.0.0.jar" PetSalonUI.java <arguments>
		-Note: The arguments are the following: "<url>" "<username>" "<password>" com.mysql.cj.jdbc.Driver
		-Note: The driver Class name is com.mysql.cj.jdbc.Driver.
	(Step 5) After compiling, you should be able to run the menu with access to various options for adding, deleting, updating and searching for entries in the Pet Salon database.
	-Note: For the Update option, you can hit enter for any of the attributes you want to skip until you get to the one you want to update.

- The video URL:
	- https://asu.zoom.us/rec/share/Ko69VK6MRkCXfGvjpeTQQtu7WRg_XrkLJir2yKXYfd9QFYD4ejLdkdyL3iSNYuLk.68-rPR5bM8nYDlEr?startTime=1728497033000
	- Passcode: 3a!@F%zU
	- Note: There is an error in the presentation video showing the Relational Schema instead of the ER diagram. The only updates made to our ER diagram were adding the attribute ID to Customer and changing the primary key for Customer to ID.

- List of individual contributions of each team member:
	-Andrew Tellez: Collaboration on application design and wrote source code for application.
	-Anne Himes: Collaboration on application design and wrote source code for application.
	-Nina Mason: Collaboration on application design and put together ReadMe.txt.
	-Weronika Golden: Collaboration on application design and recorded presentation demonstration of application.

