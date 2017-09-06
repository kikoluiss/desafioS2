//
//  MatchViewController.swift
//  desafioS2
//
//  Created by Kiko Santos on 05/09/17.
//  Copyright © 2017 Kiko Santos. All rights reserved.
//

import UIKit
import ReachabilitySwift

class MatchViewController: UIViewController, UIPageViewControllerDelegate, UIPageViewControllerDataSource, UISearchBarDelegate, MusicViewControllerDelegate {
    
    var musicList: [NSDictionary] = []
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    let reachability = Reachability()!
    
    @IBOutlet weak var searchbar: UISearchBar!
    
    var httpRequest = HttpResquestApi()
    let baseUrl = "https://itunes.apple.com/search"
    var searchText: String?
    
    let imageCache = NSCache<NSString, AnyObject>()
    
    
    var pageContainer: UIPageViewController!
    var pages: [UIViewController] = []
    var currentIndex: Int = 0
    
    var alert: UIAlertController?
    
    var page1: UIViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Create the page container
        pageContainer = UIPageViewController(transitionStyle: .scroll, navigationOrientation: .horizontal, options: nil)
        pageContainer.delegate = self
        pageContainer.dataSource = self
        
        self.page1 = self.storyboard?.instantiateViewController(withIdentifier: "WelcomeViewController")
        
        pageContainer.setViewControllers([self.page1!], direction: UIPageViewControllerNavigationDirection.forward, animated: false, completion: nil)
        
        // Add it to the view
        view.addSubview(pageContainer.view)
        
