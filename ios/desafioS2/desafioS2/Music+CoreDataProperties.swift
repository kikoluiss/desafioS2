//
//  Music+CoreDataProperties.swift
//  desafioS2
//
//  Created by Kiko Santos on 05/09/17.
//  Copyright © 2017 Kiko Santos. All rights reserved.
//

import Foundation
import CoreData


extension Music {

    @nonobjc public class func fetchRequest() -> NSFetchRequest<Music> {
        return NSFetchRequest<Music>(entityName: "Music")
    }

    @NSManaged public var artistId: Int32
    @NSManaged public var artistName: String?
    @NSManaged public var artistViewUrl: String?
    @NSManaged public var artworkUrl100: String?
    @NSManaged public var artworkUrl30: String?
    @NSManaged public var artworkUrl60: String?
    @NSManaged public var collectionCensoredName: String?
    @NSManaged public var collectionExplicitness: String?
    @NSManaged public var collectionId: Int64
    @NSManaged public var collectionName: String?
    @NSManaged public var collectionPrice: String?
    @NSManaged public var collectionViewUrl: String?
    @NSManaged public var country: String?
    @NSManaged public var currency: String?
    @NSManaged public var discCount: Int32
    @NSManaged public var discNumber: Int32
    @NSManaged public var isStreamable: Bool
    @NSManaged public var kind: String?
    @NSManaged public var previewUrl: String?
    @NSManaged public var primaryGenreName: String?
    @NSManaged public var releaseDate: NSDate?
    @NSManaged public var trackCensoredName: String?
    @NSManaged public var trackCount: Int32
    @NSManaged public var trackExplicitness: String?
    @NSManaged public var trackId: Int64
    @NSManaged public var trackName: String?
    @NSManaged public var trackNumber: Int32
    @NSManaged public var trackPrice: String?
    @NSManaged public var trackTimeMillis: Int64
    @NSManaged public var trackViewUrl: String?
    @NSManaged public var wrapperType: String?

}
