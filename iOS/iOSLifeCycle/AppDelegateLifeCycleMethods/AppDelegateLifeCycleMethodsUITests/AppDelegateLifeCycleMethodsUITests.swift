
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

import XCTest

class AppDelegateLifeCycleMethodsUITests: XCTestCase {
        
    override func setUp() {
        super.setUp()
        
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false
        // UI tests must launch the application that they test. Doing this in setup will make sure it happens for each test method.
        XCUIApplication().launch()

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testExample() {
        // Use recording to get started writing UI tests.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
}
