package clinic.ljm.com.spaghetti;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ClinicHandler {
	private ArrayList<Clinic> clinics;
    private RecyclerView.Adapter adapter;
	private final String TAG = "Clinics";
    private WebSocketCommunication connection;


    public ClinicHandler() {
        this.clinics = new ArrayList<Clinic>();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

	public void setClinic(Clinic newClinic, boolean toServer){

        boolean exists = false;
		Log.d(TAG, "Inside setClinic");
		for(int i = 0; i < clinics.size(); i++){
	        if(clinics.get(i).getId() == newClinic.getId())
	        {
                exists = true;
	        	clinics.set(i, newClinic);
                break;
	        }
		}
        if (!exists)
		    clinics.add(newClinic);
        adapter.notifyDataSetChanged(); //Todo don't notify entire list
        if(toServer)
            connection.sendUpdate(newClinic);
	}

    public Clinic getClinicById(int id) {
        for (Clinic c : clinics) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

	public ArrayList<Clinic> getAsList() {
		return clinics;
	}

    public int getSize() {
        return clinics.size();
    }

    public Clinic getByIndex(int i) {
        return clinics.get(i);
    }


    public void setConnection(WebSocketCommunication connection) {
        this.connection = connection;
    }

    public WebSocketCommunication getConnection() {
        return connection;
    }
}
