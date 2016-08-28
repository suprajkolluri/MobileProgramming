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
// @version Apr 10, 2016


//This class is a controller for the Movie Details Display view

import Foundation
import UIKit
import CoreData
class DisplayContoller: UIViewController {
    
    var movieName:String?
    
    var appDel:AppDelegate?
    var mContext:NSManagedObjectContext?
    
    @IBOutlet weak var posterTF: UILabel!
    @IBOutlet weak var titleTF: UILabel!
    
    @IBOutlet weak var yearTF: UILabel!
    
    @IBOutlet weak var ratedTF: UILabel!
    
    @IBOutlet weak var releasedTF: UILabel!
    
    @IBOutlet weak var runtimeTF: UILabel!
    
    @IBOutlet weak var genreTF: UILabel!
    
    @IBOutlet weak var actorsTF: UILabel!
    
    @IBOutlet weak var plotTF: UILabel!
    

    override func viewDidLoad() {
        super.viewDidLoad()
        appDel = (UIApplication.sharedApplication().delegate as! AppDelegate)
        mContext = appDel!.managedObjectContext
        self.loadMovieInfo()
    }
    
    func loadMovieInfo(){
        let selectRequest = NSFetchRequest(entityName: "MovieEntity")
        selectRequest.predicate = NSPredicate(format: "title == %@",movieName!)
        do{
            let results = try mContext!.executeFetchRequest(selectRequest)
            if results.count > 0 {
                titleTF.text = results[0].valueForKey("title") as? String
                ratedTF.text = results[0].valueForKey("rated") as? String
                releasedTF.text = results[0].valueForKey("released") as? String
                runtimeTF.text = results[0].valueForKey("runtime") as? String
                genreTF.text = results[0].valueForKey("genre") as? String
                actorsTF.text = results[0].valueForKey("actors") as? String
                plotTF.text = results[0].valueForKey("plot") as? String
                posterTF.text = results[0].valueForKey("poster") as? String
                
                let yearObj = results[0].valueForKey("yearID") as? NSManagedObject
                yearTF.text = yearObj?.valueForKey("year") as? String
            }
        } catch let error as NSError{
            NSLog("Error getting movie names. Error: \(error)")
        }
    }
}