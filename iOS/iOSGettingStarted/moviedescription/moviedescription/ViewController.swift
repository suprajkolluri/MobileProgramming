 //
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
 // @version January 18, 2016
 //


import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var titleTF2: UILabel!
    @IBOutlet weak var yearTF: UILabel!
    
    @IBOutlet weak var ratedTF: UILabel!
    
    @IBOutlet weak var releasedTF: UILabel!
    
    @IBOutlet weak var runtimeTF: UILabel!
    
    
    @IBOutlet weak var genreTF: UITextView!
    
    
    @IBOutlet weak var actorsTF: UITextView!
    
    
    @IBOutlet weak var plotTF: UITextView!
    
    
    @IBAction func onClickAction(sender: UIButton) {
        
        let json = "{\"Title\":\"Frozen\",\"Year\":\"2013\",\"Rated\":\"PG\",\"Released\":\"27 Nov 2013\",\"Runtime\":\"102 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Chris Buck, Jennifer Lee\",\"Writer\":\"Jennifer Lee (screenplay), Hans Christian Andersen (story), Chris Buck (story), Jennifer Lee (story), Shane Morris (story)\",\"Actors\":\"Kristen Bell, Idina Menzel, Jonathan Groff, Josh Gad\",\"Plot\":\"When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.\",\"Language\":\"English, Icelandic\",\"Country\":\"USA\",\"Awards\":\"Won 2 Oscars. Another 69 wins & 56 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQ1MjQwMTE5OF5BMl5BanBnXkFtZTgwNjk3MTcyMDE@._V1_SX300.jpg\",\"Metascore\":\"74\",\"imdbRating\":\"7.6\",\"imdbVotes\":\"390,405\",\"imdbID\":\"tt2294629\",\"Type\":\"movie\",\"Response\":\"True\"}"
        
        let des = MovieDescription(jsonStr: json)
        
        //printing Json String in the console
        
        print(des.toJsonString())
        
        titleTF2.text = des.titleVar
        yearTF.text  = des.yearVar
        ratedTF.text = des.ratedVar
        releasedTF.text = des.releasedVar
        runtimeTF.text = des.runtimeVar
        genreTF.text = des.genreVar
        actorsTF.text = des.actorsVar
        plotTF.text = des.plotVar
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

