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

//This class helps us make remote procedure calls in the background thread asynchronously.

import Foundation

public class MovieCollectionStub {
    
    var urlPre:String = "http://www.omdbapi.com/?t="
    var urlPost:String = "&y=&plot=short&r=json"
    
    static var id:Int = 0
    
    init(){}
    
    // asyncHttpGetJson creates and posts a URLRequest that attaches a JSONRPC request as an NSData object
    func asyncHttpGetJSON(url: String,
        callback: (String, String?) -> Void) {
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = "GET"
            request.addValue("application/json",forHTTPHeaderField: "Accept")
            sendHttpRequest(request, callback: callback)
    }
    
    func asyncHttpPostJSON(url: String,  data: NSData,
        callback: (String, String?) -> Void) {
            
            let request = NSMutableURLRequest(URL: NSURL(string: url)!)
            request.HTTPMethod = "POST"
            request.addValue("application/json",forHTTPHeaderField: "Content-Type")
            request.addValue("application/json",forHTTPHeaderField: "Accept")
            request.HTTPBody = data
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
    
    func getMovieInfoFromRPC(name:String, callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        MovieCollectionStub.id = MovieCollectionStub.id + 1
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"get", "params":[name], "id":MovieCollectionStub.id]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            var host:String?
            var rpc_port:String?
            if let path = NSBundle.mainBundle().pathForResource("Info", ofType: "plist"){
                if let dict = NSDictionary(contentsOfFile: path) as? [String:AnyObject] {
                    host = dict["host"] as? String
                    rpc_port = dict["rpc_port"] as? String
                }
                let urlString:String = "http://\(host!):\(rpc_port!)"
                NSLog("viewDidLoad using url: \(urlString)")
            
            self.asyncHttpPostJSON(urlString, data: reqData, callback: callback)
            ret = true
            }
        } catch let error as NSError {
            print(error)
        }
        return ret
    }

}