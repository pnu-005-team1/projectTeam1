package kr.ac.pnu.cse.menumapproject.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GeocoderUtil {
    private static Geocoder geocoder;

    @Nullable
    public static LatLng getLatLngFromAddress(String addressString, Context context) {
        if(geocoder == null) {
            geocoder = new Geocoder(context);
        }
        try {
            List<Address> address = geocoder.getFromLocationName(addressString, 5);
            if (address != null) {
                Address location = address.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
