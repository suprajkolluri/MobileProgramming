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

//This class is a controller for the Movie Details Display view

import Foundation
import UIKit

class DisplayContoller: UIViewController {
    
    var movie:MovieDescription?
    
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
        
        titleTF.text = movie!.titleVar
        yearTF.text = movie!.yearVar
        ratedTF.text = movie!.ratedVar
        releasedTF.text = movie!.releasedVar
        runtimeTF.text = movie!.runtimeVar
        genreTF.text = movie!.genreVar
        actorsTF.text = movie!.actorsVar
        plotTF.text = movie!.plotVar
    }
}