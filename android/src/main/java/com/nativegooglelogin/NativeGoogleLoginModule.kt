package com.nativegooglelogin

import android.app.Activity
import com.facebook.react.bridge.*

class NativeGoogleLoginModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "NativeGoogleLogin"
    }

    @ReactMethod
    fun configure(configureMap: ReadableMap) {
      configureMap.getString("webClientId")?.let { id ->
        GoogleSignInUtil.webClientId = id;
      }
    }


    @ReactMethod
    fun googleSignIn(promise: Promise) {
      var util: GoogleSignInUtil = GoogleSignInUtil(reactApplicationContext)
      var currentReactActivity: Activity? = currentActivity
      if (currentReactActivity != null) {
        util.signIn(currentReactActivity, promise)
      }
    }

    @ReactMethod
    fun googleSignOut(promise: Promise) {
      var util: GoogleSignInUtil = GoogleSignInUtil(reactApplicationContext);
      util.signOut(promise);
    }

    @ReactMethod
    fun googleDisconnect(promise: Promise) {
      var util: GoogleSignInUtil = GoogleSignInUtil(reactApplicationContext);
      util.disconnect(promise)
    }
}
