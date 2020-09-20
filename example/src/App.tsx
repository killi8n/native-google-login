import * as React from 'react';
import { StyleSheet, View, Button } from 'react-native';
import NativeGoogleLogin from 'native-google-login';

export default function App() {
  return (
    <View style={styles.container}>
      <Button
        title="Google Sign In"
        onPress={async () => {
          try {
            const result = await NativeGoogleLogin.googleSignIn();
            console.log(result);
          } catch (e) {
            console.error(e);
          }
        }}
      />
      <Button
        title="Google Sign Out"
        onPress={async () => {
          try {
            const result = await NativeGoogleLogin.googleSignOut();
            console.log(result);
          } catch (e) {
            console.error(e);
          }
        }}
      />
      <Button
        title="Google Disconnect"
        onPress={async () => {
          try {
            const result = await NativeGoogleLogin.googleDisconnect();
            console.log(result);
          } catch (e) {
            console.error(e);
          }
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
