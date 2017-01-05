//
//  RCTHaMap.m
//  RCTHaMap
//
//  Created by HuierCloud on 1/4/17.
//  Copyright © 2017 Hank. All rights reserved.
//

#import "RCTHaMap.h"

#import <AMapFoundationKit/AMapFoundationKit.h>
#import <AMapLocationKit/AMapLocationKit.h>

@interface RCTHaMap()

@property(nonatomic, strong) AMapLocationManager *locationManager;
@end

@implementation RCTHaMap
RCT_EXPORT_MODULE(HAMapModule);


RCT_EXPORT_METHOD(registerService:(NSString *)serviceKey) {
    
    [[AMapServices sharedServices] setApiKey:serviceKey];
    [[AMapServices sharedServices] setEnableHTTPS:YES];
}

RCT_EXPORT_METHOD(startLocate:(RCTResponseSenderBlock)callback)
{
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        
        self.locationManager = [[AMapLocationManager alloc] init];
        [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
        [self.locationManager setDelegate:self];
        [self.locationManager setLocationTimeout:10];
        [self.locationManager setReGeocodeTimeout:10];
        
        
        [self.locationManager
         requestLocationWithReGeocode:YES
         completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
             
             if (error) {
                 
                 callback(@[[NSNull null], @{@"ErrCode": [NSString stringWithFormat:@"%ld", error.code], @"ErrInfo": error.description}]);
             }else {
                 
                 NSMutableDictionary *successDict = [NSMutableDictionary new];
                 [successDict setObject:[NSString stringWithFormat:@"%lf",location.coordinate.longitude] forKey:@"longitude"];
                 [successDict setObject:[NSString stringWithFormat:@"%lf",location.coordinate.latitude] forKey:@"latitude"];
                 
                 // 时间戳转换
                 NSString *timeSp = [NSString stringWithFormat:@"%ld", (long)[location.timestamp timeIntervalSince1970] *1000];
                 [successDict setObject:timeSp forKey:@"locationTime"];
                 
                 [successDict setObject:regeocode.formattedAddress forKey:@"address"];
                 [successDict setObject:regeocode.country forKey:@"country"];
                 [successDict setObject:regeocode.province forKey:@"province"];
                 [successDict setObject:regeocode.city forKey:@"city"];
                 [successDict setObject:regeocode.district forKey:@"district"];
                 [successDict setObject:regeocode.street forKey:@"street"];
                 [successDict setObject:regeocode.POIName  forKey:@"poi"];
                 [successDict setObject:regeocode.number forKey:@"streetNum"];
                 
                 
                 callback(@[successDict, [NSNull null]]);
             }
         }
         ];
    });
}

@end
