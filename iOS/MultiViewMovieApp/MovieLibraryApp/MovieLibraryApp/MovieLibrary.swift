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

//This class is used to load the movies from the JSON file and contains the List of MovieDescription Objects

import Foundation

class MovieLibrary {
    
    var movieList = [MovieDescription]()
    
    init(){
        let path = NSBundle.mainBundle().pathForResource("movies",ofType:"json")
        let data: NSData? = NSData(contentsOfFile: path!)
        
        do{
            let dict = try NSJSONSerialization.JSONObjectWithData(data!,options:.MutableContainers) as?[AnyObject]
            for cur in dict!{
                
                let JSONData = try NSJSONSerialization.dataWithJSONObject(cur, options: NSJSONWritingOptions(rawValue: 0) )
                
                let curText = NSString(data: JSONData, encoding: NSUTF8StringEncoding) as! String
                let curMovie: MovieDescription = MovieDescription(jsonStr: curText)
                    self.movieList.append(curMovie)
            }
            

        }
        catch{
            print("unable to conv to dict")
        }
 
    }
}