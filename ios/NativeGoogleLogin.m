#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(NativeGoogleLogin, NSObject)

RCT_EXTERN_METHOD(configure: (NSDictionary*) configureOptions)
RCT_EXTERN_METHOD(googleSignIn: (RCTPromiseResolveBlock) resolve withRejecter: (RCTPromiseRejectBlock) reject)
RCT_EXTERN_METHOD(googleSignOut: (RCTPromiseResolveBlock) resolve withRejecter: (RCTPromiseRejectBlock) reject)
RCT_EXTERN_METHOD(googleDisconnect: (RCTPromiseResolveBlock) resolve withRejecter: (RCTPromiseRejectBlock) reject)

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

@end
