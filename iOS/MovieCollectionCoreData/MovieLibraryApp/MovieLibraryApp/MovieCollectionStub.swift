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

//This class helps us make remote procedure calls in the background thread asynchronously.

import Foundation

public class MovieCollectionStub {
    
    var urlPre:String = "http://www.omdbapi.com/?t="
    var urlPost:String = "&y=&plot=short&r=json"
    
    init(){}
    
    // asyncHttpPostJson creates and posts a URLRequest that attaches a JSONRPC request as an NSData object
    func asyncHttpGetJSON(url: String,
        callback: (String, String?) -> Void) {
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = "GET"
            request.addValue("application/json",forHTTPHeaderField: "Accept")
            sendHttpRequest(request, callback: callback)
    }
    
    // sendHttpRequest
    func sendHttpRequest(request: NSMutableURLRequest,
        callback: (String, String?) -> Void) {
            // task.resume causes the shared session http request to be posted in the background (non-UI Thread)
            // the use of the dispatch_async on the main queue causes the callback to be performed on the UI Thread
            // after the result of the post is received.
            let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
                (data, response, error) -> Void in
                if (error != nil) {
                    callback("", error!.localizedDescription)
                } else {
                    dispatch_async(dispatch_get_main_queue(),
                        {callback(NSString(data: data!,
                            encoding: NSUTF8StringEncoding)! as String, nil)})
                }
            }
            task.resume()
    }
    
    func getMovieInfo(name:String, callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        let url2 = urlPre + name.stringByReplacingOccurrencesOfString(" ", withString: "%20", options:NSStringCompareOptions.LiteralSearch, range: nil) + urlPost
        self.asyncHttpGetJSON(url2,callback: callback)
        ret = true
        return ret
    }

}