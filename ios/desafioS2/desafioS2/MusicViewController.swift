//
//  MusicViewController.swift
//  desafioS2
//
//  Created by Kiko Santos on 05/09/17.
//  Copyright © 2017 Kiko Santos. All rights reserved.
//

import UIKit

protocol MusicViewControllerDelegate {
    func popView(_ viewController: UIViewController)
    func saveView(_ viewController: UIViewController)
}

class MusicViewController: UIViewController {
    
    var mDelegate: MusicViewControllerDelegate?
    
    @IBOutlet weak var AlbumArtwork: UIImageView?
    
    @IBOutlet weak var lbSongTitle: UILabel?
    @IBOutlet weak var lbArtist: UILabel?
    @IBOutlet weak var lbAlbumTitle: UILabel?
    
    var music: NSDictionary?
    
    @IBAction func actionDislike(_ sender: Any) {
        mDelegate?.popView(self)
    }
    
    @IBAction func actionLike(_ sender: Any) {
        mDelegate?.saveView(self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.lbArtist?.text = music?.value(forKey: "artistName") as? String
        self.lbSongTitle?.text = music?.value(forKey: "trackName") as? String
        self.lbAlbumTitle?.text = music?.value(forKey: "collectionName") as? String
        
        self.AlbumArtwork?.loadImageAsync(with: music?.value(forKey: "artworkUrl100") as? String)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}
