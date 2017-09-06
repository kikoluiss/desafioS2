//
//  LikedTableTableViewController.swift
//  desafioS2
//
//  Created by Kiko Santos on 05/09/17.
//  Copyright © 2017 Kiko Santos. All rights reserved.
//

import UIKit

class LikedTableTableViewController: UITableViewController {

    var musicList: [Music] = []
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext

    
    override func viewDidLoad() {
        super.viewDidLoad()

        do {
            self.musicList = try context.fetch(Music.fetchRequest())
        }catch {
            print("Error fetching data from CoreData")
        }

    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidLoad()
        
        do {
            self.musicList = try context.fetch(Music.fetchRequest())
        }catch {
            print("Error fetching data from CoreData")
        }
        self.tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return self.musicList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "LikedTableViewCell") as! LikedTableViewCell
        
        cell.imgAlbumArtwork.loadImageAsync(with: self.musicList[indexPath.row].artworkUrl100)
        cell.lblArtist.text = self.musicList[indexPath.row].artistName
        cell.lbSongTitle.text = self.musicList[indexPath.row].trackName
        cell.lblAlbumTitle.text = self.musicList[indexPath.row].collectionName
        
        return cell
    }

}
