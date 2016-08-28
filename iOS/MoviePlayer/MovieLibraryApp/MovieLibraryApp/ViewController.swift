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
// @version Apr 27, 2016

//This is the controller class for the table view in the initial navigation page
//Using this class we update the movies in the table view, delete the movies and 
//navigate to Movie Details page as well the Add Movie page.

import UIKit
import CoreData
class ViewController: UITableViewController {
    
    var movies:[String]=[String]()
    
    var appDel:AppDelegate?
    var mContext:NSManagedObjectContext?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        appDel = (UIApplication.sharedApplication().delegate as! AppDelegate)
        mContext = appDel!.managedObjectContext
    }
    
    override func viewWillAppear(animated: Bool) {
        self.loadMovieNames()
    }

    func loadMovieNames(){
        movies=[String]()
        let selectRequest = NSFetchRequest(entityName: "MovieEntity")
        do{
            let results = try mContext!.executeFetchRequest(selectRequest)
            if results.count > 0 {
                var i = 0
                while(i<results.count){
                    let movieName = results[i].valueForKey("title") as? String
                    movies.append(movieName!)
                    i++
                }
                
            }
        } catch let error as NSError{
            NSLog("Error getting movie names. Error: \(error)")
        }
        self.tableView.reloadData()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.movies.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCellWithIdentifier("Cell", forIndexPath: indexPath) as UITableViewCell
        
        cell.textLabel?.text = movies[indexPath.row]
        
        return cell
    }
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete
        {
            let movie:String = movies[indexPath.row]
            self.movies.removeAtIndex(indexPath.row)
            
            let selectRequest = NSFetchRequest(entityName: "MovieEntity")
            selectRequest.predicate = NSPredicate(format: "title == %@",movie)
            do{
                let results = try mContext!.executeFetchRequest(selectRequest)
                if results.count > 0 {
                    mContext!.deleteObject(results[0] as! NSManagedObject)
                    try mContext?.save()
                }
            } catch let error as NSError{
                NSLog("error selecting all students \(error)")
            }
            self.tableView.reloadData()
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let index = tableView.indexPathForSelectedRow?.row
        if (segue.identifier == "Add"){
            segue.destinationViewController as! AddController
        }
        else if (segue.identifier == "Display"){
            let diplay:DisplayContoller = segue.destinationViewController as! DisplayContoller
            diplay.movieName = movies[index!]
        }
        
    }
    
}

