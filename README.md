# JavaRest
JavaRest, is an information system to manage orders, organize deliveries, and calculate profits, along with creating a customer-facing graphical interface and data storage capabilities. 

**Order Management**: The system allows for the creation and deletion of objects such as orders, dishes, and ingredients, with informative success or failure messages for each action.

**Query and Data Display**: The system can execute queries and display results in a user-friendly manner, enabling staff and customers to retrieve and view relevant information easily.

**Data Storage:** System data is saved using a serialized object, which is reloaded upon startup, ensuring continuity of operations and preserving the state of the system between sessions.

**Exception Handling:** The system incorporates robust error-handling, displaying appropriate error messages to users when issues arise, and uses custom exception classes to address specific scenarios.

**User Interface:** The UI includes various input methods like text fields and selection lists, making it easy for users to enter information. A login system authenticates users (Manager or Customer) and provides tailored functionality based on the user’s role.

**Manager Functions:** Managers can perform all administrative tasks, including adding, removing, and updating data, as well as querying all system information.

**Customer Functions:** Customers can view and manage their orders, update personal details, and access recommendations for dishes based on preferences.

**Persistent Storage and Navigation**: The system saves its state upon exit, ensuring that no data is lost, and offers easy navigation through internal windows for a smooth user experience.

This system ensures efficient order handling, data management, and smooth interaction for both restaurant staff and customers.




Please note:

**System Launch via Double Click:**
Before running the program, ensure that the folder "RESTAURANT" is specified in the CD path within the BATH file. This folder contains the necessary files for system operation: images, the SER file, and the JAR file. If the SER file does not exist, the program will start from scratch, and the file will be created upon exiting the program.

**Initial Login for Manager:**
The manager logs in using the username “m” with the same password.

**User Details:**
User details include the first name, last name, and their serial number in the system. A message displaying these details appears when a new user is created.
