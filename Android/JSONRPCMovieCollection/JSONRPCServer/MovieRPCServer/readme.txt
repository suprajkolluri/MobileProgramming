Author: Supraj Kolluri (skollur1@asu.edu), ASU Polytechnic, CIDSE, SE
Version: March 2016

Purpose: Movie Description Json-RPC
This program is executable on both Mac OS X and Windows.

The project includes a movie collection service.
Communication between the service is done using JSON-RPC.


The movie collection service is deployed as a stand alone Java app.

To build and run the example, you will need to have Ant installed on
your system.

If you don't already have Ant, see:
http://ant.apache.org/

This assumes that you will use Ant from the command line to build
the client and service. The Ant build file includes targets to compile the
service.

Clean the classes folder by the following command
ant clean

Build the class files using the following command
ant build.java.server

run the server from the command line with the statement:

Windows -
java -cp classes;lib/json.jar edu.asu.msse.skollur1.server.MovieServer 8080

MAC OS X
java -cp classes:lib/json.jar edu.asu.msse.skollur1.server.MovieServer 8080

end

