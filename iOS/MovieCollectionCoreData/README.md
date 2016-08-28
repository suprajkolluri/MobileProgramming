# iOS Movie Collection with Core Data

Movie Library app has been recasted to work with iOS Core Data. 

The solution includes the schema for the movie persistent storage. The iOS app uses Core Data to persist movies. 

The app provides the ability to add new Movie Descriptions, remove existing Movie Descriptions, and to view/browse the movies that are stored in the app's core data. 

The app will also provide the ability to search Open Movie Database API. 

The Json representation returned by a successful search is used, providing the user the ability to add new descriptions into the apps database. For example with the search http://www.omdbapi.com/?t=Frozen&y=&plot=short&r=json returns the Json of an entry, whose details should be displayed by your app. 