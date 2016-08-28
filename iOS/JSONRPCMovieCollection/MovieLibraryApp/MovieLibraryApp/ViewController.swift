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
// @version Mar 15, 2016

//This is the controller class for the table view in the initial navigation page
//Using this class we update the movies in the table view, delete the movies and 
//navigate to Movie Details page as well the Add Movie page.

import UIKit

class ViewController: UITableViewController {
    var urlString:String?
    
    var movies:[String]=[String]()
    var curMovie:MovieDescription = MovieDescription()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let path = NSBundle.mainBundle().pathForResource("Info", ofType: "plist")
        let dict = NSDictionary(contentsOfFile: path!)
        urlString = dict?.objectForKey("defaultURL") as? String
    }
    
    override func viewWillAppear(animated: Bool) {
        self.callUpdateGetNames()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func callUpdateGetNames() {
        let aConnect:MovieCollectionStub = MovieCollectionStub(urlString: urlString!)
        aConnect.getNames({ (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                        self.movies = (dict!["result"] as? [String])!
                
                        self.tableView.reloadData()
                       
                    } catch {
                        print("unable to convert to dictionary")
                    }
                }
                
            }
        })  // end of method call to getNames
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
            let aConnect:MovieCollectionStub = MovieCollectionStub(urlString: urlString!)
            aConnect.delete(movie,callback: { (res: String, err: String?) -> Void in
                if err != nil {
                    NSLog(err!)
                }else{
                    NSLog(res)
                    tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
                }
            })  // end of method call to delete Movies
            
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
            diplay.movieName = movies[index!]
        }
        
    }
    
    func addMovie(movie: MovieDescription) {
        
        let aConnect:MovieCollectionStub = MovieCollectionStub(urlString: urlString!)
        aConnect.add(movie.toJsonString(),callback: { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                self.callUpdateGetNames()
            }
        })  // end of method call to add Movies
    }
}

