import React, { useEffect, useRef } from "react";
import { Animated, Easing, Text, View } from "react-native";

interface Props {
    animationCompleted: (loggedIn: boolean) => void;
}

const Splash = (props: Props) => {
    const splashAnimation = useRef(new Animated.Value(0)).current;
    useEffect(() => {
        Animated.timing(splashAnimation, {
            toValue: 1,
            duration: 1000,
            useNativeDriver: true,
            easing: Easing.linear
        }).start(({ finished }) => {
            if (finished)
                props.animationCompleted(true);
        })
    }, [])
    return (
        <View style={{ width: '100%', height: '100%', justifyContent: 'center', alignItems: 'center' }}>
            <Animated.Text style={{ transform: [{ rotate: splashAnimation.interpolate({ inputRange: [0, 1], outputRange: ['0deg', '360deg'] }) }] }}>Splash Screen</Animated.Text>
        </View>
    );
};

export default Splash;