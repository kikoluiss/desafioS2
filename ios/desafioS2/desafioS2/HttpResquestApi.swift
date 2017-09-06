//
//  HttpResquestApi.swift
//  desafioS2
//
//  Created by Kiko Santos on 05/09/17.
//  Copyright © 2017 Kiko Santos. All rights reserved.
//

import Foundation

class HttpResquestApi {
    
    func getData(_ url: String, conditions: [String: AnyObject]?, completion: ((AnyObject) -> Void)!) {
        var jsonConditions: String = ""
        var newUrl = ""
        if let unwrapedConditions = conditions {
            for (name, value) in unwrapedConditions {
                if let valueString = value as? String {
                    jsonConditions = "\(jsonConditions)\(jsonConditions == "" ? "" : "&")\(name)=\(valueString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!)"
                }
                else {
                    jsonConditions = "\(jsonConditions)\(jsonConditions == "" ? "" : "&")\(name)=\(value)"
                }
            }
        }
        if (jsonConditions != "") {
            newUrl = "\(url)?\(jsonConditions)"
        }
        else {
            newUrl = url
        }
        
        let session = URLSession.shared
        let urlString = URL(string: newUrl)
        var request = URLRequest(url: urlString!)
        request.httpMethod = "GET"
        
        let task = session.dataTask(with: request, completionHandler: {
            (data, reponse, error) -> Void in
            
            var parsedObject: AnyObject?
            if error != nil {
                parsedObject = "Error: \(error!.localizedDescription)" as AnyObject?
                completion(parsedObject!)
            }
            else {
                do {
                    parsedObject = try JSONSerialization.jsonObject(with: data!, options: JSONSerialization.ReadingOptions.allowFragments) as AnyObject?
                    completion(parsedObject!)
                }
                catch {
                    parsedObject = "Error: cannot parse content" as AnyObject?
                    completion(parsedObject!)
                }
            }
        })
        task.resume()
    }
    
}
