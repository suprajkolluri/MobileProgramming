Instructions
==============
1. Create a new database moviedb
      sqlite3 movielibrarydb.db
2. To populate the database from the file initmoviedb.sql
       sqlite>.read initmoviedb.sql
	   The first time you execute this it will give errors as sqlite3
       tries to drop tables that don't exist yet.
