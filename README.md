Introduction
The purpose of the Geocoder app is to provide an interactive platform for managing a dataset of geographical locations. The app is designed to convert raw latitude and longitude coordinates into human-readable addresses using geocoding techniques and to maintain these locations within a structured database. By leveraging the capabilities of Android Studio and SQLite, alongside geocoding services, the app addresses the need for efficient data handling and geographical information retrieval in a mobile context.

The scope of the project encompasses the creation of an initial database seeded with 50 pre-defined latitude and longitude pairs, the development of an Android application that can interact with this database, and the implementation of a geocoding feature to translate these pairs into physical addresses. Additionally, the app is equipped with a user interface that allows for searching, adding, updating, and deleting records within the database.

Technologically, the app is developed using Android Studio, the official integrated development environment (IDE) for Google's Android operating system, with Java as the primary programming language. The application utilizes the SQLite database for data persistence and employs Android’s Geocoder class to perform the geocoding operations. User interaction is facilitated through a simple and intuitive interface, ensuring that users can manage location data with minimal effort.

Design
The application consists of six activities, the corresponding XML files, as well as additional classes to help facilitate the functionality. The database is made using SQLite, and the geocoding is done with google API.

MainActivity: The main activity is the landing page of the app. Here the user can navigate to the other pages depending on what they want to do. The menu is just a simple set of buttons.
AddLocationActivity: In this activity the user can add a location to the database. The user will enter the latitude and longitude for the location into edit text fields. The user will then click the button to save the location. A confirmation message will be displayed as a toast message, regarding if the input was valid and if the location was saved.
EditLocationActivity: In this activity, the user will be able to edit an entry. Given the latitude and the longitude, the user will be able to edit the address associated with the location. This allows the user to correct any issues regarding the geocoding or allows the user to be precise with the address. The user can navigate to this page from the MainActivity or from the list view for all of the activities.
LocationListActivity: This activity will allow the user to list all of the locations in the database. They will be able to scroll through each entry. They will be able to select to edit or delete an entry. If they edit they will be sent to the edit location activity, if they delete the entry will be deleted.
LocationDetailsActivity: Here the user will be able to search an address in the database and be given the corresponding latitude and longitude.
Along with the activities there are other java files to help with the functionality of the app.
CsvFileReader: Opens a CSV file and reads in the data.
DatabaseHelper: Manages the database and other related operations
DatabaseTask: Task to run and create the database
Locations: contains the location class to hold the location as an object

