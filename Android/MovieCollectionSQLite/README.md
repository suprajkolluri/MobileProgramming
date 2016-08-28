# Android Movie Collection Database

The Android Movie Library app has been recasted to work with a SQLite database. The app includes a minimal database in the app bundle, which is used to initialize the app's database should it not already exist in internal storage. 

The app provides the ability to add new Movie Descriptions, and to remove Movie Descriptions. 

The app also provides the ability to search Open Movie Database API. 

The Json representation returned by a successful search is used, providing the user the ability to add new descriptions into the apps database. For example with the search http://www.omdbapi.com/?t=Frozen&y=&plot=short&r=json returns the Json of an entry, whose details will be displayed in the app. 