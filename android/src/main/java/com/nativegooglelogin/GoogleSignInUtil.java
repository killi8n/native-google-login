package com.nativegooglelogin;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GoogleSignInUtil {
  public static int REQUEST_CODE = 100;
  public GoogleSignInClient googleSignInClient;
  public Promise reactPromise;
  public static String webClientId = "";
  public GoogleSignInUtil(ReactApplicationContext reactContext) {
    createGoogleSignInClient(reactContext);
    reactContext.addActivityEventListener(new GoogleSignInActivityEventListener());
  }

  public GoogleSignInOptions getSignInOptions() {
    GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail();
    if (GoogleSignInUtil.webClientId != null && GoogleSignInUtil.webClientId.length() > 0) {
      builder.requestIdToken(GoogleSignInUtil.webClientId);
    }
    return builder.build();
  }
  public void createGoogleSignInClient(ReactApplicationContext context) {
    GoogleSignInOptions gso = getSignInOptions();
    googleSignInClient = GoogleSignIn.getClient(context, gso);
  }

  public void signIn(Activity activity, Promise promise) {
    if (googleSignInClient == null) {
      return;
    }
    GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
    int status = availability.isGooglePlayServicesAvailable(activity);
    if (status != ConnectionResult.SUCCESS) {
      promise.reject("PLAY_SERVICES_NOT_AVAILABLE", "Play services not available");
      return;
    }
    reactPromise = promise;
    Intent signInIntent = googleSignInClient.getSignInIntent();
    activity.startActivityForResult(signInIntent, REQUEST_CODE);
  }

  public void signOut(Promise promise) {
    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        WritableMap resultMap = Arguments.createMap();
        resultMap.putBoolean("success", true);
        promise.resolve(resultMap);
      }
    }).addOnCanceledListener(new OnCanceledListener() {
      @Override
      public void onCanceled() {
        promise.reject("GOOGLE_SIGN_OUT_CANCELLED", "Google signout cancelled");
      }
    });
  }

  public void disconnect(Promise promise) {
    googleSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        WritableMap resultMap = Arguments.createMap();
        resultMap.putBoolean("success", true);
        promise.resolve(resultMap);
      }
    }).addOnCanceledListener(new OnCanceledListener() {
      @Override
      public void onCanceled() {
        promise.reject("GOOGLE_DISCONNECT_CANCELLED", "Google disconnect cancelled");
      }
    });
  }

  private class GoogleSignInActivityEventListener extends BaseActivityEventListener {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      super.onActivityResult(activity, requestCode, resultCode, data);
      if (requestCode == REQUEST_CODE) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
          GoogleSignInAccount account = task.getResult(ApiException.class);
          if (reactPromise != null) {
            WritableMap accountMap = Arguments.createMap();
            accountMap.putString("email", account.getEmail());
            accountMap.putString("familyName", account.getFamilyName());
            accountMap.putString("givenName", account.getGivenName());
            accountMap.putString("userId", account.getId());
            accountMap.putString("idToken", account.getIdToken());
            reactPromise.resolve(accountMap);
          }
        } catch (ApiException e) {
          e.printStackTrace();
          reactPromise.reject(e);
        }
      }
    }
  }
}
