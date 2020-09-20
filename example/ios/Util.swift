//
//  Util.swift
//  NativeGoogleLoginExample
//
//  Created by Dongho Choi on 2020/09/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import GoogleSignIn

@objc
class Util: NSObject {
  @objc
  static func initializeGoogleSignIn(oauthClientId: String) {
    GIDSignIn.sharedInstance()?.clientID = oauthClientId
  }
  
  @objc
  static func handleOpenUrl(url: URL) -> Bool {
    guard let sharedInstance = GIDSignIn.sharedInstance() else { return false }
    return sharedInstance.handle(url)
  }
}
