package hank.com.hamap;


import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;


/**
 * Created by Hank on 1/4/17.
 */

public class HAMapModule extends ReactContextBaseJavaModule {

    public static String MODULE_NAME = "HAMapModule";

    private Context mContext = null;

    private AMapLocationClient mlocationClient = null;
    private Callback LocateCallback = null;



    @Override
    public String getName() {
        return MODULE_NAME;
    }

    public HAMapModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @ReactMethod
    public void registerService(String serviceKey) {

    }

    @ReactMethod
    public void startLocate(Callback callback) {
        this.LocateCallback = callback;
        runAMapLocate();
    }


    private void runAMapLocate() {

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

        mlocationClient = new AMapLocationClient(this.mContext);
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {

                    mlocationClient.stopLocation();

                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息

                        WritableMap SuccessMap= Arguments.createMap();

                        try {
                            SuccessMap.putDouble("longitude", aMapLocation.getLongitude());
                            SuccessMap.putDouble("latitude", aMapLocation.getLatitude());
                            SuccessMap.putDouble("locationTime", aMapLocation.getTime());

                            SuccessMap.putString("country", aMapLocation.getCountry());
                            SuccessMap.putString("province", aMapLocation.getProvince());
                            SuccessMap.putString("city", aMapLocation.getCity());
                            SuccessMap.putString("district", aMapLocation.getDistrict());
                            SuccessMap.putString("street", aMapLocation.getStreet());
                            SuccessMap.putString("poi", aMapLocation.getPoiName());
                            SuccessMap.putString("streetNum", aMapLocation.getStreetNum());
                            SuccessMap.putString("address", aMapLocation.getAddress());


                            if (LocateCallback != null) {
                                LocateCallback.invoke(SuccessMap, null);
                                LocateCallback = null;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {

                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        WritableMap ErrorMap = Arguments.createMap();
                        try {
                            ErrorMap.putInt("ErrCode", aMapLocation.getErrorCode());
                            ErrorMap.putString("ErrInfo", aMapLocation.getErrorInfo());

                            if (LocateCallback != null) {
                                LocateCallback.invoke(ErrorMap, null);
                                LocateCallback = null;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);

        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }
}
