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

//This class is a controller for the Movie Details Display view

import Foundation
import UIKit

class DisplayContoller: UIViewController {
    
    var movieName:String?
    
    var urlString:String?
    
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
        let path = NSBundle.mainBundle().pathForResource("Info", ofType: "plist")
        let dict = NSDictionary(contentsOfFile: path!)
        urlString = dict?.objectForKey("defaultURL") as? String
        self.loadMovieInfo()
        
    }
    
    func loadMovieInfo(){
        let aConnect:MovieCollectionStub = MovieCollectionStub(urlString: urlString!)
        aConnect.get(movieName!,callback: { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        
                        let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                        let aDict:[String:AnyObject] = (dict!["result"] as? [String:AnyObject])!
                        let movie:MovieDescription = MovieDescription(dict: aDict)
                        
                        self.titleTF.text = movie.titleVar
                        self.yearTF.text = movie.yearVar
                        self.ratedTF.text = movie.ratedVar
                        self.releasedTF.text = movie.releasedVar
                        self.runtimeTF.text = movie.runtimeVar
                        self.genreTF.text = movie.genreVar
                        self.actorsTF.text = movie.actorsVar
                        self.plotTF.text = movie.plotVar
                        self.reloadInputViews()
                    } catch {
                        print("unable to convert to dictionary")
                    }
                }
                
            }
        })  // end of method call to get
    }
}