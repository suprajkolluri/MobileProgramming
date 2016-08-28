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

//This class helps us make remote procedure calls in the background thread asynchronously.

import Foundation

public class MovieCollectionStub {
    static var id:Int = 0
    
    var url:String
    
    init(urlString: String){
        self.url = urlString
    }
    
    // asyncHttpPostJson creates and posts a URLRequest that attaches a JSONRPC request as an NSData object
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

    
    func getNames(callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        MovieCollectionStub.id = MovieCollectionStub.id + 1
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"getMovieNames", "params":[ ], "id":MovieCollectionStub.id]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func get(name: String, callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        MovieCollectionStub.id = MovieCollectionStub.id + 1
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"get", "params":[name], "id":MovieCollectionStub.id]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func getGenreNames(callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        MovieCollectionStub.id = MovieCollectionStub.id + 1
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"getGenreNames", "params":[], "id":MovieCollectionStub.id]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func add(movieJson: String, callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        MovieCollectionStub.id = MovieCollectionStub.id + 1
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"add", "params":[movieJson], "id":MovieCollectionStub.id]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }
    
    func delete(name: String, callback: (String, String?) -> Void) -> Bool{
        var ret:Bool = false
        MovieCollectionStub.id = MovieCollectionStub.id + 1
        do {
            let dict:[String:AnyObject] = ["jsonrpc":"2.0", "method":"remove", "params":[name], "id":MovieCollectionStub.id]
            let reqData:NSData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions(rawValue: 0))
            self.asyncHttpPostJSON(self.url, data: reqData, callback: callback)
            ret = true
        } catch let error as NSError {
            print(error)
        }
        return ret
    }

}