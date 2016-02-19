package clinic.ljm.com.spaghetti;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ClinicHandler ch;
    private WebSocketCommunication wsCommunication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ch = new ClinicHandler();
        wsCommunication = new WebSocketCommunication(ch);

        RecyclerView clinicList = (RecyclerView) findViewById(R.id.clinicList);
        clinicList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        clinicList.setLayoutManager(llm);

        ClinicRecyclerAdapter ca = new ClinicRecyclerAdapter(ch, getFragmentManager());
        ch.setAdapter(ca);
        ch.setConnection(wsCommunication);
        clinicList.setAdapter(ca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewMap:
                goToMapActivity();
                return true;
            case R.id.addClinic:
                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                boolean admin = sp.getBoolean("admin", false);
                Log.d("bool", Boolean.toString(admin));
                if (admin == true) {
                    DialogFragment newFragment = new AddClinicDialog();
                    newFragment.show(getFragmentManager(), "this");
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry, you do not have admin permissions.", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToMapActivity() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putParcelableArrayListExtra("clinics", ch.getAsList());
        startActivity(intent);
    }

    protected void updateClinicCount(int id, int count) {
        Clinic c = ch.getClinicById(id);
        c.setCount(count);
        ch.setClinic(c, true);
    }

    protected void addToClinicHandler(Clinic c) {
        ch.setClinic(c, true);
    }
}

