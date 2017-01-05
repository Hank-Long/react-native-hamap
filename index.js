/**
 * Created by Hank on 1/4/17.
 */

'use strict';

import { NativeModules } from 'react-native';


export function registerService(key) {
    return NativeModules.HAMapModule.registerService(key);
}

export function startLocate(callback) {

    return NativeModules.HAMapModule.startLocate(callback);
};