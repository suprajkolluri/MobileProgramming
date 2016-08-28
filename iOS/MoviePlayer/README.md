# iOS Movie Library and Player

The iOS Movie Library app is recasted to work with a jsonRPC server and media streamer. 

The new versions of the app uses its internal database and add to that database any movies that are available through the JsonRPC server. 

Movies that are available throught the RPC server will have an additional Json attribute/key which indicates the filename of the media file. 

Doing an http request to the streamer with the filename will cause the file to be streamed to the Media Player of the mobile device. 

The app uses the platform suppled media player to play in a new scene/view the media file. 

Thus the app will be able to search the OMDB API, add new movie descriptions, and obtain/save JsonRPC movie descriptions, display movie information, and where available, play movies for which the streamer has a media file.