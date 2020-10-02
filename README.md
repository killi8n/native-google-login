# native-google-login

## installation

### 1. iOS

```bash
$ yarn add native-google-login
$ cd ios
$ pod install
```

create `GoogleLoginUtil.swift`

```swift
import GoogleSignIn

@objc
class GoogleLoginUtil: NSObject {
  @objc
  static func initGoogleSDK() -> Void {
    GIDSignIn.sharedInstance()?.clientID = "YOUR_CLIENT_ID"
  }

  @objc
  static func handleOpenUrl(url: URL) -> Bool {
    guard let googleSharedInstance = GIDSignIn.sharedInstance() else {
      return false
    }
    if (googleSharedInstance.handle(url)) {
      return googleSharedInstance.handle(url)
    }
    return false
  }
}

```

`AppDelegate.m`
```objc
#import "{APPNAME}-Swift.h"
...
- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
#ifdef FB_SONARKIT_ENABLED
  InitializeFlipper(application);
#endif

  ....

  [GoogleLoginUtil initGoogleSDK];
  return YES;
}
...

- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options {
  return [GoogleLoginUtil handleOpenUrlWithUrl:url];
}
```

go to xcode -> info -> URL Types 

add `com.googleusercontent.apps.{YOUR_GOOGLE_CLIENT_ID}` in url scheme

### 2. android

open `strings.xml`

```xml
<!-- add this line -->
<string name="server_client_id">YOUR_GOOGLE_CLIENT_ID</string>
```

open `build.gradle(app)`

```xml
<!-- add this line -->
implementation 'com.google.android.gms:play-services-auth:18.1.0'
```

## how to use

```js
import GoogleLogin from "native-google-login";

const google = async () => {
    const configure = GoogleLogin.configure({ webClientId: "WEB_OAUTH_CLIENT_ID" }) // in android, you need to provide webClientId to get idToken
    const signInResult = await GoogleLogin.googleSignIn()
    const signOutResult = await GoogleLogin.googleSignOut()
    const disconnectResult = await GoogleLogin.googleDisconnect()
}
```