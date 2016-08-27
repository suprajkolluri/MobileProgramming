# Getting Started with Android development

This is a single view (scene) app for Swift/iOS. The app displays various details of a Movie. The MovieDescription class acts as a container for the information returned from the Open Movie Database API. The Json representation returned by the database acts as the basis for information to be contained in the class definition. For example with the search http://www.omdbapi.com/?t=Frozen&y=&plot=short&r=json. The following fields are displayed in the app: Title, Year, Rated, Released, Runtime, Genre, Actors, and Plot.

The class includes a constructor that builds a MovieDescription object from a string of the Json form returned by the Open Movie Database API. It also includes a toJsonString method that returns a string of the Json in the same (maybe incomplete) format.
