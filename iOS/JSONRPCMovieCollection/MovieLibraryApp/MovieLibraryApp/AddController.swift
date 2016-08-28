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

//This class is a controller for the Add Movie Details Display view
//The Movie Library is updated here as well as the table view is refreshed

import Foundation
import UIKit


class AddController: UIViewController, UIPickerViewDelegate {
    
    var rowNum:Int = 0

    @IBOutlet weak var titleF: UITextField!

    @IBOutlet weak var yearF: UITextField!
    
    @IBOutlet weak var ratedF: UITextField!
    
    @IBOutlet weak var releasedF: UITextField!

    @IBOutlet weak var runtimeF2: UITextField!
    
    @IBOutlet weak var genreF: UIPickerView!
    
    @IBOutlet weak var actorsF: UITextField!
    
    @IBOutlet weak var plotF: UITextField!
    
    var genreArr:[String] = [String]()
    
    var urlString:String?
    
    var viewVar: ViewController?
    
    @IBAction func onClick(sender: AnyObject) {
 
        viewVar!.addMovie(MovieDescription(title: titleF.text!, year: yearF.text!, rated: ratedF.text!, released: releasedF.text!, runtime: runtimeF2.text! , genre: genreArr[rowNum], actors: actorsF.text!, plot: plotF.text!))
        
        self.navigationController?.popToRootViewControllerAnimated(true)
        
    }
    
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        let path = NSBundle.mainBundle().pathForResource("Info", ofType: "plist")
        let dict = NSDictionary(contentsOfFile: path!)
        urlString = dict?.objectForKey("defaultURL") as? String
        self.getGenreNames()
        genreF.delegate = self
        
        genreF.removeFromSuperview()
       
    }
   
    func getGenreNames() {
        let aConnect:MovieCollectionStub = MovieCollectionStub(urlString: urlString!)
        aConnect.getGenreNames({ (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    do{
                        let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                        self.genreArr = (dict!["result"] as? [String])!
                        self.genreF.reloadAllComponents()
                    } catch {
                        print("unable to convert to dictionary")
                    }
                }
                
            }
        })  // end of method call to getGenreNames
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