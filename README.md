# react-native-hamap
React-Native AMap locate for Android and iOS


## How to install 
npm install react-native-hamap --save


### Linking Android with Gradle

- Add following lines into `android/settings.gradle`

    ```gradle
    include ':react-native-hamap'
    project(':react-native-hamap').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-hamap/android')
    ```

- Add following lines into your `android/app/build.gradle` in section `dependencies`

    ```gradle
    dependencies {
      compile project(':react-native-hamap')    // Add this line only.
    }
    ```
    
    
- Add following lines into `AndroidManifest.xml`

 ```
 <application
      android:name=".MainApplication"
      android:label="@string/app_name"
      android:icon="@mipmap/ic_launcher">
      ...
      
      <meta-data                                        // Add this line
          android:name="com.amap.api.v2.apikey"         // Add this line
          android:value="your key"/>                    // Add this line

    </application>
    ```

- Add following lines into `MainActivity.java` or `MainApplication.java`:

    ```java
    import hank.com.hamap.HAMapPackage;       
    ...

    /**
     * A list of packages used by the app. If the app uses additional views
     * or modules besides the default ones, add more packages here.
     */
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(), 
          new HAMapPackage()        // Add this line
      );
    }
    ```

### Linking iOS

- Link `RCTHaMap` library from your `node_modules/react-native-hamap/ios` folder like react-native's 
[Linking Libraries iOS Guidance], Note: _Don't forget to add it to "Build Phases" of your target project_.

- Add the following libraries to your "Link Binary with Libraries":

    ```
    CoreTelephony.framework
    libc++
    libz
    ```
    
    
- Add the following libraries to your "Link Binary with Libraries",  clicking the  `Add other..`  button to select them from `node_modules/react-native-hamap/ios/Framework`

  ```
  node_modules/react-native-hamap/ios/Framework/AMapFoundationKit.farmework
  node_modules/react-native-hamap/ios/Framework/AMapLocationKit.framework
  ```
  
  
- Add the static libraries to your "Link Binary with Libraries"

  ```
  libRCTHaMap.a
  ```
  
  
  
- Add  `NSLocationWhenInUseUsageDescription`  or  `NSLocationAlwaysUsageDescription`  and  `NSAllowsArbitraryLoads`  with value `Yes`  in `Targets` > `info`

  or edit Info.plist add:

    ```
    <key>NSLocationWhenInUseUsageDescription</key>
	  <string>description</string>
    
    <key>NSAllowsArbitraryLoads</key>
		<true/>
    ```
    
    
    
## How to use
This package has only two methods: 

### registerService(key)
You can call this function when the application is lanuched,  but nothing will be wrong if you call it more than once.
This must be called for iOS. It will do nothing for Android because the key will be found in `AndroidManifest.xml`.

  ```js
  componentWillMount (){
      HLocation.registerService('your key')
  }
  ```

- {String} `key` the key you get from AMap dashboard
- returns {null} 


### startLocate(callback)
You can call this function when the application is lanuched,  but nothing will be wrong if you call it more than once.

  ```js
  componentDidMount() {
          HLocation.startLocate((location, error) => {
      });
  }
  ```
  
  
- returns {location} when locate successed.

values:

| name          | type   | description                         |
|---------------|--------|-------------------------------------|
| longitude     | Number |                                     |
| latitude      | Number |                                     |
| locationTime  | Number |                                     |
| country       | String |                                     |
| province      | String |                                     |
| city          | String |                                     | 
| district      | String |                                     |
| street        | String |                                     |
| poi           | String | Your building or departure          |
| streetNum     | String | Your street number                  |
| address       | String | The whole address                   |
  
  
- reterns {error} when locate failed.

values:

| name          | type   | description                         |
|---------------|--------|-------------------------------------|
| ErrorCode     | Number |                                     |
| ErrorInfo     | String |                                     |





### @author Hank

  
