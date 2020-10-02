import GoogleSignIn

@objc(NativeGoogleLogin)
class NativeGoogleLogin: NSObject, GIDSignInDelegate {
    var resolver: RCTPromiseResolveBlock?
    var rejecter: RCTPromiseRejectBlock?
    
    @objc
    func configure(_ configureOptions: NSDictionary) {
        if let webClientId = configureOptions["webClientId"] {
            guard let webClientId = webClientId as? String else {
                return
            }
            guard let sharedInstance = GIDSignIn.sharedInstance() else { return }
            sharedInstance.serverClientID = webClientId
        }
    }
    
    @objc
    func googleSignIn(_ resolve: @escaping RCTPromiseResolveBlock, withRejecter reject: @escaping RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            guard let sharedInstance = GIDSignIn.sharedInstance() else { return }
            self.resolver = resolve
            self.rejecter = reject
            sharedInstance.delegate = self
            guard let presentingViewController = UIApplication.shared.keyWindow?.rootViewController else { return }
            sharedInstance.presentingViewController = presentingViewController
//            sharedInstance.restorePreviousSignIn()
            sharedInstance.signIn()
        }
    }
    
    @objc
    func googleSignOut(_ resolve: @escaping RCTPromiseResolveBlock, withRejecter reject: @escaping RCTPromiseRejectBlock) {
        guard let sharedInstance = GIDSignIn.sharedInstance() else {
            print("sharedInstance is nil")
            reject("GOOGLE SIGN OUT ERROR", "sharedInstance is nil", nil)
            return
        }
        sharedInstance.signOut()
        resolve([
            "success": true
        ])
    }
    
    @objc
    func googleDisconnect(_ resolve: @escaping RCTPromiseResolveBlock, withRejecter reject: @escaping RCTPromiseRejectBlock) {
        guard let sharedInstance = GIDSignIn.sharedInstance() else {
            print("sharedInstance is nil")
            reject("GOOGLE DISCONNECT ERROR", "sharedInstance is nil", nil)
            return
        }
        sharedInstance.disconnect()
        resolve([
            "success": true
        ])
    }
    
//    GIDSignInDelegate
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error!) {
        if let error = error {
            guard let resolve = self.resolver else { return }
            if (error as NSError).code == GIDSignInErrorCode.hasNoAuthInKeychain.rawValue {
              print("The user has not signed in before or they have since signed out.")
                resolve(["errorMessage": "The user has not signed in before or they have since signed out."])
            } else {
              print("\(error.localizedDescription)")
                resolve(["errorMessage": "\(error.localizedDescription)"])
            }
            return
        }
        
        // Perform any operations on signed in user here.
        let userId = user.userID                  // For client-side use only!
        let idToken = user.authentication.idToken // Safe to send to the server
        let givenName = user.profile.givenName
        let familyName = user.profile.familyName
        let email = user.profile.email
        
        guard let resolve = self.resolver else { return }
        resolve([
            "userId": userId,
            "idToken": idToken,
            "givenName": givenName,
            "familyName": familyName,
            "email": email
        ])
    }
    
    func sign(_ signIn: GIDSignIn!, didDisconnectWith user: GIDGoogleUser!, withError error: Error!) {
        // Perform any operations when the user disconnects from app here.
        // ...
    }
}