        searchbar.delegate = self
        view.bringSubview(toFront: searchbar)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // called whenever text is changed.
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        self.searchText = searchText
    }
    
    // called when cancel button is clicked
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchbar.text = ""
        self.searchText = ""
    }
    
    // called when search button is clicked
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        if self.reachability.isReachable {
            self.alert = UIAlertController(title: "Buscando...", message: "Aguarde...\n\n\n", preferredStyle: UIAlertControllerStyle.alert)
            let spinner = UIActivityIndicatorView(activityIndicatorStyle: UIActivityIndicatorViewStyle.whiteLarge)
            spinner.center = CGPoint(x: 130.5, y: 95.5);
            spinner.color = UIColor(red: 0, green: 0, blue: 0, alpha: 1)
            spinner.startAnimating()
            self.alert?.view.addSubview(spinner)
            self.present(self.alert!, animated: true, completion: nil)
            
            let wsConditions = [
                "term" : self.searchText,
                "country" : "BR",
                "media" : "music",
                ]
            
            self.httpRequest.getData(self.baseUrl, conditions: wsConditions as [String : AnyObject], completion: self.searchResults)
        }
        else {
            let errorAlert = UIAlertController(title: "Erro", message: "Falha conexão com a Internet!", preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "Ok", style: .cancel, handler: nil)
            
            errorAlert.addAction(cancelAction)
            
            self.present(errorAlert, animated: true, completion: nil)
        }
        
        self.view.endEditing(true)
    }
    
    func searchResults(_ result: AnyObject) -> Void {
        DispatchQueue.main.async {
            if let resultCount = result.object(forKey: "resultCount") as? Int {
                self.pageContainer.dataSource = nil
                self.pages = []
                self.musicList = []
                self.currentIndex = 0
                if resultCount > 0 {
                    if let resultArray = result.object(forKey: "results") as? NSArray {
                        for item in resultArray {
                            if let musicItem = item as? NSDictionary {
                                if let musicDetailView = self.storyboard?.instantiateViewController(withIdentifier: "MusicViewController") as? MusicViewController {
                                    musicDetailView.mDelegate = self
                                    musicDetailView.music = musicItem
                                    self.pages.append(musicDetailView)
                                    self.musicList.append(musicItem)
                                }
                            }
                        }
                        self.pageContainer.setViewControllers([self.pages[0]], direction: UIPageViewControllerNavigationDirection.forward, animated: false, completion: { (finished: Bool) in
                            if finished {
                                self.alert?.dismiss(animated: true, completion: {
                                    self.pageContainer.dataSource = self
                                })
                            }
                        })
                    }
                }
            }
            else {
                if let errorMessage = result as? String {
                    let errorAlert = UIAlertController(title: "Erro", message: errorMessage, preferredStyle: UIAlertControllerStyle.alert)
                    
                    let cancelAction = UIAlertAction(title: "Ok", style: .cancel, handler: nil)
                    
                    errorAlert.addAction(cancelAction)
                    
                    self.present(errorAlert, animated: true, completion: nil)
                }
            }
        }
    }
    
    func createMusicEntity(musicItem: NSDictionary?) -> Music {
        let music = Music(context: self.context)
        
        music.artistId = musicItem?.value(forKey: "artistId") as! Int32
        music.artistName = musicItem?.value(forKey: "artistName") as? String
        music.artistViewUrl = musicItem?.value(forKey: "artistViewUrl") as? String
        music.artworkUrl100 = musicItem?.value(forKey: "artworkUrl100") as? String
        music.artworkUrl30 = musicItem?.value(forKey: "artworkUrl30") as? String
        music.artworkUrl60 = musicItem?.value(forKey: "artworkUrl60") as? String
        music.collectionCensoredName = musicItem?.value(forKey: "collectionCensoredName") as? String
        music.collectionExplicitness = musicItem?.value(forKey: "collectionExplicitness") as? String
        music.collectionId = musicItem?.value(forKey: "collectionId") as! Int64
        music.collectionName = musicItem?.value(forKey: "collectionName") as? String
        music.collectionPrice = musicItem?.value(forKey: "collectionPrice") as? String
        music.collectionViewUrl = musicItem?.value(forKey: "collectionViewUrl") as? String
        music.country = musicItem?.value(forKey: "country") as? String
        music.currency = musicItem?.value(forKey: "currency") as? String
        music.discCount = musicItem?.value(forKey: "discCount") as! Int32
        music.discNumber = musicItem?.value(forKey: "discNumber") as! Int32
        music.isStreamable = musicItem?.value(forKey: "isStreamable") as! Bool
        music.kind = musicItem?.value(forKey: "kind") as? String
        music.previewUrl = musicItem?.value(forKey: "previewUrl") as? String
        music.primaryGenreName = musicItem?.value(forKey: "primaryGenreName") as? String

        let isoDate = musicItem?.value(forKey: "releaseDate") as? String
        let dateFormatter = ISO8601DateFormatter()
        music.releaseDate = dateFormatter.date(from:isoDate!)! as NSDate

        music.trackCensoredName = musicItem?.value(forKey: "trackCensoredName") as? String
        music.trackCount = musicItem?.value(forKey: "trackCount") as! Int32
        music.trackExplicitness = musicItem?.value(forKey: "trackExplicitness") as? String
        music.trackId = musicItem?.value(forKey: "trackId") as! Int64
        music.trackName = musicItem?.value(forKey: "trackName") as? String
        music.trackNumber = musicItem?.value(forKey: "trackNumber") as! Int32
        music.trackPrice = musicItem?.value(forKey: "trackPrice") as? String
        music.trackTimeMillis = musicItem?.value(forKey: "trackTimeMillis") as! Int64
        music.trackViewUrl = musicItem?.value(forKey: "trackViewUrl") as? String
        music.wrapperType = musicItem?.value(forKey: "wrapperType") as? String
        
        return music
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerBefore viewController: UIViewController) -> UIViewController? {
        
        let cur = self.pages.index(of: viewController)!
        
        // if you prefer to NOT scroll circularly, simply add here:
        if cur == 0 { return nil }
        
        let prev = abs((cur - 1) % self.pages.count)
        return self.pages[prev]
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerAfter viewController: UIViewController) -> UIViewController? {
        
        let cur = self.pages.index(of: viewController)!
        
        // if you prefer to NOT scroll circularly, simply add here:
        if cur == (pages.count - 1) { return nil }
        
        let nxt = abs((cur + 1) % self.pages.count)
        return self.pages[nxt]
    }
    
    func popView(_ viewController: UIViewController) {
        if let index = self.pages.index(of: viewController) {
            self.pages.remove(at: index)
            self.musicList.remove(at: index)
            if pages.count == 0 {
                pageContainer.setViewControllers([self.page1!], direction: UIPageViewControllerNavigationDirection.forward, animated: false, completion: nil)
            }
            else {
                if index == self.pages.count {
                    let newIndex = index - 1
                    self.pageContainer.setViewControllers([self.pages[newIndex]], direction: UIPageViewControllerNavigationDirection.reverse, animated: false, completion: nil)
                }
                else {
                    self.pageContainer.setViewControllers([self.pages[index]], direction: UIPageViewControllerNavigationDirection.forward, animated: false, completion: nil)
                }
            }
        }
        
    }
    
    func saveView(_ viewController: UIViewController) {
        if let index = self.pages.index(of: viewController) {
            let musicItem = self.musicList[index]
            let musicEntity: Music? = self.createMusicEntity(musicItem: musicItem)
            context.insert(musicEntity!)
            do {
                try context.save()
            }
            catch {
                print("Error fetching data from CoreData")
            }
            
            self.pages.remove(at: index)
            self.musicList.remove(at: index)
            
            if pages.count == 0 {
                pageContainer.setViewControllers([self.page1!], direction: UIPageViewControllerNavigationDirection.forward, animated: false, completion: nil)
            }
            else {
                if index == self.pages.count {
                    let newIndex = index - 1
                    self.pageContainer.setViewControllers([self.pages[newIndex]], direction: UIPageViewControllerNavigationDirection.reverse, animated: false, completion: nil)
                }
                else {
                    self.pageContainer.setViewControllers([self.pages[index]], direction: UIPageViewControllerNavigationDirection.forward, animated: false, completion: nil)
                }
            }
        }
    }
}
