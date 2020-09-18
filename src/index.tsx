import { NativeModules } from 'react-native';

type NativeGoogleLoginType = {
  multiply(a: number, b: number): Promise<number>;
};

const { NativeGoogleLogin } = NativeModules;

export default NativeGoogleLogin as NativeGoogleLoginType;
