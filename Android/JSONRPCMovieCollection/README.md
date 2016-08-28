# JsonRPC Android App and Movie Collection Server

This application includes a JsonRPC server in Java that remotely manages a collection of movie descriptions. Refer to the readme.txt file for instructions for executing this example.

In addition to the Movie Collection JsonRPC server, an Android App is also included. The Android app uses the server created as its only data source for obtaining movie information. The application will not retain a local mirror of the movie collection within the app, and instead use the JsonRPC methods defined by the collection to obtain model information. AsyncTask approach is used to create the solution.