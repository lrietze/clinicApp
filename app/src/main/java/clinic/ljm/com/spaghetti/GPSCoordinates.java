package clinic.ljm.com.spaghetti;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Locale;


/**
 * Created by Lucas on 11/23/2014.
 */
public class GPSCoordinates {
    private AddClinicDialog dialog;

    public GPSCoordinates(AddClinicDialog dialog){
        this.dialog = dialog;
    }

    //import android.location.LocationManager;
    public Location getCurrentLocation() {
        //TODO need to use NETWORK_PROVIDER too
        LocationManager lm = (LocationManager)dialog.getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
        }
        return location;
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            Log.d("GPS", "LocationPoint established");
            LocationManager lm = (LocationManager)dialog.getActivity().getSystemService(Context.LOCATION_SERVICE);
            lm.removeUpdates(this);
            String address = getAddress(loc);
            dialog.updateAddress(address);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    public String getAddress(Location loc) {
        Context context = dialog.getActivity().getApplicationContext();
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                return (address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) + ", " : "") + address.getLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
