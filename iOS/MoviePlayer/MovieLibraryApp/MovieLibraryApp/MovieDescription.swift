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
 
 
 import Foundation
 class MovieDescription {
    var titleVar: String
    var yearVar: String
    var ratedVar: String
    var releasedVar: String
    var runtimeVar: String
    var genreVar: String
    var actorsVar: String
    var plotVar: String
    var posterVar:String
    var filenameVar:String
    
    init (jsonStr: String){
        self.titleVar = ""
        self.yearVar=""
        self.ratedVar = ""
        self.releasedVar = ""
        self.runtimeVar = ""
        self.genreVar = ""
        self.actorsVar = ""
        self.plotVar = ""
        self.posterVar = ""
        self.filenameVar = ""
        
        
        if let data: NSData = jsonStr.dataUsingEncoding(NSUTF8StringEncoding){
            do{
                let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                self.titleVar = (dict!["Title"] as? String)!
                self.yearVar = (dict!["Year"] as? String)!
                self.ratedVar = (dict!["Rated"] as? String)!
                self.releasedVar = (dict!["Released"] as? String)!
                self.runtimeVar = (dict!["Runtime"] as? String)!
                self.genreVar = (dict!["Genre"] as? String)!
                self.actorsVar = (dict!["Actors"] as? String)!
                self.plotVar = (dict!["Plot"] as? String)!
                self.posterVar = (dict!["Poster"] as? String)!
                self.filenameVar = (dict!["Filename"] as? String)!
            } catch {
                print("unable to convert to dictionary")
                
            }
        }
    }
    
    func toJsonString() -> String {
        var jsonStr = "";
        let dict = ["Title": titleVar, "Year": yearVar, "Rated":ratedVar, "Released":releasedVar, "Runtime":runtimeVar, "Genre":genreVar , "Actors":actorsVar, "Plot":plotVar]
        do {
            let jsonData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions.PrettyPrinted)
            // here "jsonData" is the dictionary encoded in JSON data
            jsonStr = NSString(data: jsonData, encoding: NSUTF8StringEncoding)! as String
        } catch let error as NSError {
            print(error)
        }
        return jsonStr
    }
 }