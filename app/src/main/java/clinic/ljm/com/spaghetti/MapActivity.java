package clinic.ljm.com.spaghetti;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends Activity {

    private int secretCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ArrayList<Clinic> clinics = getIntent().getParcelableArrayListExtra("clinics");

        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng defaultLocation = new LatLng(50.669617, -120.355133);
        LatLng sl = new LatLng(-77.841809, 166.705824);
        
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 14));

        for (Clinic c : clinics) {
            map.addMarker(new MarkerOptions()
                    .title(c.getName())
                    .snippet(c.getCount() + " people in line")
                    .position(c.getPoint()));
        }

        map.addMarker(new MarkerOptions()
                .title("McMurdo Walk-In Clinic")
                .snippet("")
                .position(sl));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                String loc = marker.getTitle();
                if (loc.equals("McMurdo Walk-In Clinic")) {
                    ++secretCount;
                    if (secretCount == 6) {
                        Toast.makeText(getApplicationContext(), "You now have admin permissions.", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("admin", true);
                        editor.commit();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewList:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
