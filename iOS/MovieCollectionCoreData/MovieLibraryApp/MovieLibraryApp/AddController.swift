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

//This class is a controller for the Add Movie Details Display view
//The Movie Details can be loaded from from open movie database usinng the search bar

import Foundation
import UIKit
import CoreData

class AddController: UIViewController, UIPickerViewDelegate, UISearchBarDelegate {
    
    var appDel:AppDelegate?
    var mContext:NSManagedObjectContext?
    
    var rowNum:Int = 0

    @IBOutlet weak var posterF: UITextField!
    @IBOutlet weak var titleF: UITextField!

    @IBOutlet weak var yearF: UITextField!
    
    @IBOutlet weak var ratedF: UITextField!
    
    @IBOutlet weak var releasedF: UITextField!

    @IBOutlet weak var runtimeF2: UITextField!
    
    @IBOutlet weak var genreF: UIPickerView!
    
    @IBOutlet weak var actorsF: UITextField!
    
    @IBOutlet weak var plotF: UITextField!
    
    var genreArr:[String] = ["Adventure","Action","Drama", "Comedy", "Fantasy", "Biography", "Animation"]
    
    var searchController:UISearchController!
    
    var movies = [NSManagedObject]()
    
    @IBAction func onClick(sender: AnyObject) {
        self.saveMovie()
        self.navigationController?.popToRootViewControllerAnimated(true)
        
    }
    
    func saveMovie(){
        let title:String = titleF.text!
        let year:String = yearF.text!
        let rated:String = ratedF.text!
        let released:String = releasedF.text!
        let runtime:String = runtimeF2.text!
        let genre:String = genreArr[rowNum]
        let actors:String = actorsF.text!
        let plot:String = plotF.text!
        let poster:String = posterF.text!
        let entity = NSEntityDescription.entityForName("MovieEntity", inManagedObjectContext: mContext!)
        let aMovie = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: mContext)
        
        let yearEntity = NSEntityDescription.entityForName("MovieYear", inManagedObjectContext: mContext!)
        let movieYear = NSManagedObject(entity: yearEntity!, insertIntoManagedObjectContext: mContext)
        aMovie.setValue(title, forKey:"title")
        aMovie.setValue(rated, forKey:"rated")
        aMovie.setValue(runtime, forKey:"runtime")
        aMovie.setValue(released, forKey:"released")
        aMovie.setValue(genre, forKey:"genre")
        aMovie.setValue(actors, forKey:"actors")
        aMovie.setValue(plot, forKey:"plot")
        aMovie.setValue(poster, forKey:"poster")
        movieYear.setValue(year, forKey: "year")
        aMovie.setValue(movieYear, forKey: "yearID")
        do{
            try mContext!.save()
            movies.append(aMovie)
        } catch let error as NSError{
            NSLog("Error adding movie \(title). Error: \(error)")
        }

    }
    
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        appDel = (UIApplication.sharedApplication().delegate as! AppDelegate)
        mContext = appDel!.managedObjectContext
        
        self.searchController = UISearchController(searchResultsController:  nil)
        self.searchController.searchBar.delegate = self
        self.searchController.hidesNavigationBarDuringPresentation = false
        self.searchController.dimsBackgroundDuringPresentation = true
        self.navigationItem.titleView = searchController.searchBar
        
        genreF.delegate = self
        
        genreF.removeFromSuperview()
       
    }
    
    func searchBarTextDidEndEditing(searchBar: UISearchBar) {
        let aConnect:MovieCollectionStub = MovieCollectionStub()
        aConnect.getMovieInfo(searchBar.text!, callback:{ (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                        let result = (dict!["Response"] as? String)
                        if(result == "True"){
                            self.titleF.text = (dict!["Title"] as? String)
                            self.yearF.text = (dict!["Year"] as? String)
                            self.ratedF.text = (dict!["Rated"] as? String)
                            self.releasedF.text = (dict!["Released"] as? String)
                            self.runtimeF2.text = (dict!["Runtime"] as? String)
                            self.actorsF.text = (dict!["Actors"] as? String)
                            self.plotF.text = (dict!["Plot"] as? String)
                            self.posterF.text = (dict!["Poster"] as? String)
                            let genreAll = (dict!["Genre"] as? String)
                            let genreAllArr = genreAll!.characters.split{$0 == ","}.map(String.init)
                            let genreFirst = genreAllArr[0].stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                            var index = self.genreArr.indexOf(genreFirst)
                            if(index == nil){
                                index = 0;
                            }
                            self.rowNum = index!
                            self.genreF.selectRow(index!, inComponent: 0, animated: true)
                        }else{
                            self.titleF.text = "No Movie Found"
                            self.yearF.text = ""
                            self.ratedF.text = ""
                            self.releasedF.text = ""
                            self.runtimeF2.text = ""
                            self.actorsF.text = ""
                            self.plotF.text = ""
                            self.posterF.text = ""
                            self.rowNum = 0
                            self.genreF.selectRow(0, inComponent: 0, animated: true)
                        }
                        self.searchController.active = false;
                        searchBar.resignFirstResponder()
                        self.reloadInputViews()
                    } catch {
                        print("unable to convert to dictionary")
                    }
                }
                
            }
        })
        
    }
   
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
       return 1
    }
    
   
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return genreArr.count
    }
    
  
    func pickerView (pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return genreArr[row]
    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int)
    {
        rowNum = row
    }
}