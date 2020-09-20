import { NativeModules } from 'react-native';

interface GoogleSignInResult {
  userId: string;
  idToken: string;
  givenName: string;
  familyName: string;
  email: string;
}

interface GoogleSignOutResult {
  success: boolean;
}

interface GoogleDisconnectResult {
  success: boolean;
}

type NativeGoogleLoginType = {
  googleSignIn: () => Promise<GoogleSignInResult>;
  googleSignOut: () => Promise<GoogleSignOutResult>;
  googleDisconnect: () => Promise<GoogleDisconnectResult>;
};

const { NativeGoogleLogin } = NativeModules;

export default NativeGoogleLogin as NativeGoogleLoginType;
