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

interface ConfigureParams {
  webClientId?: string;
}

type NativeGoogleLoginType = {
  configure: (params: ConfigureParams) => void;
  googleSignIn: () => Promise<GoogleSignInResult>;
  googleSignOut: () => Promise<GoogleSignOutResult>;
  googleDisconnect: () => Promise<GoogleDisconnectResult>;
};

const { NativeGoogleLogin } = NativeModules;

export default NativeGoogleLogin as NativeGoogleLoginType;
