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

import UIKit
import AVKit
import AVFoundation

class MoviePlayController: AVPlayerViewController, NSURLSessionDelegate {
    
    var streamer_host:String?
    var streamer_port:String?
    
    var filename:String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        // get the host and port from Info.plist
        if let path = NSBundle.mainBundle().pathForResource("Server", ofType: "plist"){
            if let dict = NSDictionary(contentsOfFile: path) as? [String:AnyObject] {
                streamer_host = dict["host"] as? String
                streamer_port = dict["streamer_port"] as? String
            }
            let urlString:String = "http://\(streamer_host!):\(streamer_port!)/\(filename!)"
            NSLog("viewDidLoad using url: \(urlString)")
            // download the video to a file before playing
            downloadVideo(urlString)
        }
    }
    
    override func viewWillDisappear(animated: Bool){
        if let status:AVPlayerStatus = self.player?.status {
            NSLog("viewWillDisappear \(((status==AVPlayerStatus.ReadyToPlay) ? "Ready":"unknown")))")
        }else{
            NSLog("viewWillDisappear player not initialized")
        }
        if self.player != nil {
            self.player?.pause()
        }
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // download the video in the background using NSURLSession.
    func downloadVideo(urlString: String){
        let bgConf = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier("bgSession")
        let backSess = NSURLSession(configuration: bgConf, delegate: self, delegateQueue:NSOperationQueue.mainQueue())
        let aUrl = NSURL(string: urlString)!
        let downloadBG = backSess.downloadTaskWithURL(aUrl)
        downloadBG.resume()
    }
    
    // play the movie from a file url
    func playMovieAtURL(fileURL: NSURL){
        if (self.player != nil && self.player!.status == AVPlayerStatus.ReadyToPlay) {
            let playerItem = AVPlayerItem(URL: fileURL)
            self.player?.replaceCurrentItemWithPlayerItem(playerItem)
        }else{
            self.player = AVPlayer(URL: fileURL)
        }
        self.videoGravity = AVLayerVideoGravityResizeAspectFill
        //self.player = aPlayer
        self.player!.play()
    }
    
    // functions for NSURLSessionDelegate
    func URLSession(session: NSURLSession,
        downloadTask: NSURLSessionDownloadTask,
        didFinishDownloadingToURL location: NSURL){
            let path = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)
            let documentDirectoryPath:String = path[0]
            let fileMgr = NSFileManager()
            let destinationURLForFile = NSURL(fileURLWithPath: documentDirectoryPath.stringByAppendingString("/\(filename!)"))
            
            if fileMgr.fileExistsAtPath(destinationURLForFile.path!) {
                NSLog("destination file url: \(destinationURLForFile.path!) exists. Deleting")
                do {
                    try fileMgr.removeItemAtURL(destinationURLForFile)
                }catch{
                    NSLog("error removing file at: \(destinationURLForFile)")
                }
            }
            do {
                try fileMgr.moveItemAtURL(location, toURL: destinationURLForFile)
                NSLog("download and save completed to: \(destinationURLForFile.path!)")
                session.invalidateAndCancel()
                playMovieAtURL(destinationURLForFile)
            }catch{
                NSLog("An error occurred while moving file to destination url")
            }
    }
    
    func URLSession(session: NSURLSession,
        downloadTask: NSURLSessionDownloadTask,
        didWriteData bytesWritten: Int64,
        totalBytesWritten: Int64,
        totalBytesExpectedToWrite: Int64){
            NSLog("did write portion of file: \(Float(totalBytesWritten)/Float(totalBytesExpectedToWrite))")
    }
    
}


