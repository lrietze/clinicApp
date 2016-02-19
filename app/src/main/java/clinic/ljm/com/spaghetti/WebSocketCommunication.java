package clinic.ljm.com.spaghetti;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class WebSocketCommunication {
    static final String debugTag = "Websocket";
    private WebSocketConnection mConnection;
    private ClinicHandler ch;

    public WebSocketCommunication(ClinicHandler ch) {
        this.ch = ch;
        mConnection = new WebSocketConnection();
        start();
    }

    private void start() {
        final String wsuri = "ws://198.162.21.167:9002";

        try {
            mConnection.connect(wsuri, new WebSocketHandler() {
                public void onOpen() {
                    Log.d(debugTag, "Websocket is open");
                    requestGetAll();
                }

                public void onTextMessage(String payload) {
                    Log.d(debugTag, "Received load: " + payload);
                    receiveJSON(payload);
                }

                public void onClose(int code, String reason) {
                    Log.d(debugTag, "Websocket connection lost");
                }
            });
        } catch (WebSocketException e) {
            Log.d(debugTag, e.toString());
        }
    }

    public void requestGetAll() {
        try {
            JSONObject getAllRequest = new JSONObject();
            getAllRequest.put("action", "getall");
            String toSend = getAllRequest.toString();
            mConnection.sendTextMessage(toSend);
        } catch (Exception e) {
            Log.d(debugTag, "Request to get all failed");
            e.printStackTrace();
        }
    }

    public void receiveJSON(String payload) {
        try {
            JSONObject data = new JSONObject(payload);
            //This assumes that data.action == 'update'
            JSONArray clinicsJSON = data.getJSONArray("clinics");
            for (int i = 0; i < clinicsJSON.length(); i++) {
                JSONObject clinicJSON = clinicsJSON.getJSONObject(i);
                Clinic c = new Clinic(clinicJSON);
                ch.setClinic(c, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUpdate(Clinic c) {
        try {
            JSONObject updateRequest = new JSONObject();
            updateRequest.put("action", "update");
            updateRequest.put("clinic", c.toJSON());
            String toSend = updateRequest.toString();
            mConnection.sendTextMessage(toSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
