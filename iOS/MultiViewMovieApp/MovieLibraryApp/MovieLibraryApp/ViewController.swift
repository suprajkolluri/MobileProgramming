// Copyright 2016 Supraj Kolluri,
//
//
// The contents of the file can only be used for the purpose of grading and reviewing.
// The instructor and the University have the right to build and evaluate the
// software package for the purpose of determining the grade and program assessment.
//
//
// @author Supraj Kolluri mailto:supraj.kolluri@asu.edu
//         Software Engineering, CIDSE, IAFSE, ASU Poly
// @version Feb 22, 2016

//This is the controller class for the table view in the initial navigation page
//Using this class we update the movies in the table view, delete the movies and 
//navigate to Movie Details page as well the Add Movie page.

import UIKit

class ViewController: UITableViewController {
    var libraryVar = MovieLibrary()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.libraryVar.movieList.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCellWithIdentifier("Cell", forIndexPath: indexPath) as UITableViewCell
        
        var movie: MovieDescription
        movie = libraryVar.movieList[indexPath.row]
        cell.textLabel?.text = movie.titleVar
        
        return cell
    }
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete
        {
            libraryVar.movieList.removeAtIndex(indexPath.row)
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let index = tableView.indexPathForSelectedRow?.row
        if (segue.identifier == "Add"){
            let add:AddController = segue.destinationViewController as! AddController
            add.viewVar = self
        }
        else if (segue.identifier == "Display"){
            let diplay:DisplayContoller = segue.destinationViewController as! DisplayContoller
            
            diplay.movie = libraryVar.movieList[index!]
        }
        
    }
    
    func addMovie(movie: MovieDescription) {
        libraryVar.movieList.append(movie)
        self.tableView.reloadData()
    }
}

