package com.mo.libsx.utils.systemUtils;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.mo.libsx.k;
import com.mo.libsx.utils.app_utils.PermissionUtil;
import com.mo.libsx.utils.tips_utils.LogUtil;

import java.util.List;
import java.util.Locale;


/**
 * @ author：mo
 * @ data：2020/8/12:17:33
 * @ 功能：
 */
public class LocationUtils {

    public static Location getLocation() {
        if (PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            LogUtil.i("定位权限关闭，无法获取地理位置");
            return null;
        }
        //获取位置管理器
        LocationManager locationManager = (LocationManager) k.app().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            //创建一个criteria对象
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            //设置不需要获取海拔方向数据
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            //设置允许产生资费
            criteria.setCostAllowed(true);
            //要求低耗电
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, true);
            LogUtil.i("Location Provider is " + provider);
            return locationManager.getLastKnownLocation(provider);
        } else {
            LogUtil.i("Location Provider is GPS_PROVIDER");
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    /**
     * 获取当前定位信息 ：:城市、街道等信息 [Address[addressLines=[0:"北京市丰台区五圈路",1:"E座",2:"中建设二局大厦",3:"高碑店列车新城",4:"F座中联盈创企业咨询有限公司",5:"北京盈坤维世纪-d座",6:"盈坤世纪F座",7:"华电科技产业园",8:"萝莉国际幼儿园",9:"万达广场",10:"北京汽车博物馆"],feature=null,admin=北京市,sub-admin=null,locality=北京市,thoroughfare=五圈路,postalCode=null,countryCode=CN,countryName=中国,hasLatitude=true,latitude=39.83186069528015,hasLongitude=true,longitude=116.31144315136672,phone=null,url=null,extras=null]]
     */
    public static Address getAddress() {
        return getAddress(getLocation());
    }

    public static Address getAddress(Location location) {
        return getAddress(location.getLatitude(), location.getLongitude());
    }

    public static Address getAddress(double latitude, double longitude) {
        List<Address> result = null;
        try {
            Geocoder gc = new Geocoder(k.app(), Locale.getDefault());
            result = gc.getFromLocation(latitude, longitude, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? null : result.get(0);
    }

    public static String getCity() {
        return getCity("全国");
    }

    public static String getCity(String defCity) {
        Address address = getAddress();
        if (address == null) {
            LogUtil.i("NULL ");
            return defCity;
        } else {
            return address.getLocality();
        }
    }

    public static Address getAddrss(String city) {
        Address address = null;
        try {
            Geocoder gc = new Geocoder(k.app(), Locale.getDefault());
            List<Address> result = gc.getFromLocationName(city, 1);
            if (result != null) {
                address = result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
