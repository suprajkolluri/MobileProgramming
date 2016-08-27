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
// @version January 28, 2016

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        // This method will get called when the application is launched for the first time
        NSLog("In AppDelegate- application method")
        return true
    }

    func applicationWillResignActive(application: UIApplication) {
        // This method will get called when we minimize the application by clicking on home button and put the application in the background.
		// Here the application will resign from active state.
        NSLog("In AppDelegate- applicationWillResignActive method")
    }

    func applicationDidEnterBackground(application: UIApplication) {
        // This is called when we click on the home button on the simulator and the application is in the background.
		// This will get called after the applicationWillResignActive method.
        NSLog("In AppDelegate- applicationDidEnterBackground method")
    }

    func applicationWillEnterForeground(application: UIApplication) {
        // This will get called when we double click the home button and select this application from the list of minimized applications.
        NSLog("In AppDelegate- applicationWillEnterForeground method")
    }

    func applicationDidBecomeActive(application: UIApplication) {
        // This method will get called when the application starts for the first time and also when we select this application from the list of
		// minimized applications after clicking on the home button and bringing this to the foreground.
        NSLog("In AppDelegate- applicationDidBecomeActive method")
    }

    func applicationWillTerminate(application: UIApplication) {
        // This method will get called when we double click on the home button and close this application by swiping it.
        NSLog("In AppDelegate- applicationWillTerminate method")
    }


}

