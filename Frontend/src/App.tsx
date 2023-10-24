import React, { useState } from 'react';
import { SafeAreaView, StatusBar, Text, View, } from 'react-native';
import Splash from './auth/splash';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SignIn from './auth/sign_in';
import SignUp from './auth/sign_up';
import ChangePassword from './auth/change_password';
import { SCREENS } from './types';
import Dashboard from './dashboard';


const Stack = createNativeStackNavigator();
function App(): JSX.Element {
  const [showNavigator, setShowNavigator] = useState({
    showNavigator: false,
    loggedIn: false
  });
  return (
    <SafeAreaView style={{ width: '100%', height: '100%' }}>
      <StatusBar />
      {!showNavigator.showNavigator && <Splash animationCompleted={(loggedIn) => setShowNavigator({ showNavigator: true, loggedIn })} />}
      {showNavigator.showNavigator &&
        <NavigationContainer>
          <Stack.Navigator initialRouteName={showNavigator.loggedIn ? SCREENS.DASHBOARD : SCREENS.SIGN_IN}>
            <Stack.Screen name={SCREENS.SIGN_IN} component={SignIn} options={{ headerShown: false }} />
            <Stack.Screen name={SCREENS.SIGN_UP} component={SignUp} options={{ headerShown: false }} />
            <Stack.Screen name={SCREENS.CHANGE_PASSWORD} component={ChangePassword} options={{ headerShown: false }} />
            <Stack.Screen name={SCREENS.DASHBOARD} component={Dashboard} options={{ headerShown: false }} />
          </Stack.Navigator>
        </NavigationContainer>
      }
    </SafeAreaView>
  );
}



export default App;
